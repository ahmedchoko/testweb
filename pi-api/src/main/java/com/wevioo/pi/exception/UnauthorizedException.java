package com.wevioo.pi.exception;

import org.springframework.http.HttpStatus;

/**
 * Unauthorized exception
 */
public class UnauthorizedException extends RestException {
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -3845612527659235601L;

	/**
	 * Constructor with code
	 * 
	 * @param code error code
	 */
	public UnauthorizedException(String code) {
		super(HttpStatus.UNAUTHORIZED, code);
	}
	public UnauthorizedException(String code,String message) {
		super(HttpStatus.UNAUTHORIZED, code,message);
	}
}
