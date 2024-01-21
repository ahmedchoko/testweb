package com.wevioo.pi.validation;


import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.referential.IdentificationType;
import com.wevioo.pi.domain.enumeration.PersonType;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.repository.MainContractualTermsRepository;
import com.wevioo.pi.rest.dto.request.MainContractualTermsStepTwoForPutDto;
import com.wevioo.pi.service.IdentificationTypeService;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MainContractualTermsValidator {



    /**
     * Injected bean {@link MainContractualTermsRepository}
     */
    @Autowired
    private IdentificationTypeValidator validator;

    /**
     * Injected bean {@link IdentificationTypeService}
     */
    @Autowired
    private IdentificationTypeService identificationTypeService;

    /**
     * validate Step Two For Post
     *
     * @param requestStepTwo
     * @param errors
     */
    public void validateStepTwoForPost(MainContractualTermsStepTwoForPutDto requestStepTwo, Errors errors) {


        if (CommonUtilities.isNullOrEmpty(requestStepTwo.getIdPropertyRequest())) {
            errors.rejectValue("idPropertyRequest",ApplicationConstants.BAD_REQUEST_CODE , ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        validateRegistrationContractDate(requestStepTwo, errors);
        // Validate Person Type required
        if (requestStepTwo.getPersonType() == null) {
            errors.rejectValue("personType", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else {
            if (PersonType.PP == requestStepTwo.getPersonType()) {
                validatePhysicPerson(requestStepTwo, errors);
            }
            if (PersonType.PM == requestStepTwo.getPersonType()) {
                validateMoralPerson(requestStepTwo, errors);
            }

        }

        // Investor Part validation : Decimal Number and required
        if (CommonUtilities.isNull(requestStepTwo.getInvestorPart())) {
            errors.rejectValue(ApplicationConstants.INVESTORPART, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        else if (!CommonUtilities.isValidDecimalNumber(requestStepTwo.getInvestorPart(), 3, 3)) {
            errors.rejectValue(ApplicationConstants.INVESTORPART, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }

        // Amount TND Property Acquitted validation :Big Decimal Number and required
        if (CommonUtilities.isNull(requestStepTwo.getAmountTNDPropertyAcquitted())) {
            errors.rejectValue(ApplicationConstants.AMOUNTTNDPROPERTYACQUITED, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        else if (!CommonUtilities.isValidBigDecimalNumber(requestStepTwo.getAmountTNDPropertyAcquitted(), 20, 3)) {
            errors.rejectValue(ApplicationConstants.AMOUNTTNDPROPERTYACQUITED, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
        // Amount Cash Contributed validation : Big Decimal Number and required

        if (CommonUtilities.isNull(requestStepTwo.getAmountCashContributed())) {
                errors.rejectValue(ApplicationConstants.AMOUNTCASHCONTRIBUTED, ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);

        }
        else if (!CommonUtilities.isValidBigDecimalNumber(requestStepTwo.getAmountCashContributed(), 30, 3)){
            errors.rejectValue(ApplicationConstants.AMOUNTCASHCONTRIBUTED, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }


        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }
    }


    /**
     * @param requestStepTwo
     * @param errors
     */
    public void validateMoralPerson(MainContractualTermsStepTwoForPutDto requestStepTwo, Errors errors) {

        // validate identification Type Existence in Identification  Type reference
        IdentificationType identificationTypeExist = identificationTypeService
                .findByType(requestStepTwo.getIdentificationType());
        // social reason validation : length and required
        CommonsValidation.validateNullValueAndValidLength("socialReason",
                requestStepTwo.getSocialReason(), 90, errors);

        // social reason validation alphanumeric
        if (!CommonUtilities.isValidAlphaNumeriqueValue(requestStepTwo.getSocialReason())) {
            errors.rejectValue("socialReason", ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_INVALID_SOCIAL_REASON_FORMAT);
        }
        // validate identification Type is not null
        if (CommonUtilities.isNullOrEmpty(requestStepTwo.getIdentificationType())) {
            errors.rejectValue("identificationType", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_IDENTIFICATION_TYPE_NOT_FOUND);
        }
        else
        {
            // validation Identification Type
            if(ApplicationConstants.IDENTIFIANT_UNIQUE.equals(requestStepTwo.getIdentificationType())){
                //  identification validation :  length , alphanumeric
                validator.validateIdentificationType(identificationTypeExist , requestStepTwo.getIdentificationType(),requestStepTwo.getUniqueIdentifier() ,errors);

            }
            else{
                errors.rejectValue("identificationType", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_IDENTIFICATION_TYPE_NOT_CORRESPOND_PM);
            }
        }

    }

    /**
     * @param requestStepTwo
     * @param errors
     */
    public void validatePhysicPerson(MainContractualTermsStepTwoForPutDto requestStepTwo, Errors errors) {

        // validate identification Type Existence in Identification  Type reference
        IdentificationType identificationTypeExist = identificationTypeService
                .findByType(requestStepTwo.getIdentificationType());

        // nationality validation : required
        if (requestStepTwo.getIsTunisian() == null) {
            errors.rejectValue("isTunisian", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        // validation Identification Type
        validator.validateIdentificationType(identificationTypeExist , requestStepTwo.getIdentificationType(),requestStepTwo.getUniqueIdentifier() ,errors);

        // firstname validation : length
        CommonsValidation.validateNullValueAndValidLength("firstName",
                requestStepTwo.getFirstName(), 60, errors);

        // lastname validation : length
        CommonsValidation.validateNullValueAndValidLength("lastName",
                requestStepTwo.getLastName(), 60, errors);

    }

    /**
     * @param requestStepTwo
     * @param errors
     */
    // Validate registration Contract Date With Actual Date and required
    private void validateRegistrationContractDate(MainContractualTermsStepTwoForPutDto requestStepTwo, Errors errors) {

        // Validate registration Contract Date : required and compared to actual date
        if (CommonUtilities.isNullOrEmptyDate(requestStepTwo.getRegistrationContractDate())) {
            errors.rejectValue("registrationContractDate", ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.compareToActualDate(requestStepTwo.getRegistrationContractDate())) {
            errors.rejectValue("registrationContractDate", ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_ACTUAL_DATE);
        }
    }

}
