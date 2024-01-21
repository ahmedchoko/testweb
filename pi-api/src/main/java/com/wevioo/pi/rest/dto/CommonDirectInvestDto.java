package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import com.wevioo.pi.annotation.ExcelPdfHeader;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.domain.enumeration.StepEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * CommonDirectInvestDto
 */
@Getter
@Setter

public class CommonDirectInvestDto implements Serializable {

	/**
	 * Serial Number 
	 */
	private static final long serialVersionUID = -6165774318868747055L;
	/**
	 * reference
	 */
	@ExcelPdfHeader("Reference")
	private String reference;
	/**
	 * formInvest
	 */
	@ExcelPdfHeader("Type Operation")
	private OperationType formInvest;
	/**
	 * companyName
	 */
	@ExcelPdfHeader("Nom Societe")
	private String companyName;
	/**
	 * companyIdentifier
	 */
	@ExcelPdfHeader("Identifiant Unique")
	private String companyIdentifier;
	/**
	 * status
	 */
	@ExcelPdfHeader("Status ")
	private RequestStatusEnum status;
	/**
	 * Step
	 */
	private StepEnum step;

}
