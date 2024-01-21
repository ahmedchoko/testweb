package com.wevioo.pi.rest.dto.response;


import com.wevioo.pi.rest.dto.GenericModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Currency Financing Data For Step  Six : Direct invest
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyFinancingDataForStepFive implements Serializable {


    private static final long serialVersionUID = 102175599295059635L;
    /**
     * id
     */
    private String id;
    /**
     * bank
     */
    private GenericModel bank;

    /**
     * Financial mode.
     */

    private  GenericModel financialMode;
    /**
     * Importation piece.
     */

    private  GenericModel importationPiece;
    /**
     * Date of the piece.
     */

    private Date pieceDate;
    /**
     * Piece reference.
     */

    private String pieceReference;

    /**
     * Imported amount.
     */

    private BigDecimal importedAmount;
    /**
     * Financial currency.
     */

    private  GenericModel financialCurrency;
    /**
     * Currency cession.
     */

    private Boolean currencyCession;
    /**
     * Date of cession.
     */
    private Date cessionDate;
    /**
     * Cession exchange rate.
     */
    private Float cessionExchangeRate;

    /**
     * Imported counter Value Of Imported Amount en TND.
     */
    private BigDecimal counterValueOfImportedAmountTND;
}
