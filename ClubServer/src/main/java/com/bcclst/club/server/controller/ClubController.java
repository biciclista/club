package com.bcclst.club.server.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bcclst.club.server.dto.ClubDto;
import com.bcclst.club.server.service.ClubService;
import com.bcclst.club.server.service.exception.ExistentClubAcronymException;

@RestController
@RequestMapping("/clubs")
public class ClubController {
	final private ClubService clubService;
	
	public ClubController(final ClubService clubService){
		this.clubService = clubService;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
    public ClubDto create(@RequestBody @Valid final ClubDto club) {
        return clubService.create(club);
    }
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handleExistentClubAcronymException(ExistentClubAcronymException e) {
	    return e.getMessage();
	}
}
