package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InvestorGetForPutDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;

    /**
     * Investor's id
     */
    private String id;

    /**
     * Investor's id
     */
    private String uniqueId;

    /**
     * Investor type
     */
    private PersonTypeEnum investorType;

    /**
     * Investor's firstName
     */
    private String firstName;

    /**
     * Investor's creationDate
     */
    private String lastName;

    /**
     * Investor's socialReason ( for moral person )
     */
    private String socialReason;

    /**
     * Investor's Name for fund
     */
    private String nameForFund;

    /**
     * Investor is Investment Funds
     */
    private boolean investmentFunds;

    /**
     * Investor's email
     */
    private String email;

    /**
     * Investor's phone number
     */
    private String phoneNumber;

    /**
     * Investor's address
     */
    private String address;

    /**
     * Investor's passport Number
     */
    private String passportNumber;

    /**
     * Investor's country of residency
     */
    private String countryOfResidency;

    /**
     * Investor's nationality
     */
    private String nationality;

    /**
     * Investor's passport Number
     */
    private Date passportExpirationDate;

    /**
     * Investor's nationalId
     */
    private String nationalId;

    /**
     * Investor's birthdayDate
     */
    private Date birthdayDate;

    /**
     * Investor's nationalIdReleaseDate
     */
    private Date nationalIdReleaseDate;


    /**
     * Investor active: 1 : Yes | 0 : No
     */
    private boolean active;

}
