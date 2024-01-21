package com.wevioo.pi.rest.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationIdentificationStepTwoForPostDto {


    /**
     * idFicheDirectInvest
     */
    private String ficheInvestId;

    /**
     * Social capital.
     */
    private BigDecimal socialCapital;

    /**
     * Free capital.
     */
    private BigDecimal freeCapital;

    /**
     * Number of actions.
     */
    private Long numberAction;

    /**
     * Nominal value.
     */
    private BigDecimal nominalValue;

    /**
     * Date of immatriculation.
     */
    private Date immatriculationDate;

    /**
     * Participation rate.
     */
    private Float participationRate;

    /**
     * Number of parts.
     */
    private Long numberPart;

    /**
     * Contribution amount.
     */
    private BigDecimal contributionAmount;

    /**
     * Indicates if paid capital is by tranche or not.
     */
    private Boolean paidCapitalByTranche;

    /**
     * Indicates if paid capital is by tranche or not.
     */
    private Boolean methodIncrease;

    /**
     * Total amount associated with the transaction or investment.
     */
    private BigDecimal totalAmount;

    /**
     * Amount that has been released or disbursed.
     */
    private BigDecimal releasedAmount;

    /**
     * Nominal value of the transaction or investment.
     */
    private BigDecimal nominalValueInC;

    /**
     * Amount invested in the transaction or investment.
     */
    private BigDecimal investedAmountInC;

    /**
     * Total amount invested across all transactions or investments.
     */
    private BigDecimal totalAmountInvested;

    /**
     * Number of participants or investors involved in the transaction.
     */
    private Long numberPartInvestor;

    /**
     * Total number of units issued for the transaction or investment.
     */
    private Long totalNumberIssued;

    /**
     * Total number of participants or investors involved in all transactions.
     */
    private Long partTotalInvestorNumber;


    /**
     * Number of acquired action
     */
    private Long  numberActionAcquired ;

    /**
     * Unit acquisition cost
     */
    private BigDecimal unitAcquisitionCost;

    /**
     * Total acquisition amount
     */
    private BigDecimal totalAmountAcquisition;

    /**
     * Transaction notice date
     */
    private Date transactionNoticeDate;

    /**
     * Registration acquisition contract date
     */
    private Date acquisitionContractRegistrationDate;

    /**
     * Registration certificate date
     */
    private Date registrationCertificateDate;

    /**
     * quest First Tranche
     */
    private Boolean questFirstTranche;

    /**
     * reference Declaration
     */
    private String  referenceDeclaration;
    /**
     * date Declaration
     */
    private Date dateDeclaration;


}
