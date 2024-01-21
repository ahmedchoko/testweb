package com.wevioo.pi.auth.exception;

import org.springframework.http.HttpStatus;

/**
 * The {@code BadRequestException} class represents an exception that should be thrown when a client's request is deemed

 *
 * @see RestException
 */
public class BadRequestException extends RestException {

    private static final long serialVersionUID = -3260745311457353808L;
    /**
     * Constructs a new {@code BadRequestException} with the provided error code and error message.
     *
     * @param code    The error code associated with the exception.
     * @param message A descriptive error message providing additional details about the exception.
     */
    public BadRequestException(String code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}
