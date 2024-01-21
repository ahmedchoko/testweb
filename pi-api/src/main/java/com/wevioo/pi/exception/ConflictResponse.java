package com.wevioo.pi.exception;

import lombok.Data;

@Data
public class ConflictResponse {
	/**
	 * ConflictResponse's path
	 */
	private String path;
	/**
	 * ConflictResponse's error
	 */
	private String error;
	/**
	 * ConflictResponse's message
	 */
	private String message;

	/**
	 * Constructor of ConflictResponse
	 * 
	 * @param path    ConflictResponse's path
	 * @param error   ConflictResponse's error
	 * @param message ConflictResponse's message
	 */
	public ConflictResponse(String path, String error, String message) {
		this.path = path;
		this.error = error;
		this.message = message;
	}
}
