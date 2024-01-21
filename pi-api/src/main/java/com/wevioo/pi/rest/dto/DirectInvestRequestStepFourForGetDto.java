package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import com.wevioo.pi.domain.enumeration.DepositType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DirectInvestRequestStepFourForGetDto implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 1988313761556335762L;

	/**
	 * DirectInvestRequestStepFourForGetDto's ficheInvestId
	 */
	private String ficheInvestId;

	/**
	 * Direct Invest Request's depositType.
	 */
	private DepositType depositType;

	/**
	 * Direct Invest Request's Bank.
	 */

	private BankDto bank;

	/**
	 * Direct Invest Request's Agency.
	 */

	private AgencyDto agency;

}
