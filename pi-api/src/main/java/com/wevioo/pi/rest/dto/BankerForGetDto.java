package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.TypeAdministrator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankerForGetDto {
    /**
     * id
     */
    private  String id;
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
     * seniority Sector
     */
    private   String senioritySector;


    /**
     * The university degree of the banker.
     */
    private    String universityDegree;


    /**
     * User cell phone.
     */
    private String cellPhone;


    /**
     * The banker's home phone number.
     */
    private String homePhoneNumber;

    /**
     * The fax number of the banker.
     */
    private String faxNumber;
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
    private  Boolean externalLoans;



    // of Admin Banker

    /**
     * User status.
     */
    private Boolean isActive;

     private BankDto bank;
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
}
