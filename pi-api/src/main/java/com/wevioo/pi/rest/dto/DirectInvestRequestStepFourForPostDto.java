package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import com.wevioo.pi.domain.enumeration.DepositType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DirectInvestRequestStepFourForPostDto implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 794957408756419281L;

	
	/**
	 * DirectInvestRequestStepFourForPostDto's ficheInvestId
	 */
	private String ficheInvestId;

	/**
	 * Direct Invest Request's Agency.
	 */
	private String agency;

	/**
	 * Direct Invest Request's Bank.
	 */
	private String bank;
	/**
	 * Direct Invest Request's depositType.
	 */
	private DepositType depositType;
}
