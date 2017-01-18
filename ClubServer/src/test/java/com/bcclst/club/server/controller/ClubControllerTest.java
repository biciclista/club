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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	// Base path for URLs.
	private static final String PATH_CLUBS_BASE = "/clubs";
	private static final String PATH_CLUBS_FIND_BY_ID = PATH_CLUBS_BASE + "/{id}";

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
		final ClubDto club = new ClubDto(0L, "Club", "CLUB");
		when(clubService.create(any(ClubDto.class))).thenReturn(club);

		this.mvc.perform(post(PATH_CLUBS_BASE)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(club)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(club.getId().intValue())))
				.andExpect(jsonPath("$.name", is(club.getName())))
				.andExpect(jsonPath("$.acronym", is(club.getAcronym())));

		// Verify ClubService.create() is called once.
		ArgumentCaptor<ClubDto> dtoCaptor = ArgumentCaptor.forClass(ClubDto.class);
		verify(clubService).create(dtoCaptor.capture());
		verifyNoMoreInteractions(clubService);

		// Verify the ClubDto passed to ClubService.
		ClubDto capturedClub = dtoCaptor.getValue();
		assertThat(capturedClub.getId(), is(club.getId()));
		assertThat(capturedClub.getName(), is(club.getName()));
		assertThat(capturedClub.getAcronym(), is(club.getAcronym()));
		assertThat(capturedClub.getAcronym(), is(club.getAcronym()));
	}

	@Test
	public void createExistentAcronym() throws Exception {
		final String DUPLICATED_ACRONYM = "AAA";

		when(clubService.create(any(ClubDto.class))).thenThrow(new DuplicatedClubAcronymException(DUPLICATED_ACRONYM));

		this.mvc.perform(post(PATH_CLUBS_BASE)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(new ClubDto(0L, "AAA", DUPLICATED_ACRONYM))))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is(ERROR_DUPLICATED_ACRONYM)))
				.andExpect(jsonPath("$.message",
						is(String.format("The acronym %s is in use by another club.", DUPLICATED_ACRONYM))));

		// Verify ClubService.create() is called once.
		verify(clubService).create(any(ClubDto.class));
		verifyNoMoreInteractions(clubService);
	}

	@Test
	public void createWithInvalidDto() throws Exception {
		this.mvc.perform(post(PATH_CLUBS_BASE)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(new ClubDto(null, "AA", "A_A"))))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(3)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("id", "name", "acronym")));

		// Check that ClubService is not called.
		verifyZeroInteractions(clubService);
	}

	@Test
	public void findByIdIsValid() throws Exception {
		final ClubDto foundClub = new ClubDto(1L, "Club", "CLUB");
		when(clubService.findById(foundClub.getId())).thenReturn(foundClub);

		this.mvc.perform(get(PATH_CLUBS_FIND_BY_ID, 1L)
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

		mvc.perform(get(PATH_CLUBS_FIND_BY_ID, 1L)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

		verify(clubService).findById(1L);
		verifyNoMoreInteractions(clubService);
	}
}
