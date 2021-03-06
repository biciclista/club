package com.bcclst.club.server.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bcclst.club.server.dto.ClubDto;
import com.bcclst.club.server.service.ClubService;
import com.bcclst.club.server.service.exception.ClubNotFoundException;
import com.bcclst.club.server.service.exception.DuplicatedClubAcronymException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ClubController.class)
public class ClubControllerTest {
	// URIs.
	private static final String PATH_CLUBS = "/clubs";
	private static final String PATH_CLUBS_ID = PATH_CLUBS + "/{id}";

	// Error codes.
	private final static String ERROR_DUPLICATED_ACRONYM = "10001001";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ClubService clubService;

	@Test
	public void createIsValid() throws Exception {
		final ClubDto club = new ClubDto(0L, "Club", "CLUB", true);
		when(clubService.create(any(ClubDto.class))).thenReturn(club);

		this.mvc.perform(post(PATH_CLUBS)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(club)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(club.getId().intValue())))
				.andExpect(jsonPath("$.name", is(club.getName())))
				.andExpect(jsonPath("$.acronym", is(club.getAcronym())));

		ArgumentCaptor<ClubDto> dtoCaptor = ArgumentCaptor.forClass(ClubDto.class);
		verify(clubService).create(dtoCaptor.capture());
		verifyNoMoreInteractions(clubService);

		ClubDto capturedClub = dtoCaptor.getValue();
		assertThat(capturedClub.getId(), is(club.getId()));
		assertThat(capturedClub.getName(), is(club.getName()));
		assertThat(capturedClub.getAcronym(), is(club.getAcronym()));
		assertThat(capturedClub.isActive(), is(club.isActive()));
	}

	@Test
	public void createExistentAcronym() throws Exception {
		final String DUPLICATED_ACRONYM = "AAA";

		when(clubService.create(any(ClubDto.class))).thenThrow(new DuplicatedClubAcronymException(DUPLICATED_ACRONYM));

		this.mvc.perform(post(PATH_CLUBS)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(new ClubDto(0L, "AAA", DUPLICATED_ACRONYM, false))))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(ERROR_DUPLICATED_ACRONYM)))
				.andExpect(jsonPath("$.message",
						is(String.format("The acronym %s is in use by another club.", DUPLICATED_ACRONYM))));

		verify(clubService).create(any(ClubDto.class));
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void createWithInvalidDto() throws Exception {
		this.mvc.perform(post(PATH_CLUBS)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(new ClubDto(null, "AA", "A_A", false))))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(3)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("id", "name", "acronym")));

		verifyZeroInteractions(clubService);
	}

	@Test
	public void findByIdIsValid() throws Exception {
		final ClubDto foundClub = new ClubDto(1L, "Club", "CLUB", true);
		when(clubService.findById(foundClub.getId())).thenReturn(foundClub);

		this.mvc.perform(get(PATH_CLUBS_ID, 1L)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(foundClub.getId().intValue())))
				.andExpect(jsonPath("$.name", is(foundClub.getName())))
				.andExpect(jsonPath("$.acronym", is(foundClub.getAcronym())));

		verify(clubService).findById(1L);
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void findByIdClubNotFound() throws Exception {
		when(clubService.findById(anyLong())).thenThrow(new ClubNotFoundException(0));

		mvc.perform(get(PATH_CLUBS_ID, 1L)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

		verify(clubService).findById(1L);
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void findAllIsValid() throws Exception {
		final ClubDto club1 = new ClubDto(1L, "Club 1", "CLUB1", false);
		final ClubDto club2 = new ClubDto(2L, "Club 2", "CLUB2", true);
		final ClubDto[] clubs = { club1, club2 };
		final List<ClubDto> clubList = Arrays.asList(clubs);

		when(clubService.findAll()).thenReturn(clubList);

		mvc.perform(get(PATH_CLUBS)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(club1.getId().intValue())))
				.andExpect(jsonPath("$[0].name", is(club1.getName())))
				.andExpect(jsonPath("$[0].acronym", is(club1.getAcronym())))
				.andExpect(jsonPath("$[0].active", is(club1.isActive())))
				.andExpect(jsonPath("$[1].id", is(club2.getId().intValue())))
				.andExpect(jsonPath("$[1].name", is(club2.getName())))
				.andExpect(jsonPath("$[1].acronym", is(club2.getAcronym())))
				.andExpect(jsonPath("$[1].active", is(club2.isActive())));

		verify(clubService).findAll();
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void findAllIsEmpty() throws Exception {
		final ClubDto[] clubs = {};
		final List<ClubDto> clubList = Arrays.asList(clubs);

		when(clubService.findAll()).thenReturn(clubList);

		mvc.perform(get(PATH_CLUBS)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(0)));

		verify(clubService).findAll();
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void updateIsValid() throws Exception {
		final ClubDto club = new ClubDto(1L, "Club 1", "CLUB1", false);

		when(clubService.update(any(ClubDto.class))).thenReturn(club);

		mvc.perform(put(PATH_CLUBS_ID, 1)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(club)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(club.getId().intValue())))
				.andExpect(jsonPath("$.name", is(club.getName())))
				.andExpect(jsonPath("$.acronym", is(club.getAcronym())));

		ArgumentCaptor<ClubDto> dtoCaptor = ArgumentCaptor.forClass(ClubDto.class);
		verify(clubService).update(dtoCaptor.capture());
		verifyNoMoreInteractions(clubService);

		ClubDto capturedClub = dtoCaptor.getValue();
		assertThat(capturedClub.getId(), is(club.getId()));
		assertThat(capturedClub.getName(), is(club.getName()));
		assertThat(capturedClub.getAcronym(), is(club.getAcronym()));
		assertThat(capturedClub.isActive(), is(club.isActive()));
	}

	@Test
	public void updateClubNotFound() throws Exception {
		final ClubDto club = new ClubDto(1L, "Club 1", "CLUB1", true);

		when(clubService.update(any(ClubDto.class))).thenThrow(new ClubNotFoundException(club.getId()));

		mvc.perform(put(PATH_CLUBS_ID, club.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(club)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

		verify(clubService).update(any(ClubDto.class));
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void updateDifferentIdThanUri() throws Exception {
		final ClubDto club = new ClubDto(2L, "Club 1", "CLUB1", false);
		final ClubDto updatedClub = new ClubDto(1L, "Club 1", "CLUB1", false);

		when(clubService.update(any(ClubDto.class))).thenReturn(updatedClub);

		mvc.perform(put(PATH_CLUBS_ID, 1)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(club)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(updatedClub.getId().intValue())));

		ArgumentCaptor<ClubDto> dtoCaptor = ArgumentCaptor.forClass(ClubDto.class);
		verify(clubService).update(dtoCaptor.capture());
		verifyNoMoreInteractions(clubService);

		ClubDto capturedClub = dtoCaptor.getValue();
		assertThat(capturedClub.getId(), is(updatedClub.getId()));
	}

	@Test
	public void deleteIsValid() throws Exception {
		final ClubDto club = new ClubDto(1L, "Club 1", "CLUB1", true);

		when(clubService.deleteById(club.getId())).thenReturn(club);

		mvc.perform(delete(PATH_CLUBS_ID, club.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(club.getId().intValue())))
				.andExpect(jsonPath("$.name", is(club.getName())))
				.andExpect(jsonPath("$.acronym", is(club.getAcronym())));

		verify(clubService).deleteById(club.getId());
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void deleteClubNotFound() throws Exception {
		final Long id = new Long(1);

		when(clubService.deleteById(id)).thenThrow(new ClubNotFoundException(id));

		mvc.perform(delete(PATH_CLUBS_ID, id)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

		verify(clubService).deleteById(id);
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void updateOnlyActive() throws Exception {
		final ClubDto club = new ClubDto(1L, "Club 1", "CLUB1", true);

		when(clubService.changeState(club.getId(), true)).thenReturn(club);
		
		mvc.perform(put(PATH_CLUBS_ID, club.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.param("active", "true"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(club.getId().intValue())))
				.andExpect(jsonPath("$.name", is(club.getName())))
				.andExpect(jsonPath("$.acronym", is(club.getAcronym())))
				.andExpect(jsonPath("$.active", is(club.isActive())));
		
		verify(clubService).changeState(club.getId(), club.isActive());
		verifyNoMoreInteractions(clubService);
	}
}
