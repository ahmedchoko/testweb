package com.wevioo.pi.exception;

import org.springframework.http.HttpStatus;

public class MissingFileException extends RestException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1810232097765640500L;

	public MissingFileException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
