package com.wevioo.pi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

/**
 * Data validation exception. It will return the Not found HTTP status code
 */
public class DataValidationException extends RestException {
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -3260745311457353808L;

	/**
	 * Constructor with code
	 * 
	 * @param code error code
	 */
	public DataValidationException(String code) {
		super(HttpStatus.BAD_REQUEST, code);
	}

	/**
	 * Constructor with code and message
	 * 
	 * @param code    error code
	 * @param message error message
	 */
	public DataValidationException(String code, String message) {
		super(HttpStatus.BAD_REQUEST, code, message);
	}

	/**
	 * Constructor with errors and message
	 * 
	 * @param message error message
	 * @param errors  Object of errors
	 */
	public DataValidationException(String message, Errors errors) {
		super(HttpStatus.BAD_REQUEST, message, errors);
	} 
}
