package com.wevioo.pi.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.utility.CommonUtilities;
public final class CommonsValidation {

    public static void emailValidation(String attribute, Errors errors, String email, int maxLength){
        if (CommonUtilities.isNullOrEmpty(email)) {
            errors.rejectValue(attribute, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else {
            if (!CommonUtilities.isValidEmail(email)) {
                errors.rejectValue(attribute, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_INVALID_EMAIL_FORMAT);
            }
            if (!CommonUtilities.isValidLength(email, maxLength)) {
                errors.rejectValue(attribute, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }
        }
    }

    public static void validatePhoneNumber(String attribute,Errors errors, String phoneNumber, int maxLength){
        if (CommonUtilities.isNullOrEmpty(phoneNumber)) {
            errors.rejectValue(attribute, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else {
            if (!CommonUtilities.isValidPhoneNumber(phoneNumber)) {
                errors.rejectValue(attribute, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_INVALID_PHONE_FORMAT);
            }
            if (!CommonUtilities.isValidLength(phoneNumber, maxLength)) {
                errors.rejectValue(attribute,ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }
        }
    }

    public static void validateNullValue(String fieldName, String input,  Errors errors) {
        if (CommonUtilities.isNullOrEmpty(input)) {
            errors.rejectValue(fieldName, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_UNEXPECTED_NULL_VALUE);
        }
    }

    public static void validateNullValue(String fieldName, Boolean input,  Errors errors) {
        if (CommonUtilities.isNull(input)) {
            errors.rejectValue(fieldName, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_UNEXPECTED_NULL_VALUE);
        }
    }

    public static void validateNullValue(String fieldName, Long input,  Errors errors) {
        if (CommonUtilities.isNull(input)) {
            errors.rejectValue(fieldName, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_UNEXPECTED_NULL_VALUE);
        }
    }


    public static void validateNullValueAndValidLength(String fieldName, String input, int maxLength, Errors errors) {
        if (CommonUtilities.isNullOrEmpty(input)) {
            errors.rejectValue(fieldName, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        else if (!CommonUtilities.isValidLength(input,maxLength)) {
            errors.rejectValue(fieldName, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
    }

    public static void validateFloatInput(String fieldName, Float input, int minLength, int maxLength, Errors errors) {
        if (CommonUtilities.isNull(input)) {
            errors.rejectValue(fieldName, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        else if (!CommonUtilities.isValidDecimalNumber(input, maxLength, minLength)) {
            errors.rejectValue(fieldName, ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }

    }

    public static void validatePassword(String pwd ,String fieldName, Errors errors ){
        if(!CommonUtilities.isValidPassword(pwd)){
            errors.rejectValue(fieldName,ApplicationConstants.BAD_REQUEST_CODE,ApplicationConstants.ERROR_INVALID_PASSWORD_FORMAT);
        }
    }

    public static void validateCommon(String label, String longLabel, BindingResult result) {
        if (CommonUtilities.isNullOrEmpty(label)) {
            result.rejectValue("label", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNullOrEmpty(longLabel)) {
            result.rejectValue("longLabel", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (result.hasErrors()) {
            throw new BadRequestException("400", result);
        }
    }

    public static void validateCommon(String label, String longLabel, Long id, String labelOperation, BindingResult result) {
        if (CommonUtilities.isNullOrEmpty(label)) {
            result.rejectValue("label", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (id == null) {
            result.rejectValue(labelOperation, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNullOrEmpty(longLabel)) {
            result.rejectValue("longLabel", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (result.hasErrors()) {
            throw new BadRequestException("400", result);
        }
    }

    public boolean validatePattern(String str, String pattern) {
        return CommonUtilities.isValidFormat(str, pattern)	;
    }

}
