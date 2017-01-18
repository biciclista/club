package com.bcclst.club.server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for returning validation errors. It's constructed with a collection of
 * {@link FieldErrorDto} with the validation error for each field in the form.
 * 
 * @author Nacho
 */
public class ValidationErrorDto implements Serializable {
	private static final long serialVersionUID = -2882726583030692869L;
	private List<FieldErrorDto> fieldErrors = new ArrayList<>();

	/**
	 * Add an error of a field to the list of errors.
	 * 
	 * @param field Name of the field that the error is bound to. 
	 * @param message Error message for the field.
	 */
	public void addFieldError(String field, String message) {
		FieldErrorDto fieldError = new FieldErrorDto(field, message);
		fieldErrors.add(fieldError);
	}

	/**
	 * Gets all field errors.
	 *  
	 * @return List with all field errors.
	 */
	public List<FieldErrorDto> getFieldErrors() {
		return fieldErrors;
	}
}
