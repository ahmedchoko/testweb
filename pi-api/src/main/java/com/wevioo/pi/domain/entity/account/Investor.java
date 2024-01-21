package com.wevioo.pi.domain.entity.account;

import com.wevioo.pi.domain.entity.referential.Country;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

/**
 * Investor Model DataTable
 *
 * @author shl
 *
 */
@Entity
@Table(name = "PI002T_INVESTOR")
@Getter
@Setter
public class Investor extends User {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -8550625439759770094L;

    /**
     * Investor's investorType
     */
    @Column(name = "PI002_INVESTOR_TYPE", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonTypeEnum investorType;

    /**
     * Investor's uniqueId
     */
    @Column(name = "PI002_UNIQUE_ID", length = 150 ,  unique = true)
    private String uniqueId;

    /**
     * Investor's nationality.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI002_COUNTRY_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private Country nationality;


    /**
     * Investor's countryOfResidency.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI002_COUNTRY_RESIDENCY_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private Country countryOfResidency;

    /**
     * Investor's address
     */
    @Column(name = "PI002_ADDRESS", length = 100)
    private String address;

    /**
     * Investor's nationalId
     */
    @Column(name = "PI002_NATIONAL_ID", length = 100 , unique = true)
    private String nationalId;

    /**
     * Investor's birthdayDate
     */
    @Column(name = "PI002_BIRTHDAY_DATE")
    private Date birthdayDate;

    /**
     * Investor's nationalIdReleaseDate
     */
    @Column(name = "PI002_NATIONAL_ID_RELEASE_DATE")
    private Date nationalIdReleaseDate;

    /**
     * Investor's Social reason
     */
    @Column(name = "PI002_SOCIAL_REASON", length = 20)
    private String socialReason;

    /**
     * Investor's Name for fund
     */
    @Column(name = "PI002_NAME_FOR_FUND", length = 30)
    private String nameForFund;

    /**
     * Investor's Investment Funds
     */
    @Column(name = "PI002_INVESTMENT_FUNDS")
    private Boolean investmentFunds;

    /**
     * Investor's Passport number
     */
    @Column(name = "PI002_PASSPORT_NUMBER", length = 20 , unique = true)
    private String passportNumber;

    /**
     * Investor's Passport expiration date
     */
    @Column(name = "PI002_PASSPORT_EXPIRATION_DATE")
    private Date passportExpirationDate;


    /**
     * Investor's representative
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name =  "PI002_REPRESENTATIVE_ID", nullable = true)
    private Representative representative;

}
