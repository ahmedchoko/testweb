package com.wevioo.pi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

/**
 * Missing required data exception
 */
public class BadRequestException extends RestException {
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -3260745311457353808L;
	
	/**
	 * Constructor with code
	 * 
	 * @param code error code
	 */
	public BadRequestException(String code) {
		super(HttpStatus.BAD_REQUEST, code);
	}
	/**
	 * Constructor with errord and message
	 * 
	 * @param message error message
	 * @param errors  Object of errors
	 */
	public BadRequestException( String message, Errors errors) {
		super(HttpStatus.BAD_REQUEST, message,errors);
	}


	public BadRequestException(String code, String message) {
		super(HttpStatus.BAD_REQUEST, code, message);
	}

	public BadRequestException(String code, String message, String fieldName) {
		super(HttpStatus.BAD_REQUEST, code, message, fieldName);
	}



}
