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
public class BankerForPutDto {

    // admin
    /**
     * User first name.
     */
    private String firstName;

    /**
     * User last name.
     */
    private String lastName;


    /**
     * The university degree of the banker.
     */
    private    String universityDegree;


    /**
     * User cell phone.
     */
    private String cellPhone;

    /**
     * The type of administrator for the banker.
     */
    private TypeAdministrator typeAdministrator;


    /**
     * The direction of the administrator.
     */
    private String direction;

    /**
     * The bank approved as an intermediary for the banker.
     */
    private  String bankId;

    /**
     * User email.
     */
    private String  email;
    /**
     * seniority Sector
     */
    private  Long senioritySector;
    /**
     * The banker's home phone number.
     */
    private String homePhoneNumber;
    /**
     * The grade of the administrator.
     */
    private String grade;

    /**
     * The function of the administrator.
     */
    private String function;
    /**
     * is Active
     */
    private Boolean isActive;
    
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

}
