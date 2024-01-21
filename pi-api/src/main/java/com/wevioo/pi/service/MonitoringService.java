package com.wevioo.pi.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;

import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.rest.dto.DirectInvestDto;
import com.wevioo.pi.rest.dto.response.MonitoringResponse;

public interface MonitoringService {

	/**
	 * 
	 * @param declarationNature
	 * @param investmentType
	 * @param reference
	 * @param invistor
	 * @param types
	 * @param companyName
	 * @param companyIdentifier
	 * @param banks
	 * @param status
	 * @param investorIdentifier
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */

	MonitoringResponse<Object> findDirectInvestRequestByCriteria(DeclarationNature declarationNature,
			InvestmentType investmentType, String reference, String invistor, List<OperationType> types,
			String companyName, String companyIdentifier, List<String> banks, List<RequestStatusEnum> status,
			String investorIdentifier, Integer page, Integer size, Sort sort);

	MonitoringResponse<Object>  findDirectInvestRequestByCriteriaExport(DeclarationNature declarationNature,
			InvestmentType investmentType, String reference, String invistor, List<OperationType> types,
			String companyName, String companyIdentifier, List<String> banks, List<RequestStatusEnum> status,
			String investorIdentifier);
}
