package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import com.wevioo.pi.annotation.ExcelPdfHeader;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectInvestForBCTAgentDto extends CommonDirectInvestDto implements Serializable {

	

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -6165774318868747055L;
	@ExcelPdfHeader("Investisseur")
	private String investorName;
	@ExcelPdfHeader("Bank")
	private String bank;

}
