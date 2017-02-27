package com.bcclst.club.webclient.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bcclst.club.webclient.dto.ClubDto;
import com.bcclst.club.webclient.service.exception.ClubNotFoundException;
import com.bcclst.club.webclient.service.exception.DuplicatedClubAcronymException;

@Service
public class ClubServiceRest implements ClubService {

	private RestTemplate restTemplate;

	public ClubServiceRest(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public List<ClubDto> findAll() {
		return Arrays.stream(restTemplate.getForObject("http://localhost:8080/clubs", ClubDto[].class))
				.collect(Collectors.toList());
	}

	@Override
	public ClubDto findById(long id) throws ClubNotFoundException {
		return restTemplate.getForObject("http://localhost:8080/clubs/{id}", ClubDto.class, id);
	}

	@Override
	public ClubDto create(ClubDto club) throws DuplicatedClubAcronymException {
		return restTemplate.postForObject("http://localhost:8080/clubs", club, ClubDto.class);
	}

	@Override
	public ClubDto update(ClubDto club) throws DuplicatedClubAcronymException {
		return restTemplate.exchange("http://localhost:8080/clubs/{id}", HttpMethod.PUT, new HttpEntity<>(club),
				ClubDto.class, club.getId()).getBody();
	}

	@Override
	public ClubDto disable(long id) {
		return restTemplate.exchange("http://localhost:8080/clubs/{id}/disable", HttpMethod.PUT, null,
				ClubDto.class, id).getBody();
	}

	@Override
	public ClubDto enable(long id) {
		return restTemplate.exchange("http://localhost:8080/clubs/{id}/enable", HttpMethod.PUT, null,
				ClubDto.class, id).getBody();
	}

}
