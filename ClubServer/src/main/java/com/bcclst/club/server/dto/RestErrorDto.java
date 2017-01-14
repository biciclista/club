package com.bcclst.club.server.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class RestErrorDto implements Serializable {

	private static final long serialVersionUID = 7026894240730324882L;

	private final HttpStatus status;
	private final int code;
	private final String description;
	private final String developerInfo;
	private final Throwable exception;
	
	/**
	 * @param status
	 * @param code
	 * @param description
	 * @param developerInfo
	 * @param exception
	 */
	public RestErrorDto(HttpStatus status, int code, String description, String developerInfo, Throwable exception) {
		super();
		this.status = status;
		this.code = code;
		this.description = description;
		this.developerInfo = developerInfo;
		this.exception = exception;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getDeveloperInfo() {
		return developerInfo;
	}

	public Throwable getException() {
		return exception;
	}
	
	
}
