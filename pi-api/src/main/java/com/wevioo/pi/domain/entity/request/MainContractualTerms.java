package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.enumeration.PersonType;
import com.wevioo.pi.domain.enumeration.StepEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PI020_MAIN_CONTRACTUAL_TERMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainContractualTerms implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7712441602930366311L;

    /**
     * ID
     */
    @Id
    @Column(name = "PI020_ID" , nullable = false , updatable = false)
    private String id;

    /**
     * first name
     */
    @Column(name = "PI020_FIRST_NAME")
    private String firstName;

    /**
     * last name
     */
    @Column(name = "PI020_LAST_NAME")
    private String lastName;

    /**
     * social reason
     */
    @Column(name = "PI020_SOCIAL_REASON")
    private String socialReason;


    /**
     * is Tunisian
     */
    @Column(name = "PI020_IS_TUNISIAN")
    private Boolean isTunisian;

    /**
     * Investor's investorType
     */
    @Column(name = "PI020_PERSON_TYPE", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonType personType;


    /**
     * User's type.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PI020_STEP", length = 50, nullable = false)
    private StepEnum step ;

    /**
     * Identification Type
     */
    @Column(name = "PI020_IDENTIFICATION_TYPE")
    private String identificationType;

    /**
     * identification value
     */
    @Column(name = "PI020_IDENTIFICATION_VALUE")
    private String uniqueIdentifier;



    /**
     * Registration contract date
     */
    @Column(name = "PI020_REGISTRATION_CONTRACT_DATE")
    private Date registrationContractDate;

    /**
     * Registration Acquisition date
     */
    @Column(name = "PI020_REGISTRATION_AQCUISITION_DATE")
    private Date registrationAqcuisitionDate;


    /**
     * Amount TND property Acquitted
     */
    @Column(name = "PI020_AMOUNT_TND_PROPERTY_ACQUITTED", nullable = false)
    private BigDecimal amountTNDPropertyAcquitted;

    /**
     * Amount cash contributed
     */
    @Column(name = "PI020_AMOUNT_CASH_CONTRIBUTED", nullable = true)
    private BigDecimal amountCashContributed;

    /**
     * Investor Part
     */
    @Column(name = "PI020_INVESTOR_PART", nullable = false)
    private Float investorPart;

    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI020_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI020_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI020_MODIFICATON_DATETIME")
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI020_MODIFIED_BY")
    private User modifiedBy;

    /**
     * /**
     * Pre persist method.
     */
    @PrePersist
    private void prePersist() {
        this.creationDate = new Date();
    }

    /**
     * Pre update method.
     */
    @PreUpdate
    private void preUpdate() {
        this.modificationDate = new Date();
    }



}
