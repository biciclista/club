package com.bcclst.club.server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for validation errors. It's formed with a collection of
 * {@link FieldErrorDto} with the validation error for each field in the form.
 * 
 * @author Nacho
 */
public class ValidationErrorDto implements Serializable {
	private static final long serialVersionUID = -2882726583030692869L;
	private List<FieldErrorDto> fieldErrors = new ArrayList<>();

	public void addFieldError(String path, String message) {
		FieldErrorDto error = new FieldErrorDto(path, message);
		fieldErrors.add(error);
	}

	public List<FieldErrorDto> getFieldErrors() {
		return fieldErrors;
	}
}
