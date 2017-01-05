package com.bcclst.club.server.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bcclst.club.server.dto.ClubDto;
import com.bcclst.club.server.service.ClubService;
import com.bcclst.club.server.service.exception.ClubNotFoundException;
import com.bcclst.club.server.service.exception.DuplicatedClubAcronymException;
import com.bcclst.common.rest.RestError;

/**
 * Controller for Club REST API.
 * 
 * @author Nacho
 *
 */
@RestController
@RequestMapping("/clubs")
public class ClubController {
	private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

	final private ClubService clubService;
	final private MessageSource messageSource;

	/**
	 * Constructor for Spring.
	 * 
	 * @param clubService Bean for service for managing club requests.
	 * @param messageSource Bean for i18n messages source.
	 */
	public ClubController(final ClubService clubService, final MessageSource messageSource) {
		this.clubService = clubService;
		this.messageSource = messageSource;
	}

	/**
	 * Creates a new Club. Accepts POST method with a {@link ClubDto} object.
	 * 
	 * @param club ClubDto object with values for new club.
	 * @return ClubDto object with the values of the new created Club.
	 * @throws DuplicatedClubAcronymException If Acronym for new club is in use by another club.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ClubDto create(@RequestBody @Valid final ClubDto club) throws DuplicatedClubAcronymException {
		return clubService.create(club);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ClubDto findById(@PathVariable("id") Long id) throws ClubNotFoundException {
		ClubDto club = clubService.findById(id);

		return club;
	}

	@ExceptionHandler(DuplicatedClubAcronymException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public RestError handleDuplicatedClubAcronymException(DuplicatedClubAcronymException e) {

		final String[] params = { e.getDuplicatedAcronym() };
		final Locale locale = LocaleContextHolder.getLocale();

		logger.debug("Locale: {}", locale);

		final String msg = messageSource.getMessage("exception.duplicatedClubAcronymException.message", params,
				locale);
		
		logger.debug("Message: {}:", msg);

		return new RestError(0, msg, e.getMessage(), e);
	}
}