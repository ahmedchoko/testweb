package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wevioo.pi.rest.dto.response.CurrencyFinancingDataForStepFive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude
public class PropertyRequestToSummaryDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 5837797173993528561L;

    /**
     * ficheInvestId
     */
    private String ficheInvestId;

    /**
     * property Description
     */
    private PropertyDescriptionToStepFiveDto propertyDescription;

    /**
     * main Contractual Terms
     */
    private MainContractualTermsForStepFiveDto mainContractualTerms;

    /**
     * authorization required
     */
    private AuthorizationRequiredForStepFiveDto authorizationRequired;

    /**
     * currency financing
     */
    private List<CurrencyFinancingDataForStepFive> currencyFinancingList;






}
