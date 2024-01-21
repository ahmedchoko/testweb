package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Agency;
import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.enumeration.DepositType;
import com.wevioo.pi.domain.enumeration.Language;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.domain.enumeration.StepEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PI019_PROPERTY_REQUEST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequest implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7712561602930366311L;

    /**
     * ID
     */
    @Id
    @Column(name = "PI019_ID" , nullable = false , updatable = false)
    private String id;

    /**
     * Property Request's Bank.
     */
    @ManyToOne
    @JoinColumn(name = "PI019_BANK_ID", insertable = false, updatable = false)
    private Bank bank;

    /**
     * Property Request's Agency.
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name =  "PI019_AGENCY_ID", referencedColumnName = "GR138AG", nullable = true),
            @JoinColumn(name =  "PI019_BANK_ID", referencedColumnName = "GR028BQ", nullable = true)
    })
    private Agency agency;

    /**
     * Investor.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI019_INVESTOR_IDFK", nullable = true)
    private Investor investor;

    /**
     * deposit Type
     */
    @Column(name = "PI019_DEPOSIT_TYPE")
    @Enumerated(EnumType.STRING)
    private DepositType depositType;

    /**
     * Banker Assign.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI019_BANKER_IDFK", nullable = true)
    private Banker banker;

    /**
     * language.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PI019_LANGUAGE", length = 50)
    private Language language;

    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI019_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI019_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI019_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI019_MODIFIED_BY", nullable = true)
    private User modifiedBy;

    /**
     * status
     */
    @Column(name = "PI019_STATUS")
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum status;

    /**
     * step
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PI019_STEP")
    private StepEnum step;


    /**
     * examine acceptance
     */
    @Column(name =  "PI019_EXAMINE_ACCEPTANCE")
    private  Boolean examineAcceptance;

    /**
     * Transmission date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI019_TRANSMISSION_DATE")
    private Date transmissionDate;

    /**
     * Validation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI019_VALIDATION_DATE")
    private Date validationDate;

    /**
     * Requester
     */
    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "PI019_REQUESTER")
    private Requester requester;

    /**
     * Property Request's Property Description
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PI019_PROPERTY_DESCRIPTION_IDFK")
    private PropertyDescription propertyDescription;

    /**
     * Property Request's Main Contractual Terms
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PI019_MAIN_CONTRACTUAL_TERMS_IDFK", insertable = false)
    private MainContractualTerms mainContractualTerms;

    /**
     * Property Request's Authorisations Required
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PI019_AUTHORIZATION_REQUIRED_IDFK", insertable = false)
    private AuthorisationsRequired authorisationsRequired;

    /**
     * Property Request's Currency Financing
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PI019_CURRENCY_FINANCING_IDFK", insertable = false)
    private CurrencyFinancing currencyFinancing;

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
