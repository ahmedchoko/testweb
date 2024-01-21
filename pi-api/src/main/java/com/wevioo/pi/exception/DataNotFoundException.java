package com.wevioo.pi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

/**
 * Data not found exception. It will return the Not found HTTP status code
 */
public class DataNotFoundException extends RestException {
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -3260745311457353808L;

	/**
	 * Constructor with code
	 * 
	 * @param code error code
	 */
	public DataNotFoundException(String code) {
		super(HttpStatus.NOT_FOUND, code);
	}

	/**
	 * Constructor with code and message
	 * 
	 * @param code    error code
	 * @param message error message
	 */
	public DataNotFoundException(String code, String message) {
		super(HttpStatus.NOT_FOUND, code, message);
	}

	/**
	 * Constructor with errord and message
	 * 
	 * @param message error message
	 * @param errors  Object of errors
	 */
	public DataNotFoundException(String message, Errors errors) {
		super(HttpStatus.NOT_FOUND, message, errors);
	} 
}
