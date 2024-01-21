package com.wevioo.pi.service.imp;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.referential.ConfigurationSearchField;
import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.IRequestMapper;
import com.wevioo.pi.repository.ConfigurationSearchFieldRepository;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.IBankerRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.DirectInvestDto;
import com.wevioo.pi.rest.dto.DirectInvestForBCTAgentDto;
import com.wevioo.pi.rest.dto.DirectInvestForBankerDto;
import com.wevioo.pi.rest.dto.DirectInvestForInvestorDto;
import com.wevioo.pi.rest.dto.response.MonitoringResponse;
import com.wevioo.pi.rest.dto.response.StructureDto;
import com.wevioo.pi.service.MonitoringService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MonitoringServiceImpl implements MonitoringService {

	/**
	 * @Injected bean {@link DirectInvestRequestRepository }
	 */
	@Autowired
	private DirectInvestRequestRepository directInvestRequestRepository;
	/**
	 * Injected bean {@link UtilityService}
	 */
	@Autowired
	private UtilityService utilityService;
	/**
	 * Injected bean {@link SecurityUtils}
	 */
	@Autowired
	private SecurityUtils securityUtils;

	/**
	 * Injected bean {@link UserRepository}
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Injected bean {@link UserRepository}
	 */
	@Autowired
	private ConfigurationSearchFieldRepository configurationSearchFieldRepository;
	/**
	 * Inject {@link ObjectMapper} bean
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Injected bean {@link IBankerRepository }
	 */
	@Autowired
	IBankerRepository iBankerRepository;

	/**
	 * Injected bean {@link IRequestMapper }
	 */
	@Autowired
	IRequestMapper iRequestMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonitoringResponse<Object> findDirectInvestRequestByCriteria(DeclarationNature declarationNature,
			InvestmentType investmentType, String reference, String invistor, List<OperationType> types,
			String companyName, String companyIdentifier, List<String> banks, List<RequestStatusEnum> status,
			String investorIdentifier, Integer page, Integer size, Sort sort) {

		Sort sortingCriteria = utilityService.sortingCriteria(sort, Sort.Direction.DESC,
				ApplicationConstants.CREATION_DATE);
		Pageable pageable = utilityService.createPageable(page != null ? page : 1, size != null ? size : 10,
				sortingCriteria);
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));

		Page<DirectInvestRequest> result = null;
		ConfigurationSearchField configurationSearchField = null;
		switch (user.getUserType()) {

		case BANKER:
		case ADMIN_BANKER:
			Banker banker = iBankerRepository.findById(userId)
					.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
							ApplicationConstants.USER_NOT_FOUND + userId));
			List<String> bankers = null;
			if (user.getUserType() == UserTypeEnum.ADMIN_BANKER) {
				bankers = iBankerRepository
						.findAllBankerByApprovedIntermediaryId(banker.getApprovedIntermediary().getId());
				bankers.add(userId);
			} else {
				bankers = new ArrayList<>();
				bankers.add(userId);
			}
			if (user.getUserType() == UserTypeEnum.ADMIN_BANKER) {
				result = directInvestRequestRepository.findDirectInvestRequestByCriteria(reference, invistor, types,
						companyName, companyIdentifier, banks, status, investorIdentifier, null, bankers, RequestStatusEnum.DRAFT, pageable);
			}else {
				result = directInvestRequestRepository.findDirectInvestRequestByCriteria(reference, invistor, types,
						companyName, companyIdentifier, banks, status, investorIdentifier, null, bankers, null, pageable);
			}
			
			
			configurationSearchField = configurationSearchFieldRepository
					.findByInvestmentTypeAndUserType(investmentType, UserTypeEnum.BANKER);
			break;

		case BCT_ADMIN:
		case BCT_AGENT:

			result = directInvestRequestRepository.findDirectInvestRequestByCriteria(reference, invistor, types,
					companyName, companyIdentifier, banks, status, investorIdentifier, null, null,
					RequestStatusEnum.DRAFT, pageable);
			configurationSearchField = configurationSearchFieldRepository
					.findByInvestmentTypeAndUserType(investmentType, UserTypeEnum.BCT_AGENT);
			break;

		case INVESTOR:

			result = directInvestRequestRepository.findDirectInvestRequestByCriteria(reference, types, companyName,
					companyIdentifier, banks, status, userId, userId, pageable);
			configurationSearchField = configurationSearchFieldRepository
					.findByInvestmentTypeAndUserType(investmentType, UserTypeEnum.INVESTOR);
			break;
		default:

			break;
		}
		MonitoringResponse<Object> response = new MonitoringResponse<>();
		response.setTotalElement(result != null ? result.getTotalElements() : 0);
		response.setTotalPage(result != null ? result.getTotalPages() : 0);
		response.setPageSize(result != null ? result.getSize() : 0);
		response.setPage(result != null ? result.getNumber() : 0);
		switch (user.getUserType()) {

		case BANKER:
		case ADMIN_BANKER:
			List<DirectInvestForBankerDto> requestBankerList = iRequestMapper
					.toMonoritingBankerDto(result.getContent());
			response.setData(requestBankerList != null ? new ArrayList<Object>(requestBankerList) : null);
			break;

		case BCT_ADMIN:
		case BCT_AGENT:

			List<DirectInvestForBCTAgentDto> requestBCTList = iRequestMapper
					.toMonoritingBCTAgentDto(result.getContent());
			response.setData(requestBCTList != null ? new ArrayList<Object>(requestBCTList) : null);
			break;

		case INVESTOR:
			List<DirectInvestForInvestorDto> requestInvestorList = iRequestMapper
					.toMonoritingInvestorDto(result.getContent());
			response.setData(requestInvestorList != null ? new ArrayList<Object>(requestInvestorList) : null);
			break;
		default:

			break;
		}

		try {
			List<StructureDto> investorForPostDto = objectMapper.readValue(configurationSearchField.getConfigData(),
					new TypeReference<List<StructureDto>>() {
					});
			response.setStructure(investorForPostDto);
		} catch (IOException e) {
			log.error("An exception has been thrown ", e);
		}

		return response;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonitoringResponse<Object> findDirectInvestRequestByCriteriaExport(DeclarationNature declarationNature,
																		InvestmentType investmentType, String reference, String invistor, List<OperationType> types,
																		String companyName, String companyIdentifier, List<String> banks, List<RequestStatusEnum> status,
																		String investorIdentifier) {
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));

		List <DirectInvestRequest> result = null;
		ConfigurationSearchField configurationSearchField = null;
		switch (user.getUserType()) {

			case BANKER:
			case ADMIN_BANKER:
				Banker banker = iBankerRepository.findById(userId)
						.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
								ApplicationConstants.USER_NOT_FOUND + userId));
				List<String> bankers = null;
				if (user.getUserType() == UserTypeEnum.ADMIN_BANKER) {
					bankers = iBankerRepository
							.findAllBankerByApprovedIntermediaryId(banker.getApprovedIntermediary().getId());
					bankers.add(userId);
				} else {
					bankers = new ArrayList<>();
					bankers.add(userId);
				}
				if (user.getUserType() == UserTypeEnum.ADMIN_BANKER) {
					result = directInvestRequestRepository.findDirectInvestRequestByCriteriaExport(reference, invistor, types,
							companyName, companyIdentifier, banks, status, investorIdentifier, null, bankers, RequestStatusEnum.DRAFT);
				}else {
					result = directInvestRequestRepository.findDirectInvestRequestByCriteriaExport(reference, invistor, types,
							companyName, companyIdentifier, banks, status, investorIdentifier, null, bankers, null);
				}


				configurationSearchField = configurationSearchFieldRepository
						.findByInvestmentTypeAndUserType(investmentType, UserTypeEnum.BANKER);
				break;

			case BCT_ADMIN:
			case BCT_AGENT:

				result = directInvestRequestRepository.findDirectInvestRequestByCriteriaExport(reference, invistor, types,
						companyName, companyIdentifier, banks, status, investorIdentifier, null, null,
						RequestStatusEnum.DRAFT);
				configurationSearchField = configurationSearchFieldRepository
						.findByInvestmentTypeAndUserType(investmentType, UserTypeEnum.BCT_AGENT);
				break;

			case INVESTOR:

				result = directInvestRequestRepository.findDirectInvestRequestByCriteriaExport(reference, types, companyName,
						companyIdentifier, banks, status, userId, userId);
				configurationSearchField = configurationSearchFieldRepository
						.findByInvestmentTypeAndUserType(investmentType, UserTypeEnum.INVESTOR);
				break;
			default:

				break;
		}
		MonitoringResponse<Object> response = new MonitoringResponse<>();
//		response.setTotalElement(result != null ? result.getTotalElements() : 0);
//		response.setTotalPage(result != null ? result.getTotalPages() : 0);
//		response.setPageSize(result != null ? result.getSize() : 0);
//		response.setPage(result != null ? result.getNumber() : 0);
		switch (user.getUserType()) {

			case BANKER:
			case ADMIN_BANKER:
				List<DirectInvestForBankerDto> requestBankerList = iRequestMapper
						.toMonoritingBankerDto(result);
				response.setData(requestBankerList != null ? new ArrayList<Object>(requestBankerList) : null);
				break;

			case BCT_ADMIN:
			case BCT_AGENT:

				List<DirectInvestForBCTAgentDto> requestBCTList = iRequestMapper
						.toMonoritingBCTAgentDto(result);
				response.setData(requestBCTList != null ? new ArrayList<Object>(requestBCTList) : null);
				break;

			case INVESTOR:
				List<DirectInvestForInvestorDto> requestInvestorList = iRequestMapper
						.toMonoritingInvestorDto(result);
				response.setData(requestInvestorList != null ? new ArrayList<Object>(requestInvestorList) : null);
				break;
			default:

				break;
		}

		try {
			List<StructureDto> investorForPostDto = objectMapper.readValue(configurationSearchField.getConfigData(),
					new TypeReference<List<StructureDto>>() {
					});
			response.setStructure(investorForPostDto);
		} catch (IOException e) {
			log.error("An exception has been thrown ", e);
		}

		return response;
	}

}
