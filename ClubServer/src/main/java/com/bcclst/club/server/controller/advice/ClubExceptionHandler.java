package com.bcclst.club.server.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bcclst.club.server.dto.RestErrorDto;
import com.bcclst.club.server.service.exception.ClubNotFoundException;

@ControllerAdvice
public class ClubExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = ClubNotFoundException.class)
	@ResponseBody
	public RestErrorDto handleClubNotFound(ClubNotFoundException e) {
		return new RestErrorDto(HttpStatus.NOT_FOUND, 0, e.getLocalizedMessage(), e.getMessage(), e);
	}
}
