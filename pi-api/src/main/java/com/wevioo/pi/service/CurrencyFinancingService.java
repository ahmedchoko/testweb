package com.wevioo.pi.service;

import com.wevioo.pi.rest.dto.request.CurrencyFinancingForPostDto;
import com.wevioo.pi.rest.dto.response.CurrencyFinancingForGetDto;
import org.springframework.validation.BindingResult;

public interface CurrencyFinancingService {
    /**
     * Saves property request currency financing information.
     *
     * @param currencyFinancingForPostDto The data for property request currency financing.
     * @param result                      The binding result for validation.
     * @return The CurrencyFinancingForGetDto after saving the property request currency financing information.
     */
    CurrencyFinancingForGetDto savePropertyRequestCurrencyFinancingInformation(CurrencyFinancingForPostDto currencyFinancingForPostDto,
                                                                               BindingResult result);
    /**
     * Retrieves property request currency financing information based on the Fiche Invest ID.
     *
     * @param ficheInvestId The unique identifier used to retrieve property request currency financing information.
     * @return The CurrencyFinancingForGetDto corresponding to the provided Fiche Invest ID.
     */
     CurrencyFinancingForGetDto getPropertyRequestCurrencyFinancingInformation(String ficheInvestId);
    /**
     * Retrieves currency financing information based on the provided Fiche Invest ID.
     *
     * @param ficheInvestId The unique identifier used to retrieve currency financing information.
     * @return The CurrencyFinancingForGetDto corresponding to the provided Fiche Invest ID.
     */
    CurrencyFinancingForGetDto getCurrencyFinancingInformationById(String ficheInvestId);
    /**
     * Saves currency financing information for direct investment.
     *
     * @param currencyFinancingForPostDto The data for currency financing direct investment.
     * @param result                      The binding result for validation.
     * @return The CurrencyFinancingForGetDto after saving the currency financing direct investment information.
     */
    CurrencyFinancingForGetDto saveCurrencyFinancingDirectInvest(CurrencyFinancingForPostDto currencyFinancingForPostDto, BindingResult result);
}
