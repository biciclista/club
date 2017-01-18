package com.bcclst.club.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bcclst.club.server.dto.ClubDto;
import com.bcclst.club.server.service.ClubService;
import com.bcclst.club.server.service.exception.ClubNotFoundException;
import com.bcclst.club.server.service.exception.DuplicatedClubAcronymException;

/**
 * Controller for Club REST API.
 * 
 * @author Nacho
 */
@RestController
@RequestMapping("/clubs")
public class ClubController {

	final private ClubService clubService;

	/**
	 * Constructor for Spring.
	 * 
	 * @param clubService Bean for service for managing club requests.
	 * @param messageSource Bean for i18n messages source.
	 */
	public ClubController(final ClubService clubService) {
		this.clubService = clubService;
	}

	/**
	 * Creates a new Club. Accepts POST method with a {@link ClubDto} object.
	 * 
	 * @param club ClubDto object with values for new club.
	 * @return ClubDto Object with the values of the new created Club.
	 * @throws DuplicatedClubAcronymException If Acronym for new club is in use
	 *             by another club.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ClubDto create(@RequestBody @Valid final ClubDto club) throws DuplicatedClubAcronymException {
		return clubService.create(club);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ClubDto findById(@PathVariable("id") Long id) throws ClubNotFoundException {
		ClubDto club = clubService.findById(id);

		return club;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<ClubDto> findAll() {
		return clubService.findAll();
	}
}
