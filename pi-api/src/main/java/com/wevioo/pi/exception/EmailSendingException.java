package com.wevioo.pi.exception;

import org.springframework.http.HttpStatus;

public class EmailSendingException extends RestException {

    /**
     * Email server error handling
     *
     * @param code error code
     */
    public EmailSendingException(String code) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, code);
    }

    /**
     * Email server error handling with code and message
     *
     * @param code    error code
     * @param message error message
     */
    public EmailSendingException(String code, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
    }
}
