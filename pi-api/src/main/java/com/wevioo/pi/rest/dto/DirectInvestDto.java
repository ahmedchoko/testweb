package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DirectInvestDto  implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -6165774318868747055L;
	private String reference;
	private String investorName;
	private OperationType formInvest;
	private String companyName;
	private String companyIdentifier;
	private RequestStatusEnum status;
	private String bank;

}
