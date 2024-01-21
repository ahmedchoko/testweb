package com.wevioo.pi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

/**
 * Entity already exist exception
 */
public class AlreadyExistException extends RestException {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -3260745311457353808L;

	/**
	 * Constructor with code and message
	 * 
	 * @param code    error code
	 * @param message error message
	 */
	public AlreadyExistException(String code, String message) {
		super(HttpStatus.CONFLICT, code, message);
	}

	/**
	 * Constructor with errord and message
	 * 
	 * @param message error message
	 * @param errors  Object of errors
	 */
	public AlreadyExistException(String message, Errors errors) {
		super(HttpStatus.CONFLICT, message, errors);
	}
}
