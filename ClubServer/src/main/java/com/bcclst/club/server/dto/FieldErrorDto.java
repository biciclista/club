package com.bcclst.club.server.dto;

import java.io.Serializable;

/**
 * Represents a validation error for a field in a form.
 * 
 * @author Nacho
 */
public class FieldErrorDto implements Serializable {

	private static final long serialVersionUID = 1969087151427173245L;

	private final String field;
	private final String message;

	/**
	 * Constructs a new validation error for a field.
	 * 
	 * @param field Field with validation error.
	 * @param message Description of validation error.
	 */
	public FieldErrorDto(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

}
