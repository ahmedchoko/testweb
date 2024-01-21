package com.wevioo.pi.rest.dto.request;

import com.wevioo.pi.domain.enumeration.PersonType;
import com.wevioo.pi.domain.enumeration.StepEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class MainContractualTermsStepTwoBaseDto {


    /**
     * id property request
     */
    private String idPropertyRequest;
    /**
     * First Name
     */
    private String firstName;
    /**
     * Last Name
     */
    private String lastName;
    /**
     * Social Reason
     */
    private String socialReason;
    /**
     * Indicates if he is Tunisian or not
     */
    private Boolean isTunisian;
    /**
     * Person Type
     */
    private PersonType personType;
    /**
     * Identification Type
     */
    private String identificationType;
    /**
     * Identification Value
     */
    private String uniqueIdentifier;
    
    /**
     * Registration Contract Date
     */
    private Date registrationContractDate;

    /**
     * Registration Acquisition Date
     */
    private Date registrationAqcuisitionDate;

    /**
     * Amount TND Property Acquitted
     */
    private BigDecimal amountTNDPropertyAcquitted;
    /**
     * Amount Cash Contributed
     */
    private BigDecimal amountCashContributed;
    /**
     * Investor Part
     */
    private Float investorPart;
    /**
     * Step
     */
    private StepEnum step;
}
