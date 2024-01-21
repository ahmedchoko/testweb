package com.wevioo.pi.auth.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class AuthentificationException extends OAuth2Exception {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -3590909724425683256L;
    private   String errorCode;



    @Override
    public String getOAuth2ErrorCode() {
        return "TFA_REQUIRED";
    }
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public int getHttpErrorCode() {
        return 401;
    }
    public AuthentificationException(String msg, String code) {
        super(msg);
        this.errorCode=code;
    }

}
