package com.wevioo.pi.domain.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
import com.wevioo.pi.utility.ActionListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  Direct Invest Request Entity
 */
@Entity
@Table(name = "PI012_DIRECT_INVEST_REQUEST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(ActionListener.class)
public class DirectInvestRequest  implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7732861602930366311L;

    /**
     * ID
     */
    @Id
    @Column(name = "PI012_ID" , nullable = false , updatable = false)
    private String id;
    /**
     * deposit Type
     */
    @Column(name = "PI012_DEPOSIT_TYPE")
    @Enumerated(EnumType.STRING)
    private DepositType depositType;

    @Column(name = "PI012_LANGUAGE")
    @Enumerated(EnumType.STRING)
    private Language language;

    @OneToOne
    @JoinColumn( name =  "PI012_BANKER_AFFECTED_ID")
    private Banker affectedTo;

    @Column(name =  "PI012_EXAMINE_ACCEPTANCE")
    private  Boolean examineAcceptance;

    /**
     * Direct Invest Request's Bank.
     */
    @ManyToOne
    @JoinColumn(name = "PI012_IAT_ID_FK")
    private Bank bank;

    /**
     * status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PI012_STATUS")
    private RequestStatusEnum status;

    /**
     * step
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PI012_STEP")
    private StepEnum step;
    /**
     * Direct Invest Request's Agency.
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name =  "PI012_AGENCY_ID", referencedColumnName = "GR138AG", nullable = true),
            @JoinColumn(name =  "PI012_BANK_ID", referencedColumnName = "GR028BQ", nullable = true)
    })
    private Agency agency;


    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI012_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI012_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI012_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI012_MODIFIED_BY", nullable = true)
    private User modifiedBy;

    @OneToMany(mappedBy = "directInvestRequest")
    private List<ImportationPiece> importationPieces;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PI012_INVEST_IDENTIFICATION_IDFK")
    private InvestIdentification investIdentification;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "PI012_PARTICIPATION_IDENTIFICATION_IDFK")
    private  ParticipationIdentification  participationIdentification;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PI012_CURRENCY_FINANCING_IDFK")
    private   CurrencyFinancing   currencyFinancing;

    @OneToOne(cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
    @JoinColumn(name = "PI012_REQUESTER")
    private    Requester    requester;
    
    
    @OneToOne(cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
    @JoinColumn(name = "PI012_INVISTOR")
    private Investor invistor;
    
    /**
     * Operation type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PI012_OPERATION_TYPE")
    private OperationType operationType;

    /**
     * Transmission date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI012_TRANSMISSION_DATE")
    private Date transmissionDate;

    /**
     * Validation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI012_VALIDATION_DATE")
    private Date validationDate;


    @PrePersist
    private  void   onCreate(){
        this.creationDate = new Date();
    }

    @PreUpdate
    private void onUpdate(){
        this.modificationDate = new Date();
    }


}
