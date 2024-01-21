package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.request.CurrencyInvestmentFinancingDto;
import com.wevioo.pi.rest.dto.request.CurrencyFinancingForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class CurrencyFinancingValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return CurrencyInvestmentFinancingDto.class.equals(aClass);
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

    public static void validatePost(CurrencyFinancingForPostDto requestStepFour, Errors errors ,UserTypeEnum userType) {
        if (CommonUtilities.isNullOrEmpty(requestStepFour.getFicheInvestId())) {
            errors.rejectValue("ficheInvestId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (requestStepFour. getCurrencyFinancingData() == null ||
                requestStepFour.getCurrencyFinancingData().isEmpty()) {
            errors.rejectValue("currencyFinancingData", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            if (errors.hasErrors()) {
                throw new BadRequestException("400", errors);
            }
        }
        validateCurrencyDataPost(requestStepFour.getCurrencyFinancingData(), errors, userType);
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }
    }


    public static void validateCurrencyDataPost(List<CurrencyInvestmentFinancingDto> currencyInvestmentFinancingDtoList, Errors errors ,UserTypeEnum userType) {
        for (int i = 0; i < currencyInvestmentFinancingDtoList.size(); i++) {
            CurrencyInvestmentFinancingDto currencyInvestmentFinancingDto = currencyInvestmentFinancingDtoList.get(i);

            if (CommonUtilities.isNullOrEmpty(currencyInvestmentFinancingDto.getBankId()) && UserTypeEnum.BANKER.equals(userType)) {
                errors.rejectValue("currencyFinancingData[" + i + "].bankId",
                        ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }  /* Validation called if current User is a Banker */

            if (CommonUtilities.isNull(currencyInvestmentFinancingDto.getFinancialModeId())) {
                errors.rejectValue("currencyFinancingData[" + i + "].financialModeId",
                        ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

            if (CommonUtilities.isNull(currencyInvestmentFinancingDto.getImportationPieceId())) {
                errors.rejectValue("currencyFinancingData[" + i + "].importationPieceId",
                        ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

            if (CommonUtilities.isNull(currencyInvestmentFinancingDto.getImportedAmount())) {
                errors.rejectValue("currencyFinancingData[" + i + "].importedAmount",
                        ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

            if (CommonUtilities.isNullOrEmpty(currencyInvestmentFinancingDto.getFinancialCurrencyId())) {
                errors.rejectValue("currencyFinancingData[" + i + "].financialCurrencyId",
                        ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

            if (CommonUtilities.isNull(currencyInvestmentFinancingDto.getCurrencyCession())) {
                errors.rejectValue("currencyFinancingData[" + i + "].currencyCession",
                        ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

            if (Boolean.TRUE.equals(currencyInvestmentFinancingDto.getCurrencyCession()) &&
                    CommonUtilities.isNullOrEmptyDate(currencyInvestmentFinancingDto.getCessionDate())) {
                errors.rejectValue("currencyFinancingData[" + i + "].cessionDate",
                        ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

            // Handle null cessionExchangeRate separately
            if (Boolean.TRUE.equals(currencyInvestmentFinancingDto.getCurrencyCession()) &&
                    CommonUtilities.isNull(currencyInvestmentFinancingDto.getCessionExchangeRate())) {
                errors.rejectValue("currencyFinancingData[" + i + "].cessionExchangeRate",
                        ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
        }

        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }


    }
}
