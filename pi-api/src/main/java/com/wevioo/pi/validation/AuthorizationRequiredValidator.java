package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.request.AuthorizationsRequiredForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AuthorizationRequiredValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return AuthorizationsRequiredForPostDto.class.equals(aClass);
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

    public static void validatePost(AuthorizationsRequiredForPostDto requestStepThree, Errors errors, boolean isBanker) {
        if (CommonUtilities.isNullOrEmpty(requestStepThree.getFicheInvestId())) {
            errors.rejectValue("ficheInvestId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNull(requestStepThree.getGovernorAuthorisationNumber())) {
            errors.rejectValue("governorAuthorisationNumber", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNullOrEmptyDate(requestStepThree.getGovernorAuthorisationDate())) {
            errors.rejectValue("governorAuthorisationDate", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNull(requestStepThree.getAuthorisationBCT())) {
            errors.rejectValue("authorisationBCT", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (isBanker && !CommonUtilities.isNull(requestStepThree.getAuthorisationBCT()) &&
                Boolean.TRUE.equals(requestStepThree.getAuthorisationBCT()) &&
        CommonUtilities.isNullOrEmpty(requestStepThree.getReferenceAuthorisationBCT())) {
            errors.rejectValue("referenceAuthorisationBCT", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (isBanker && !CommonUtilities.isNull(requestStepThree.getAuthorisationBCT()) &&
                Boolean.TRUE.equals(requestStepThree.getAuthorisationBCT()) &&
                CommonUtilities.isNullOrEmptyDate(requestStepThree.getAuthorisationBCTDate())) {
            errors.rejectValue("authorisationBCTDate", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }
    }

}
