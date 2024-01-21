package com.wevioo.pi.rest.dto.response;


import com.wevioo.pi.domain.entity.referential.Currency;
import com.wevioo.pi.rest.dto.BankDto;
import com.wevioo.pi.rest.dto.GenericModel;
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
public class CurrencyFinancingDataForGet {
    /**
     * id
     */
    private String id;
    /**
     * bank
     */
    private BankDto bank;

    /**
     * Financial mode.
     */

    private GenericModel financialMode;
    /**
     * Importation piece.
     */

    private GenericModel importationPiece;
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

    private Currency financialCurrency;
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
