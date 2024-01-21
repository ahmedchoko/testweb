package com.wevioo.pi.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyFinancingForGetDto {
    /**
     * fiche Invest id
     */
    private String ficheInvestId;
    /**
     * list of  currencyFinancingData
     */
    private List<CurrencyFinancingDataForGet>  currencyFinancingData;
}
