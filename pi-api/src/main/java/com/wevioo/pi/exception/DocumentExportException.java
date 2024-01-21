package com.wevioo.pi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public class DocumentExportException extends RestException {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = -3260745311457353808L;

    /**
     * Constructor with code
     *
     * @param code error code
     */
    public
    DocumentExportException(String code) {
        super(HttpStatus.BAD_REQUEST, code);
    }

    /**
     * Constructor with code and message
     *
     * @param code    error code
     * @param message error message
     */
    public
    DocumentExportException(String code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }

    /**
     * Constructor with errors and message
     *
     * @param message error message
     * @param errors  Object of errors
     */
    public
    DocumentExportException(String message, Errors errors) {
        super(HttpStatus.BAD_REQUEST, message, errors);
    }
}
