package com.wevioo.pi.validation;


import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.referential.IdentificationType;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;



@Component
public class IdentificationTypeValidator {



    /**
     * @param identificationType
     * @param identificationValue
     * @param errors
     */
    public void validateIdentificationType( IdentificationType identificationTypeExist , String identificationType , String identificationValue , Errors errors) {

        // validate identification Type is not null
        if (CommonUtilities.isNullOrEmpty(identificationType)) {
            errors.rejectValue("identificationType", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_IDENTIFICATION_TYPE_NOT_FOUND);
        } else {
            if (identificationTypeExist == null) {
                errors.rejectValue("identificationType", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_IDENTIFICATION_TYPE_NOT_FOUND_REF);
            }
            else {
                /// identification Value validation : required , respect pattern
                if (CommonUtilities.isNullOrEmpty(identificationValue)) {
                    errors.rejectValue(ApplicationConstants.REQUESTER_INFORMTION_IDENTIFICATION_VALUE,
                            ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
                }
                if (!CommonUtilities.isValidFormat(identificationValue, identificationTypeExist.getPattern())) {
                    errors.rejectValue(ApplicationConstants.REQUESTER_INFORMTION_IDENTIFICATION_VALUE,
                            ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_INVALID_IDENTIFICATION_VALUE);
                }
            }
        }


    }
}
