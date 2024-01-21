package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.rest.dto.request.ActivityDeclarationForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ActivityDeclarationValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return ActivityDeclarationForPostDto.class.equals(aClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object o, Errors errors) {
        /**
         * overrided method
         */
    }

    /**
     * Validate Operator for post method
     *
     * @param activityDeclarationForPostDto ActivityDeclarationForPostDto
     * @param errors             Errors
     */
    public static void validateActivityMain(ActivityDeclarationForPostDto activityDeclarationForPostDto, Errors errors) {
            if(activityDeclarationForPostDto == null){
                errors.rejectValue("mainActivityDeclaration", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
        if ( activityDeclarationForPostDto != null && CommonUtilities.isNullOrEmpty(activityDeclarationForPostDto.getActivitySectorId())) {
            errors.rejectValue("mainActivityDeclaration.activitySectorId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
    }



    /**
     * Validate Activity Secondary for post method
     *
     * @param activityDeclarationForPostDto ActivityDeclarationForPostDto
     * @param errors             Errors
     */
    public static void validateActivitySecondary(ActivityDeclarationForPostDto activityDeclarationForPostDto, Errors errors , boolean isActivitySecondary) {
        if(isActivitySecondary && activityDeclarationForPostDto == null){
            errors.rejectValue("activityDeclarationSecondary", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (  isActivitySecondary && activityDeclarationForPostDto != null && CommonUtilities.isNullOrEmpty(activityDeclarationForPostDto.getActivitySectorId())) {
            errors.rejectValue("activityDeclarationSecondary.activitySectorId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
    }

}
