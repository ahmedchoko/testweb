package com.wevioo.pi.auth.common;

/**
 * The {@code ApplicationConstants} class contains constant values used in the authorization service.
 * These constants include various strings such as passwords, grant types, and client information.
 * This class is intended to be used for storing and accessing constant values throughout the application.
 */
public final class ApplicationConstants {

    private ApplicationConstants() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * Constant representing the string "password" used in authentication.
     */
    public   static  final  String  PASSWORD = "password";
    public   static  final  String   PROFILE = "profile";
    /**
     * Constant representing the string "refresh_token" used for token refreshing.
     */
    public   static  final  String  REFRESH_TOKEN  = "refresh_token";

    /**
     * Constant representing the string "MISSING_REQUIRED_DATA" used for indicating missing required data.
     */
    public static  final  String MISSING_REQUIRED_DATA ="MISSING_REQUIRED_DATA";
    /**
     * Constant representing the string "grant_type" used in authentication.
     */
    public static  final  String  GRANT_TYPE ="grant_type";
    /**
     * Constant representing the string "username" used in authentication.
     */
    public static  final  String   USERNAME ="username";
    /**
     * Constant representing the string "revoke_token" used for token revocation.
     */
    public static  final  String    REVOKE_TOKEN ="revoke_token";
    /**
     * Constant representing the OAuth2 client name as "wevioo".
     */
    public  static  final  String   AUTH2_CLIENT = "wevioo" ;

    /**
     * Constant representing the client's secret (password) with a "{noop}" prefix for plain text.
     */
    public  static  final String     SECRET  = "{noop}wevioo@@2024";
    
    
    /**
     * Constant representing the error message 'Could not authenticate user'.
     */
    public  static  final String MESSAGE_ERROR_M1 ="Could not authenticate user:";

    /**
     * Constant representing the password key.
     */
    public  static  final String PASSWORD_KEY ="password";



}
