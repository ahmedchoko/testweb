package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "PI013_PARTICIPATION_IDENTIFICATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationIdentification implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * ID of the participation identification.
     */
    @Id
    @Column(name = "PI013_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Social capital.
     */
    @Column(name = "PI013_SOCIAL_CAPITAL")
    private BigDecimal socialCapital;

    /**
     * Free capital.
     */
    @Column(name = "PI013_FREE_CAPITAL" , nullable = true)
    private BigDecimal freeCapital;

    /**
     * Number of actions.
     */
    @Column(name = "PI013_NUMBER_ACTION")
    private Long numberAction;

    /**
     * Nominal value.
     */
    @Column(name = "PI013_NOMINAL_VALUE")
    private BigDecimal nominalValue;

    /**
     * Date of immatriculation.
     */
    @Column(name = "PI013_IMMATRICULATION_DATE" , nullable = true)
    @Temporal(TemporalType.DATE)
    private Date immatriculationDate;

    /**
     * Participation rate.
     */
    @Column(name = "PI013_PARTICIPATION_RATE" ,nullable = true)
    private Float participationRate;

    /**
     * Number of parts.
     */
    @Column(name = "PI013_INUMBER_PART")
    private Long numberPart;

    /**
     * Contribution amount.
     */
    @Column(name = "PI013_CONTRIBUTION_AMOUNT" , nullable = true)
    private BigDecimal contributionAmount;

    /**
     * Indicates if paid capital is by tranche or not.
     */
    @Column(name = "PI013_PAID_CAPITAL_BY_TRANCHE")
    private Boolean paidCapitalByTranche;

    /**
     * Indicates if paid capital is by tranche or not.
     */
    @Column(name = "PI013_METHOD_INCREASE")
    private Boolean methodIncrease;

    /**
     * Number of acquired action
     */
    @Column(name = "PI013_NUMBER_ACTION_ACQUIRED" , nullable = true )
    private Long  numberActionAcquired ;

    /**
     * Unit acquisition cost
     */
    @Column(name = "PI013_UNIT_AQUISITION_COST" , nullable = true)
    private BigDecimal unitAcquisitionCost;

    /**
     * Total acquisition amount
     */
    @Column(name = "PI013_TOTALE_ACQUISITION_AMOUNT" , nullable = true)
    private BigDecimal totalAmountAcquisition;

    /**
     * Transaction notice date
     */
    @Column(name = "PI013_DATE_TRANSACTION_NOTICE" , nullable = true)
    private Date transactionNoticeDate;

    /**
     * Registration acquisition contract date
     */
    @Column(name = "PI013_DATE_CONTRACT_REGISTRATION" , nullable = true)
    private Date acquisitionContractRegistrationDate;

    /**
     * Registration certificate date
     */
    @Column(name = "PI013_DATE_CERTIFICATE_REGISTRATION" , nullable = true)
    private Date registrationCertificateDate;

    /**
     * Total amount associated with the transaction or investment.
     */
    @Column(name = "PI013_TOTAL_AMOUNT", nullable = true)
    private BigDecimal totalAmount;

    /**
     * Amount that has been released or disbursed.
     */
    @Column(name = "PI013_RELEASED_AMOUNT", nullable = true)
    private BigDecimal releasedAmount;

    /**
     * Nominal value of the transaction or investment.
     */
    @Column(name = "PI013_NOMINAL_VALUE_INC", nullable = true)
    private BigDecimal nominalValueInC;

    /**
     * Amount invested in the transaction or investment.
     */
    @Column(name = "PI013_INVESTED_AMOUNT_INC", nullable = true)
    private BigDecimal investedAmountInC;

    /**
     * Total amount invested across all transactions or investments.
     */
    @Column(name = "PI013_TOTAL_AMOUNT_INVESTED", nullable = true)
    private BigDecimal totalAmountInvested;

    /**
     * Number of participants or investors involved in the transaction.
     */
    @Column(name = "PI013_NUMBER_PART_INVESTOR", nullable = true)
    private Long numberPartInvestor;

    /**
     * Total number of units issued for the transaction or investment.
     */
    @Column(name = "PI013_TOTAL_NUMBER_ISSUED", nullable = true)
    private Long totalNumberIssued;

    /**
     * Total number of participants or investors involved in all transactions.
     */
    @Column(name = "PI013_NUMBER_PART_TOTAL_INVESTOR", nullable = true)
    private Long partTotalInvestorNumber;
    /**
     * quest First Tranche
     */
     @Column( name = "PI013_QUEST_FIRST_TRANCHE")
     private Boolean questFirstTranche;

    /**
     * reference Declaration
     */
    @Column(name = "PI013_REFERENCE_DECLARATION")
     private String  referenceDeclaration;
      /**
      * date Declaration
      */
      @Column(name = "PI013_DATE_DECLARATION")
     private Date dateDeclaration;


    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI013_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI013_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI013_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;

    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI013_MODIFIED_BY", nullable = true)
    private User modifiedBy;


    @PrePersist
    void onCreate(){
        this.creationDate = new Date();
    }
    @PreUpdate
    void onUpdate(){
        this. modificationDate = new Date();
    }

}
