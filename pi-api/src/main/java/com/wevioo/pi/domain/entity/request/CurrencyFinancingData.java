package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.entity.referential.Currency;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
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

/**
 * Entity representing Currency Financing Data.
 */
@Entity
@Table(name = "PI014_CURRENCY_FINANCING_DATA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyFinancingData implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * ID of the currency financing data.
     */
    @Id
    @Column(name = "PI014_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Currency Financing Bank.
     */
    @ManyToOne
    @JoinColumn(name = "PI014_BANK_ID")
    private Bank bank;

    /**
     * Financial mode.
     */
    @ManyToOne
    @JoinColumn(name = "PI014_FINANCIAL_MODE_ID", nullable = false)
    private SpecificReferential financialMode;
    /**
     * Importation piece.
     */
    @ManyToOne
    @JoinColumn(name = "PI014_IMPORTATION_PIECE_ID", nullable = false)
    private SpecificReferential importationPiece;
    /**
     * Date of the piece.
     */
    @Column(name = "PI014_PIECE_DATE")
    @Temporal(TemporalType.DATE)
    private Date pieceDate;
    /**
     * Piece reference.
     */
    @Column(name = "PI014_PIECE_REFERENCE")
    private String pieceReference;

    /**
     * Imported amount.
     */
    @Column(name = "PI014_IMPORTED_AMOUNT", nullable = false)
    private BigDecimal importedAmount;
    /**
     * Financial currency.
     */
    @ManyToOne
    @JoinColumn(name = "PI014_FINANCIAL_CURRENCY_ID")
    private Currency financialCurrency;
    /**
     * Currency cession.
     */
    @Column(name = "PI014_CURRENCY_CESSION", nullable = false)
    private Boolean currencyCession;
    /**
     * Date of cession.
     */
    @Column(name = "PI014_CESSION_DATE")
    @Temporal(TemporalType.DATE)
    private Date cessionDate;
    /**
     * Cession exchange rate.
     */
    @Column(name = "PI014_CESSION_EXCHANGE_RATE")
    private Float cessionExchangeRate;

    /**
     * Imported counter Value Of Imported Amount en TND.
     */
    @Column(name = "PI014_COUNTER_VALUE_OF_IMPORTED_AMOUNT_TND")
    private BigDecimal counterValueOfImportedAmountTND;

    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI014_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI014_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI014_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI014_MODIFIED_BY", nullable = true)
    private User modifiedBy;
    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI014_CURRENCY_FINANCING_ID", nullable = false)
    private CurrencyFinancing currencyFinancing;

    @PrePersist
    void onCreate(){
        this.creationDate = new Date();
    }
    @PreUpdate
    void onUpdate(){
        this. modificationDate = new Date();
    }
}
