package com.wevioo.pi.validation;


import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.BctAgentForPostDto;
import com.wevioo.pi.rest.dto.BctAgentForPutDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
/**
 * BctAgentValidation validation class
 *
 * @author knh
 *
 */

@Component
public class BctAgentValidation  {

    private BctAgentValidation(){
        super();
    }

    /**
     * Validate BctAgentDto for both POST and PUT methods.
     *
     * @param bctAgentDto BctAgentDto
     * @param errors      Errors
     */
    public static void validateBctAgentForPost(BctAgentForPostDto bctAgentDto, Errors errors ) {


        if(CommonUtilities.isNull(bctAgentDto.getLanguage())) {
            errors.rejectValue("language", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
         if (CommonUtilities.isNullOrEmpty(bctAgentDto.getFirstName())) {
            errors.rejectValue(ApplicationConstants.FIRST_NAME, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bctAgentDto.getFirstName(), 50)) {
            errors.rejectValue(ApplicationConstants.FIRST_NAME, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
        if (CommonUtilities.isNullOrEmpty(bctAgentDto.getLastName())) {
            errors.rejectValue(ApplicationConstants.LAST_NAME, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bctAgentDto.getLastName(), 50)) {
            errors.rejectValue(ApplicationConstants.LAST_NAME, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }

        if (CommonUtilities.isNullOrEmpty(bctAgentDto.getGeneralManagementAssignment())) {
            errors.rejectValue( ApplicationConstants.GENERAL_MANAGEMENT_ASSIGNMENT, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bctAgentDto.getLastName(), 50)) {
            errors.rejectValue(ApplicationConstants.GENERAL_MANAGEMENT_ASSIGNMENT, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }

        if (CommonUtilities.isNullOrEmpty(bctAgentDto.getServiceAssignment())) {
            errors.rejectValue(ApplicationConstants.SERVICE_ASSIGNMENT, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bctAgentDto.getLastName(), 50)) {
            errors.rejectValue(ApplicationConstants.SERVICE_ASSIGNMENT, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
        if(CommonUtilities.isNull(bctAgentDto.getIsActive())){
            errors.rejectValue("isActive", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        CommonsValidation.emailValidation("email", errors, bctAgentDto.getEmail(), 150);

            if (CommonUtilities.isNullOrEmpty(bctAgentDto.getRegistrationNumber())) {
                errors.rejectValue("registrationNumber", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            } else if (!CommonUtilities.isValidLength(bctAgentDto.getLastName(), 50)) {
                errors.rejectValue("registrationNumber", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }

            if (CommonUtilities.isNullOrEmpty(bctAgentDto. getPassword())) {
                errors.rejectValue(ApplicationConstants.PASSWORD, ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            if (CommonUtilities.isNullOrEmpty(bctAgentDto. getPasswordConfirmation())) {
                errors.rejectValue("passwordConfirmation", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            CommonsValidation.validatePassword(bctAgentDto.getPasswordConfirmation(),"passwordConfirmation" , errors  );
            CommonsValidation.validatePassword(bctAgentDto.getPassword(),ApplicationConstants.PASSWORD , errors  );

            if(!(bctAgentDto.getPassword() != null && bctAgentDto.getPasswordConfirmation() != null &&
                    bctAgentDto.getPassword().equals(bctAgentDto.getPasswordConfirmation())) ){
                errors.rejectValue(ApplicationConstants.PASSWORD, ApplicationConstants.ERROR_MISSING_CONFIRMATION_PASSWORD,
                        ApplicationConstants.ERROR_MISSING_CONFIRMATION_PASSWORD);
            }
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }

    }


    /**
     * Validate BctAgentDto for both POST and PUT methods.
     *
     * @param bctAgentDto BctAgentDto
     * @param errors      Errors
     */
    public static void validateBctAgentForPut(BctAgentForPutDto bctAgentDto, Errors errors ) {

        if (CommonUtilities.isNullOrEmpty(bctAgentDto.getFirstName())) {
            errors.rejectValue(ApplicationConstants.FIRST_NAME, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bctAgentDto.getFirstName(), 50)) {
            errors.rejectValue(ApplicationConstants.FIRST_NAME, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
        if (CommonUtilities.isNullOrEmpty(bctAgentDto.getLastName())) {
            errors.rejectValue(ApplicationConstants.LAST_NAME, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bctAgentDto.getLastName(), 50)) {
            errors.rejectValue(ApplicationConstants.LAST_NAME, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
        if (CommonUtilities.isNullOrEmpty(bctAgentDto.getGeneralManagementAssignment())) {
            errors.rejectValue(ApplicationConstants.GENERAL_MANAGEMENT_ASSIGNMENT, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bctAgentDto.getLastName(), 50)) {
            errors.rejectValue(ApplicationConstants.GENERAL_MANAGEMENT_ASSIGNMENT, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
        if (CommonUtilities.isNullOrEmpty(bctAgentDto.getServiceAssignment())) {
            errors.rejectValue(ApplicationConstants.SERVICE_ASSIGNMENT, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bctAgentDto.getLastName(), 50)) {
            errors.rejectValue(ApplicationConstants.SERVICE_ASSIGNMENT, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
        if(CommonUtilities.isNull(bctAgentDto.getIsActive())){
            errors.rejectValue("isActive", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        CommonsValidation.emailValidation("email", errors, bctAgentDto.getEmail(), 150);
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }

    }




}
