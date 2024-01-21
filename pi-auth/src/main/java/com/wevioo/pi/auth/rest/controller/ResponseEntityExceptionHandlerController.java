package com.wevioo.pi.auth.rest.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.wevioo.pi.auth.exception.AuthentificationException;
import com.wevioo.pi.auth.exception.BadRequestException;
import com.wevioo.pi.auth.exception.BadRequestResponse;
import com.wevioo.pi.auth.exception.ExceptionResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



/**
 * Rest Exception handler
 */
@ControllerAdvice
public class ResponseEntityExceptionHandlerController extends ResponseEntityExceptionHandler {

    /**
     * RestException handler: construct and return <code>ExceptionData</code> json
     * response object.
     *
     * @see ExceptionData
     * @param ex      : RestException
     * @param request : WebRequest
     * @return ResponseEntity
     */
    @ExceptionHandler({ OAuth2Exception.class })
    public ResponseEntity<Object> handleRestException(OAuth2Exception ex, WebRequest request) {

        return new ResponseEntity<>(new DataError(ex.getHttpErrorCode(), ex.getOAuth2ErrorCode(), ex.getMessage()), new HttpHeaders(), HttpStatus.valueOf(ex.getHttpErrorCode()));

    }

    /**
     * AuthentificationException handler: construct and return
     * <code>ExceptionData</code> json response object.
     *
     * @param ex      AuthentificationException
     * @param request WebRequest
     * @return ResponseEntity
     */

    @ExceptionHandler({ AuthentificationException.class })
    public ResponseEntity<Object> handleRestException(AuthentificationException ex, WebRequest request) {

        return new ResponseEntity<>(new DataError(HttpStatus.UNAUTHORIZED.value(), ex.getErrorCode(),
                ex.getMessage()), new HttpHeaders(), HttpStatus.UNAUTHORIZED);

    }

    /**
     * InvalidGrantException handler: construct and return
     * <code>ExceptionData</code> json response object.
     *
     * @param ex      InvalidGrantException
     * @param request WebRequest
     * @return ResponseEntity
     */
    @ExceptionHandler({ InvalidGrantException.class })
    public ResponseEntity<Object> handleRestException(InvalidGrantException ex, WebRequest request) {

        return new ResponseEntity<>(new DataError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), ex.getMessage()), new HttpHeaders(), HttpStatus.UNAUTHORIZED);

    }



    /**
     * Json Object to return when exception is throwing
     */
    @Getter
    @Setter
    class DataError {

        private int status;
        private String error;
        private String message;

        public DataError(int httpStatus, String error, String message) {
            this.status = httpStatus;
            this.error = error;
            this.message = message;
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    /**
     * BadRequestException handler: construct and return <code>ExceptionData</code>
     * json response object.
     *
     * @param ex      BadRequestException
     * @param request WebRequest
     * @return ResponseEntity
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<BadRequestResponse> handleUserBadRequestException(BadRequestException ex, HttpServletRequest req) {
        return ExceptionResult.generateBadRequestException(ex.getErrors(), req.getRequestURI());
    }

    /**
     * Json Object to return when exception is throwing
     */
    @Getter
    @Setter
    class Data {

        private int status;


        public Data(int httpStatus) {
            this.status = httpStatus;
        }
    }
}
