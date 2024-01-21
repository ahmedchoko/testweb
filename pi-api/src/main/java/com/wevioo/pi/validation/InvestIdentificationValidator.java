package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.rest.dto.request.InvestIdentificationForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class InvestIdentificationValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return InvestIdentificationForPostDto.class.equals(aClass);
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
     * @param investIdentificationForPostDto ActivityDeclarationForPostDto
     * @param errors             Errors
     */
    public static void validatePost(InvestIdentificationForPostDto investIdentificationForPostDto, Errors errors , UserTypeEnum userType) {
       if(CommonUtilities.isNullOrEmpty(investIdentificationForPostDto.getFicheInvestId())){
           errors.rejectValue("ficheInvestId", ApplicationConstants.BAD_REQUEST_CODE,
                   ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
       }

        if (CommonUtilities.isNullOrEmpty(investIdentificationForPostDto.getUniqueIdentifier())) {
            errors.rejectValue("uniqueIdentifier", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNullOrEmpty(investIdentificationForPostDto.getCompanyName())) {
            errors.rejectValue("companyName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNull(investIdentificationForPostDto.getLegalFormId())) {
            errors.rejectValue("legalFormId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (investIdentificationForPostDto.isMainActivitySupport() &&
                (investIdentificationForPostDto.getMainActivitySupportList() == null ||
                        investIdentificationForPostDto.getMainActivitySupportList().isEmpty())) {
            errors.rejectValue("mainActivitySupportList", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if( !investIdentificationForPostDto.isMainActivitySupport()
                 && investIdentificationForPostDto.getMainActivitySupportList() != null &&
                ! investIdentificationForPostDto.getMainActivitySupportList().isEmpty() ){
            errors.rejectValue("mainActivitySupportList", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }else if(!investIdentificationForPostDto.isMainActivitySupport()){


            if(UserTypeEnum.INVESTOR.equals(userType) && Boolean.FALSE.equals(investIdentificationForPostDto.isMainActivitySupport())) {
                if (CommonUtilities.isNullOrEmpty(investIdentificationForPostDto.getAuthorityNumber())) {
                    errors.rejectValue("authorityNumber", ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
                }

                if (CommonUtilities.isNullOrEmptyDate(investIdentificationForPostDto.getAuthorityDate())) {
                    errors.rejectValue("authorityDate", ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
                }
            }
        }

        if(CommonUtilities.isNull(investIdentificationForPostDto.isSecondaryActivityDeclaration())){
            errors.rejectValue("secondaryActivityDeclaration", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

    }
}
