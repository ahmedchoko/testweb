package com.wevioo.pi.domain.entity.account;

import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.enumeration.TypeAdministrator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * Banker Model DataTable
 *
 * @author knh
 *
 */
@Table(name = "PI003T_BANKER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Banker extends  User{
	
	/**
	 * Serial Number
	 */
    private static final long serialVersionUID = -1275816435212674527L;

	/**
     * The seniority sector of the banker.
     */
    @Column(name = "PI003_SENIORITY_SECTOR")
    private  Long senioritySector;

    /**
     * Indicates whether the banker invests directly in real estate.
     */
    @Column(name = "PI003_INVESTMENT_DIRECT_AND_REAL_ESTATE")
    private Boolean investmentDirectAndRealEstate;

    /**
     * Indicates whether the banker has an investment portfolio.
     */
    @Column(name = "PI003_INVESTMENT_PORTFOLIO")
    private Boolean investmentPortfolio;

    /**
     * Indicates whether the banker has external loans.
     */
    @Column(name = "PI003_EXTERNAL_LOANS")
    private Boolean externalLoans;

    /**
     * The university degree of the banker.
     */
    @Column(name = "PI003_UNIVERSITY_DEGREE")
    private  String universityDegree;

    /**
     * The banker's home phone number.
     */
    @Column(name = "PI003_HOME_PHONE_NUMBER" , length = 150)
    private String homePhoneNumber;

    /**
     * The fax number of the banker.
     */
    @Column(name = "PI003_HOME_FAX_NUMBER" , length = 150)
    private String faxNumber;

    /**
     * The bank approved as an intermediary for the banker.
     */
    @ManyToOne
    @JoinColumn(name = "PI003_BANK_APPROVED_ID")
    private Bank approvedIntermediary;

    /**
     * The type of administrator for the banker.
     */
    @Enumerated(EnumType.STRING)
    @Column(name =  "PI003_TYPE_ADMINISTRATOR")
    private TypeAdministrator typeAdministrator;


    /**
     * The direction of the administrator.
     */
    @Column(name = "PI003_DIRECTION" , length = 255)
    private String direction;

    /**
     * The grade of the administrator.
     */
    @Column(name = "PI003_GRADE" , length = 250 )
    private String grade;

    /**
     * The function of the administrator.
     */
    @Column(name = "PI003_FUNCTION" , length = 250)
    private String function;

    /**
     * is Admin
     */
    @Column(name = "PI003_IS_ADMIN")
    private Boolean isAdmin;

    @ManyToOne
    @JoinColumn(name =  "BANKER_PARENT_ID")
    private Banker parent;
}
