package com.bcclst.club.server.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class RestErrorDto implements Serializable {

	private static final long serialVersionUID = 7026894240730324882L;

	private final HttpStatus status;
	private final String code;
	private final String message;
	private final String developerInfo;
	private final Throwable exception;
	
	/**
	 * @param status
	 * @param code
	 * @param message
	 * @param developerInfo
	 * @param exception
	 */
	public RestErrorDto(HttpStatus status, String code, String description, String developerInfo, Throwable exception) {
		this.status = status;
		this.code = code;
		this.message = description;
		this.developerInfo = developerInfo;
		this.exception = exception;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDeveloperInfo() {
		return developerInfo;
	}

	public Throwable getException() {
		return exception;
	}
	
	
}
