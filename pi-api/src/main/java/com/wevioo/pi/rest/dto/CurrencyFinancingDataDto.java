package com.wevioo.pi.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyFinancingDataDto {

	/**
	 * Importation piece.
	 */
	private String supportingDocuments;

	/**
	 * descriptionSpecRefList
	 */
	private String descriptionSpecRefList;

	/**
	 * Importation piece.
	 */
	private String importedAmount;

	/**
	 * importedAmountList
	 */
	private BigDecimal importedAmountList;

	/**
	 * currency
	 */
	private String currency;

	/**
	 * financialCurrencyList
	 */
	private String financialCurrencyList;

	/**
	 * cessionDate
	 */
	private String cessionDate;

	/**
	 * cessionDateList
	 */
	private String cessionDateList;

	/**
	 * counterValueOfImportedAmountTND
	 */
	private String counterValueOfImportedAmountTND;

	/**
	 * counterValueOfImportedAmountTNDList
	 */
	private BigDecimal counterValueOfImportedAmountTNDList;
	
	/**
	 * totalImportedAmount
	 */
	private String totalImportedAmount;

}
