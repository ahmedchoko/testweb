package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.DepositType;
import com.wevioo.pi.rest.dto.response.CurrencyFinancingDataForStepFive;
import com.wevioo.pi.rest.dto.response.InvestIdentificationDtoToStepFive;
import com.wevioo.pi.rest.dto.response.ParticipationIdentificationStepTwoForGetDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 *  Direct Invest To Step  Five  : direct invest
 */
@Getter
@Setter
public class DirectInvestToStepFive implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 5837797173993528561L;

    /**
     * ficheInvestId
     */
    String ficheInvestId;

    /**
     *  investIdentification
     */
    InvestIdentificationDtoToStepFive investIdentification;
    /**
     * participation Identification
     */
    ParticipationIdentificationStepTwoForGetDto participationIdentification;
    /**
     *Currency Financing Data
     */
    List<CurrencyFinancingDataForStepFive> currencyFinancingData;


    /**
     * agency
     */
    AgencyDto agency;

    /**
     * banck
     */
    BankDto bank;

    /**
     * depositpe
     */
    DepositType deposiType ;

    /**
     * Section and data saved 
     */
    List<SectionConfigDto> sectionDataDto;
    
}
