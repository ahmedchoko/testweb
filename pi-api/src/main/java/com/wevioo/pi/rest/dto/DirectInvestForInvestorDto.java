package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import com.wevioo.pi.annotation.ExcelPdfHeader;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectInvestForInvestorDto extends CommonDirectInvestDto implements Serializable {
	
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -6165774318868747055L;
	@ExcelPdfHeader("Bank")
	private String bank;

}
