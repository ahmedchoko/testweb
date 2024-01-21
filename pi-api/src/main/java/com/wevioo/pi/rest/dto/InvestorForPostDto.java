package com.wevioo.pi.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wevioo.pi.domain.enumeration.Language;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(content = JsonInclude.Include.ALWAYS)
public class InvestorForPostDto implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = -1803974258607615449L;

    /**
     * User's email
     */
    private String email;

    /**
     * User password.
     */
    private String password;

    /**
     * Investor's first name.
     */

    private String firstName;

    /**
     * Investor's last name.
     */

    private String lastName;

    /**
     * Investor's phoneNumber
     */

    private String cellPhone;

    /**
     * Investor's investorType
     */
    private PersonTypeEnum investorType;

    /**
     * Investor's uniqueId
     */
    private String uniqueId;

    /**
     * Investor's nationality.
     */
    private String nationality;


    /**
     * Investor's countryOfResidency.
     */
    private String countryOfResidency;

    /**
     * Investor's address
     */
    private String address;

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
     * Investor's Passport number
     */
    private String passportNumber;

    /**
     * Investor's Passport expiration date
     */
    private Date passportExpirationDate;

    /**
     * Investor's Representative
     */
    private RepresentativeForPostDto representative;


    /**
     * Investor's has representative ? 1: Yes , 0: No
     */
    private boolean hasRepresentative;

    /**
     * language
     */
    private Language language;


    /**
     * file
     */

    private  String file;

}
