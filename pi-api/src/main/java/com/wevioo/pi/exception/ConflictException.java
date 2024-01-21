package com.wevioo.pi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
/**
 * Conflict  exception. It will return the Conflict  HTTP status code
 */
public class ConflictException extends RestException {
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -3260745311457353808L;

	/**
	 * Constructor with code
	 * 
	 * @param code error code
	 */
	public ConflictException(String code) {
		super(HttpStatus.CONFLICT, code);
	}

	/**
	 * Constructor with code and message
	 * 
	 * @param code    error code
	 * @param message error message
	 */
	public ConflictException(String code, String message) {
		super(HttpStatus.CONFLICT, code, message);
	}

	/**
	 * Constructor with errord and message
	 * 
	 * @param message error message
	 * @param errors  Object of errors
	 */
	public ConflictException(String message, Errors errors) {
		super(HttpStatus.CONFLICT, message, errors);
	} 
}
