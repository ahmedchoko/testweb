package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude
public class CurrencyInvestmentFinancingDto implements Serializable {


    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;

    /**
     * Currency Financing's id
     */
    private String id;

    /**
     * Currency Financing's bank
     */
    private String bankId;

    /**
     * Currency Financing's financialMode
     */
    private Long financialModeId;

    /**
     * Currency Financing's importationPiece
     */
    private Long importationPieceId;

    /**
     * Currency Financing's pieceDate
     */
    private Date pieceDate;

    /**
     * Currency Financing's pieceReference
     */
    private String pieceReference;

    /**
     * Currency Financing's importedAmount
     */
    private BigDecimal importedAmount;

    /**
     * Currency Financing's financialCurrency
     */
    private String financialCurrencyId;

    /**
     * Currency Financing's currencyCession ? Yes : No
     */
    private Boolean currencyCession;

    /**
     * Currency Financing's cessionDate
     */
    private Date cessionDate;
    /**
     * Currency Financing's counterValueOfImportedAmountTND
     */
    private BigDecimal counterValueOfImportedAmountTND;

    /**
     * Currency Financing's cessionExchangeRate
     */
    private Float cessionExchangeRate;

}
