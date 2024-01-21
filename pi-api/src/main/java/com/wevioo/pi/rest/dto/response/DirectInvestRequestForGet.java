package com.wevioo.pi.rest.dto.response;

import java.io.Serializable;

import com.wevioo.pi.domain.enumeration.DepositType;
import com.wevioo.pi.rest.dto.AgencyDto;
import com.wevioo.pi.rest.dto.BankDto;

import lombok.Getter;
import lombok.Setter;

/**
 * Direct Invest Request For Get
 */
@Getter
@Setter
public class DirectInvestRequestForGet implements Serializable {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = -7139054719695454100L;
    /**
     * ficheInvestId
     */
    private String  ficheInvestId  ;
    /**
     * deposit Type
     */

    private DepositType depositType;
   

    /**
     * Direct Invest Request's Bank.
     */

    private BankDto bank;


    /**
     * Direct Invest Request's Agency.
     */

    private AgencyDto agency;
}
