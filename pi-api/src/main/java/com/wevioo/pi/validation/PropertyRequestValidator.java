package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepOneForPostDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepZeroForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;

public class PropertyRequestValidator {
	
	
	/**
	 * Private Constructor
	 */
	private PropertyRequestValidator() {
		
	}

    public static void validatePost(PropertyRequestStepOneForPostDto propertyRequestStepOneForPostDto, Errors errors) {

        if(CommonUtilities.isNullOrEmpty(propertyRequestStepOneForPostDto.getFicheInvestId())){
            errors.rejectValue("ficheInvestId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (propertyRequestStepOneForPostDto.getPropertyDescription() == null) {
            errors.rejectValue("propertyDescription", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }

    }

    public  static  void validatePropertyRequestRequester(PropertyRequestStepZeroForPostDto propertyRequestStepZeroForPostDto, Errors errors,
                                                          boolean isBanker){
        if (CommonUtilities.isNull(propertyRequestStepZeroForPostDto.getHasRequester())) {
            errors.rejectValue("hasRequester", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if(Boolean.TRUE.equals(propertyRequestStepZeroForPostDto.getHasRequester()) && propertyRequestStepZeroForPostDto.getRequester() == null) {
            errors.rejectValue("requester", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (propertyRequestStepZeroForPostDto.getRequester() != null && !propertyRequestStepZeroForPostDto.getHasRequester()) {
            errors.rejectValue("requester", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_INCOMPATIBLE_DATA  +
                            ApplicationConstants.ERROR_MSG_REPRESENTATIVE_MUST_BE_NULL);
        }
        if (isBanker && propertyRequestStepZeroForPostDto.getInvestorId() == null) {
            errors.rejectValue("investorId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }
    }
}
