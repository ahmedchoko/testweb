package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.enumeration.TypeAdministrator;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Locale;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankerDto  {
    /**
     * id
     */
    private String id;

    /**
     * User password.
     */

    private String password;

    /**
     * usser password Confirmation
     */
    private String passwordConfirmation;


    /**
     * User email.
     */
    private String  email;

    /**
     * User first name.
     */
    private String firstName;

    /**
     * User last name.
     */
    private String lastName;

    /**
     * User cell phone.
     */
    private String cellPhone;

    /**
     * User status.
     */
    private Boolean isActive;

    /**
     * expiration Date for otp Code
     */
    private Date expirationDate;

    /**
     * User's type.
     */
    private UserTypeEnum userType;

    /**
     * Creation date.
     */
    private Date creationDate;

    /**
     * Created by.
     */
    private User createdBy;

    /**
     * Modification date.
     */
    private Date modificationDate;
    /**
     * Modified by.
     */
    private User modifiedBy;
    /**
     * password last modificationDate
     */
    private Date pwdModificationDate;


    /**
     * seniority Sector
     */
    private  Long senioritySector;

    /**
     * Indicates whether the banker invests directly in real estate.
     */
    private Boolean investmentDirectAndRealEstate;

    /**
     * Indicates whether the banker has an investment portfolio.
     */
    private Boolean investmentPortfolio;

    /**
     * Indicates whether the banker has external loans.
     */
    private boolean externalLoans;

    /**
     * The university degree of the banker.
     */
    private    String universityDegree;

    /**
     * The banker's home phone number.
     */
    private String homePhoneNumber;

    /**
     * The fax number of the banker.
     */
    private String faxNumber;

    /**
     * The bank approved as an intermediary for the banker.
     */
    private Bank approvedIntermediary;

    /**
     * The type of administrator for the banker.
     */
    private TypeAdministrator typeAdministrator;


    /**
     * The direction of the administrator.
     */
    private String direction;

    /**
     * The grade of the administrator.
     */
    private String grade;

    /**
     * The function of the administrator.
     */
    private String function;

    /**
     * is Admin
     */
    private  Boolean isAdmin;

    /**
     * banker parent
     */
    private Banker parent;

    /**
     * locale language
     */
    private Locale locale ;


    /**
     * is first conextion
     */

    private  Boolean isFirstConnection;

}
