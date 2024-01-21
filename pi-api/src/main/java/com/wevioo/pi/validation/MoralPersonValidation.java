package com.wevioo.pi.validation;


import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.domain.entity.referential.RefCodificationPK;
import com.wevioo.pi.repository.CodificationRepository;
import com.wevioo.pi.repository.IdentificationTypeRepository;
import com.wevioo.pi.rest.dto.request.CodificationForPostDto;
import com.wevioo.pi.service.ParameterService;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Map;

@Component
public class MoralPersonValidation {


    @Autowired
    IdentificationTypeRepository identificationTyprepository;

    @Autowired
    CodificationRepository codificationRepository;

    @Autowired
    ParameterService  parameterService ;

    public void validateMoralPerson (CodificationForPostDto codificationForPostDto, Errors errors){



        // unique identification validation
       boolean exist =  codificationRepository.existsById(new RefCodificationPK(codificationForPostDto.getUniqueIdentifier(),ApplicationConstants.MATRICULE_FISCAL ));
       if (exist){
           errors.rejectValue("uniqueIdentifier", ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_UNIQUE_IDENTIFICATION_FOUND);
       }
        // get parameters patterns
        Map<String, Parameter> getMapParamByPrefix  = parameterService.getMapParamByPrefix("codification");
        // social reason validation : length and required
        CommonsValidation.validateNullValue("socialReason",
                codificationForPostDto.getSocialReason(), errors);
        ///CommonUtilities.isValidFormat(isValidFormat)

        // social reason validation uppercase alphanumeric
        if (!CommonUtilities.isValidFormat(codificationForPostDto.getSocialReason(),getMapParamByPrefix.get(ApplicationConstants.SOCIALREASON).getParameterPattern())) {
            errors.rejectValue("socialReason", ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_INVALID_SOCIAL_REASON_FORMAT);
        }

        // address validation : required
        CommonsValidation.validateNullValue("address",
                codificationForPostDto.getAddress(), errors);

        // address validation uppercase alphanumeric
        if (!CommonUtilities.isValidFormat(codificationForPostDto.getAddress(),getMapParamByPrefix.get(ApplicationConstants.ADDRES).getParameterPattern())) {
            errors.rejectValue("address", ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_INVALID_ADDRESS_FORMAT);
        }

    }
}
