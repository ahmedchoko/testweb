package com.wevioo.pi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.PropertyRequest;
import com.wevioo.pi.rest.dto.DirectInvestForBCTAgentDto;
import com.wevioo.pi.rest.dto.DirectInvestForBankerDto;
import com.wevioo.pi.rest.dto.DirectInvestForInvestorDto;
import com.wevioo.pi.rest.dto.ValidateDirectInvestByBanker;
import com.wevioo.pi.rest.dto.response.DirectInvestRequestForGet;
import com.wevioo.pi.rest.dto.response.RequestStepSixForGet;

/**
 * Invest Identification Mapper
 */
@Mapper(componentModel = "spring", uses = { AgencyMapper.class })
public interface IRequestMapper {

	@Mapping(target = "ficheInvestId", source = "id")
	RequestStepSixForGet toDto(DirectInvestRequest directInvestRequest);

	@Mapping(target = "ficheInvestId", source = "id")
	ValidateDirectInvestByBanker toValidateDirectInvestByBanker(DirectInvestRequest directInvestRequest);

	@Mapping(target = "ficheInvestId", source = "id")
	RequestStepSixForGet toDto(PropertyRequest propertyRequest);

	@Mapping(target = "ficheInvestId", source = "id")
	DirectInvestRequestForGet toDtoForGet(DirectInvestRequest directInvestRequest);

	/**
	 * Map DirectInvestRequest to DirectInvestForBankerDto
	 * 
	 * @param directInvestRequest DirectInvestRequest
	 * @return DirectInvestForBankerDto
	 */
	@Mapping(target = "reference", source = "id")
	@Mapping(target = "formInvest", source = "operationType")
	@Mapping(target = "companyName", source = "investIdentification.companyName")
	@Mapping(target = "companyIdentifier", source = "investIdentification.uniqueIdentifier")
	@Mapping(target = "investorName", source = "." , qualifiedByName = "investorName")
	DirectInvestForBankerDto toMonoritingBankerDto(DirectInvestRequest directInvestRequest);

	/**
	 * Map List of DirectInvestRequest to list of DirectInvestForBankerDto
	 * 
	 * @param directInvestRequests List<DirectInvestRequest>
	 * @return List<DirectInvestForBankerDto>
	 */
	List<DirectInvestForBankerDto> toMonoritingBankerDto(List<DirectInvestRequest> directInvestRequests);

	/**
	 * Map DirectInvestRequest to DirectInvestForBankerDto
	 * 
	 * @param directInvestRequest DirectInvestRequest
	 * @return DirectInvestForBCTAgentDto
	 */
	@Mapping(target = "reference", source = "id")
	@Mapping(target = "formInvest", source = "operationType")
	@Mapping(target = "companyName", source = "investIdentification.companyName")
	@Mapping(target = "companyIdentifier", source = "investIdentification.uniqueIdentifier")
	@Mapping(target = "investorName", source = "." , qualifiedByName = "investorName")
	@Mapping(target = "bank", source = "bank.code")
	DirectInvestForBCTAgentDto toMonoritingBCTAgentDto(DirectInvestRequest directInvestRequest);

	/**
	 * Map List of DirectInvestRequest to list of DirectInvestForBankerDto
	 * 
	 * @param directInvestRequests List<DirectInvestRequest>
	 * @return List<DirectInvestForBCTAgentDto>
	 */
	List<DirectInvestForBCTAgentDto> toMonoritingBCTAgentDto(List<DirectInvestRequest> directInvestRequests);

	/**
	 * Map DirectInvestRequest to DirectInvestForBankerDto
	 * 
	 * @param directInvestRequest DirectInvestRequest
	 * @return DirectInvestDto
	 */
	@Mapping(target = "reference", source = "id")
	@Mapping(target = "formInvest", source = "operationType")
	@Mapping(target = "companyName", source = "investIdentification.companyName")
	@Mapping(target = "companyIdentifier", source = "investIdentification.uniqueIdentifier")
	@Mapping(target = "bank", source = "bank.code")
	DirectInvestForInvestorDto toMonoritingInvestorDto(DirectInvestRequest directInvestRequest);

	/**
	 * Map List of DirectInvestRequest to list of DirectInvestForBankerDto
	 * 
	 * @param directInvestRequests List of DirectInvestRequest
	 * @return list of DirectInvestForBankerDto
	 */
	List<DirectInvestForInvestorDto> toMonoritingInvestorDto(List<DirectInvestRequest> directInvestRequests);

	
	/**
	 * Search investor Name
	 * @param directInvestRequest DirectInvestRequest
	 * @return
	 */
	@Named("investorName")
	default String getInvestorName(DirectInvestRequest directInvestRequest) {
		if (directInvestRequest != null && directInvestRequest.getInvistor() != null) {
			return directInvestRequest.getInvistor().getSocialReason() != null
					? directInvestRequest.getInvistor().getSocialReason()
					: (directInvestRequest.getInvistor().getNameForFund() != null
							? directInvestRequest.getInvistor().getNameForFund()
							: directInvestRequest.getInvistor().getFirstName()
									.concat(" " + directInvestRequest.getInvistor().getLastName()));
		}
		return null;
	}
}
