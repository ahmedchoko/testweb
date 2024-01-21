package com.wevioo.pi.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
public class InvestorForGetDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751742478447203565L;

    /**
     * Investor's id
     */
    private String id;

    /**
     * Investor's password.
     */
    @JsonIgnore
    private String password;

    /**
     * Investor's email.
     */
    private String email;

    /**
     * Investor's nationalId.
     */
    private String nationalId;

    /**
     * Investor's first name.
     */

    private String firstName;

    /**
     * Investor's last name.
     */

    private String lastName;

    /**
     * Investor's birthday date.
     */

    private Date birthdayDate;

    /**
     * Investor's phoneNumber
     */

    private String phoneNumber;

    /**
     * Investor's countryOfResidence
     */

    private String countryOfResidency;

    /**
     * Investor's nationality
     */

    private String nationality;

    /**
     * Investor's Passport number
     */
    private String passportNumber;

    /**
     * Investor's Passport number
     */
    private Date passportExpirationDate;


    /**
     * Investor's type
     */
    private PersonTypeEnum investorType;

    /**
     * Investor's representative
     */
    private RepresentativeForGetDto representative;

    /**
     * Investor's address
     */

    private String address;
    /**
     * Investor's Social reason
     */
    private String socialReason;

    /**
     * Investor's name for fund
     */
    private String nameForFund;

    /**
     * PM_NON_RESIDENT_FOREIGN Investor's has investment funds ? 1: Yes , 0: No
     */
    private boolean investmentFunds;

    /**
     * Investor's isActive
     */
    private boolean active;

}
