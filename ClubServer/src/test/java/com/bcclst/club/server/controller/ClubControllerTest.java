package com.bcclst.club.server.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bcclst.club.server.dto.ClubDto;
import com.bcclst.club.server.service.ClubService;
import com.bcclst.club.server.service.exception.ExistentClubAcronymException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ClubController.class)
public class ClubControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ClubService clubService;
	
	@Test
	public void createIsValid() throws Exception {
		final ClubDto dto = new ClubDto(1L, "Club", "CLUB");
		when(clubService.create(any(ClubDto.class))).thenReturn(dto);

		this.mvc.perform(post("/clubs/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new ClubDto(0L, "AAA", "AAA"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(dto.getId()))
				.andExpect(jsonPath("$.name").value(dto.getName()))
				.andExpect(jsonPath("$.acronym").value(dto.getAcronym()));
	}
	
	@Test
	public void createExistentAcronym() throws Exception {
		when(clubService.create(any(ClubDto.class))).thenThrow(new ExistentClubAcronymException());
		
		this.mvc.perform(post("/clubs/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new ClubDto(0L, "AAA", "AAA"))))
				.andExpect(status().isConflict());
	}
}
