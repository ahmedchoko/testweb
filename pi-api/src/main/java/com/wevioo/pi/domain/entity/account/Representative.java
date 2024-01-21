package com.wevioo.pi.domain.entity.account;

import com.wevioo.pi.domain.entity.referential.Country;
import com.wevioo.pi.domain.enumeration.PersonType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Representative Model DataTable
 *
 */
@Entity
@Table(name = "PI007T_REPRESENTATIVE")
@Getter
@Setter
public class Representative implements Serializable {



    /**
     * Serial Number
     */
    private static final long serialVersionUID = -8550625439989770094L;

    /**
     * Representative's id.
     */
    @Id
    @Column(name = "PI007_ID", updatable = false, nullable = false)
    private String id;

    /**
     * Representative's investorType
     */
    @Column(name = "PI007_REPRESENTATIVE_TYPE", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonType representativeType;

    /**
     * Representative's uniqueId
     */
    @Column(name = "PI007_UNIQUE_ID", length = 150)
    private String uniqueId;

    /**
     * Representative's email.
     */
    @Column(name = "PI007_EMAIL", length = 150)
    private String email;

    /**
     * Representative's first name.
     */
    @Column(name = "PI007_FIRST_NAME", length = 150)
    private String firstName;

    /**
     * Representative last name.
     */
    @Column(name = "PI007_LAST_NAME", length = 150)
    private String lastName;

    /**
     * Representative cell phone.
     */
    @Column(name = "PI007_CELL_PHONE", length = 15)
    private String cellPhone;

    /**
     * Representative's nationality.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI007_COUNTRY_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private Country nationality;


    /**
     * Representative's countryOfResidency.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI007_COUNTRY_RESIDENCY_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private Country countryOfResidency;

    /**
     * Representative's address
     */
    @Column(name = "PI007_ADDRESS", length = 100)
    private String address;

    /**
     * Representative's nationalId
     */
    @Column(name = "PI007_NATIONAL_ID", length = 100)
    private String nationalId;

    /**
     * Representative's birthdayDate
     */
    @Column(name = "PI007_BIRTHDAY_DATE")
    private Date birthdayDate;

    /**
     * Representative's nationalIdReleaseDate
     */
    @Column(name = "PI007_NATIONAL_ID_RELEASE_DATE")
    private Date nationalIdReleaseDate;

    /**
     * Representative's Social reason
     */
    @Column(name = "PI007_SOCIAL_REASON", length = 20)
    private String socialReason;



}
