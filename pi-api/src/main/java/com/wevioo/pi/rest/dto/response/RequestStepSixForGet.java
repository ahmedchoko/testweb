package com.wevioo.pi.rest.dto.response;

import com.wevioo.pi.domain.enumeration.DepositType;
import com.wevioo.pi.domain.enumeration.Language;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.rest.dto.AgencyDto;
import com.wevioo.pi.rest.dto.BankDto;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;

/**
 * Direct Invest Request For Get
 */
@Getter
@Setter
public class RequestStepSixForGet implements Serializable {
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
     * language
     */

    private Language language;


    /**
     * examineAcceptance
     */

    private boolean examineAcceptance;

    /**
     * Direct Invest Request's Bank.
     */

    private BankDto bank;

    /**
     * status
     */

    private RequestStatusEnum status;

    /**
     * Direct Invest Request's Agency.
     */

    private AgencyDto agency;

    /**
     * declarationDate
     */
    private Date transmissionDate;
}
