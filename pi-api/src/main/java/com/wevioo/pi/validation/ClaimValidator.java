package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.ClaimForPostDto;
import com.wevioo.pi.rest.dto.ClaimPublicForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class ClaimValidator {

    private ClaimValidator() {
        super();
    }

    /**
     * Validate Claim for method post
     *
     * @param claim  ClaimForPostDto
     * @param errors Errors
     */
    public static void validateClaim(ClaimForPostDto claim, Errors errors) {


        if (CommonUtilities.isNullOrEmpty(claim.getCellPhone())) {
            errors.rejectValue("cellPhone", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }


        if (CommonUtilities.isNullOrEmpty(claim.getDescription())) {
            errors.rejectValue("description", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }


        if (CommonUtilities.isNullOrEmpty(claim.getEmail())) {
            errors.rejectValue("email", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }


        CommonsValidation.validatePhoneNumber("cellPhone", errors, claim.getCellPhone(), 15);
        CommonsValidation.emailValidation("email", errors, claim.getEmail(), 150);
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }

    }

    /**
     * Validate Claim for method post public
     *
     * @param claim  ClaimPublicForPostDto
     * @param errors Errors
     */
    public static void validateClaimPublic(ClaimPublicForPostDto claim, Errors errors) {


        if (CommonUtilities.isNullOrEmpty(claim.getDescription())) {
            errors.rejectValue("description", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }


        if (CommonUtilities.isNullOrEmpty(claim.getEmail())) {
            errors.rejectValue("email", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (!CommonUtilities.isNullOrEmpty(claim.getCellPhone()))
        {
            CommonsValidation.validatePhoneNumber("cellPhone", errors, claim.getCellPhone(), 15);
        }
        CommonsValidation.emailValidation("email", errors, claim.getEmail(), 150);
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }

    }

}
