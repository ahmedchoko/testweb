package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wevioo.pi.domain.enumeration.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MainContractualTermsForStepFiveDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 5837797173993528561L;

    /**
     * id
     */
    private String id;
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
    private String identificationValue;
    /**
     * Unique Identification
     */
    private String uniqueIdentification;

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
}
