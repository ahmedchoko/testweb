package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.request.PropertyDescriptionForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PropertyDescriptionValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return PropertyDescriptionForPostDto.class.equals(aClass);
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


    public static void validatePost(PropertyDescriptionForPostDto propertyDescriptionForPostDto, Errors errors,
                                    String investTypeAqcTerValue, boolean isBanker) {



        if(propertyDescriptionForPostDto== null){
            errors.rejectValue("propertyDescription", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            throw  new BadRequestException("400", errors);
        }
        if (CommonUtilities.isNull(propertyDescriptionForPostDto.getInvestTypeId())) {
            errors.rejectValue("propertyDescription.investTypeId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNull(propertyDescriptionForPostDto.getVocationId()) &&
                investTypeAqcTerValue != null &&
                propertyDescriptionForPostDto.getInvestTypeId().equals(Long.valueOf(investTypeAqcTerValue))) {
            errors.rejectValue("propertyDescription.vocationId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNull(propertyDescriptionForPostDto.getLocationId())) {
            errors.rejectValue("propertyDescription.locationId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNull(propertyDescriptionForPostDto.getUsageId())) {
            errors.rejectValue("propertyDescription.usageId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNullOrEmpty(propertyDescriptionForPostDto.getAddress())) {
            errors.rejectValue("propertyDescription.address", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNullOrEmpty(propertyDescriptionForPostDto.getGovernorateId())) {
            errors.rejectValue("propertyDescription.governorateId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (isBanker && CommonUtilities.isNullOrEmpty(propertyDescriptionForPostDto.getDelegationId())) {
            errors.rejectValue("propertyDescription.delegationId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (isBanker && CommonUtilities.isNullOrEmpty(propertyDescriptionForPostDto.getLocalityId())) {
            errors.rejectValue("propertyDescription.localityId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (isBanker && CommonUtilities.isNullOrEmpty(propertyDescriptionForPostDto.getZipCodeId())) {
            errors.rejectValue("propertyDescription.zipCodeId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNull(propertyDescriptionForPostDto.getIsRegistered())) {
            errors.rejectValue("propertyDescription.isRegistered", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (Boolean.TRUE.equals(propertyDescriptionForPostDto.getIsRegistered()) &&
                CommonUtilities.isNull(propertyDescriptionForPostDto.getLandTitleNumber())) {
            errors.rejectValue("propertyDescription.landTitleNumber", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (Boolean.TRUE.equals(propertyDescriptionForPostDto.getIsRegistered()) &&
                CommonUtilities.isNullOrEmpty(propertyDescriptionForPostDto.getNameOfLandTitle())) {
            errors.rejectValue("propertyDescription.nameOfLandTitle", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }


    }
}
