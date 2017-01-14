package com.bcclst.club.server.controller.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bcclst.club.server.dto.FieldErrorDto;
import com.bcclst.club.server.dto.ValidationErrorDto;
import com.bcclst.club.server.util.LocaleMessage;

/**
 * Handler for validation exceptions. It constructs a {@link ValidationErrorDto}
 * with a list of individual {@link FieldErrorDto} for each validation error.
 * 
 * @author Nacho
 */
@ControllerAdvice
public class ValidationErrorHandler {

	private LocaleMessage localeMessage;

	/**
	 * Constructs the handler.
	 * 
	 * @param localeMessage Bean for localize messages.
	 */
	public ValidationErrorHandler(LocaleMessage localeMessage) {
		this.localeMessage = localeMessage;
	}

	/**
	 * Process the validation errors.
	 * 
	 * @param e Exception that fires this handler.
	 * @return {@link ValidationErrorDto} with the list of {@link FieldErrorDto}
	 *         for each field with validation error.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDto processValidationError(MethodArgumentNotValidException e) {
		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		ValidationErrorDto validationErrorDto = new ValidationErrorDto();

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = localeMessage.getMessage(fieldError);
			validationErrorDto.addFieldError(fieldError.getField(), localizedErrorMessage);
		}

		return validationErrorDto;
	}
}
