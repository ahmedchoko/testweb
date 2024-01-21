package com.wevioo.pi.validation;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.referential.IdentificationType;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.repository.IdentificationTypeRepository;
import com.wevioo.pi.rest.dto.request.RequesterForPostDto;
import com.wevioo.pi.utility.AuditUtil;
import com.wevioo.pi.utility.CommonUtilities;

/**
 *  Requester validation class
 *
 *
 */
@Component
public class RequesterValidation {



    private RequesterValidation() {
        super();
    }

    public static void validateRequester(RequesterForPostDto requester ,   Errors errors) {
        IdentificationTypeRepository identificationTypeRepository = AuditUtil.getBean(IdentificationTypeRepository.class);
        if (CommonUtilities.isNullOrEmpty(requester.getIdentificationType())) {
            errors.rejectValue("requester.identificationType", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }else {
            Optional<IdentificationType> identificationType =   identificationTypeRepository.findByType(requester.getIdentificationType());
             if(!identificationType.isPresent()) {
                 throw new DataNotFoundException(ApplicationConstants.ERROR_IDENTIFICATION_TYPE_NOT_FOUND_REF,
                         ApplicationConstants.ERROR_IDENTIFICATION_TYPE_NOT_FOUND_REF  );
             } else {

                 if (CommonUtilities.isNullOrEmpty( requester.getIdentificationValue())) {
                     errors.rejectValue( "requester.identificationValue",
                             ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
                 }
                 if (!CommonUtilities.isValidFormat(requester.getIdentificationValue(), identificationType.get().getPattern())) {
                     errors.rejectValue("requester.identificationValue",
                             ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_INVALID_IDENTIFICATION_VALUE);
                 }

                 if(ApplicationConstants.CIN.equals(identificationType.get().getType()) ||
                    ApplicationConstants.PASSPORT.equals(identificationType.get().getType())){
                     if (CommonUtilities.isNullOrEmpty(requester.getFirstName())) {
                         errors.rejectValue("requester.firstName", ApplicationConstants.BAD_REQUEST_CODE,
                                 ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
                     }

                     if (CommonUtilities.isNullOrEmpty(requester.getLastName())) {
                         errors.rejectValue("requester.lastName", ApplicationConstants.BAD_REQUEST_CODE,
                                 ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
                     }
                 }else if(ApplicationConstants.IDENTFIANT_UNIQUE.equals(identificationType.get().getType())) {
                     if (CommonUtilities.isNullOrEmpty(requester.getSocialReason())) {
                         errors.rejectValue("requester.socialReason", ApplicationConstants.BAD_REQUEST_CODE,
                                 ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
                     }
                 }
             }
        }



        if (CommonUtilities.isNullOrEmpty(requester.getIdentificationValue())) {
            errors.rejectValue("requester.identificationValue", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (CommonUtilities.isNullOrEmpty(requester.getEmail())) {
            errors.rejectValue("requester.email", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (CommonUtilities.isNullOrEmpty(requester.getAddress())) {
            errors.rejectValue("requester.address", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNullOrEmpty(requester.getPhoneNumber())) {
            errors.rejectValue("requester.phoneNumber", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        CommonsValidation.validatePhoneNumber("requester.phoneNumber", errors, requester.getPhoneNumber(), 15);
        CommonsValidation.emailValidation("requester.email", errors, requester.getEmail(), 150);
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }

    }
}
