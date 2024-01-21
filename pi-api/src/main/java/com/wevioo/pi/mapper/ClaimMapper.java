package com.wevioo.pi.mapper;


import com.wevioo.pi.domain.entity.account.Claim;
import com.wevioo.pi.rest.dto.ClaimForGetDto;
import com.wevioo.pi.rest.dto.ClaimForPostDto;
import com.wevioo.pi.rest.dto.ClaimForPutDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * This interface defines a mapper for converting between different types related to claims.
 * The mapper is typically used in Spring components for mapping between different representations of claims.
 */
@Mapper(componentModel = "spring")
public interface ClaimMapper {

	/**
	 * Converts a Claim entity to a ClaimForGetDto.
	 *
	 * @param claim The Claim entity to convert.
	 * @return The corresponding ClaimForGetDto.
	 */
	@Mapping(target = "modifiedBy",  source = "modifiedBy.firstName")
	@Mapping(target = "email", source = "email")
	@Mapping(target = "reason",  source = "reason")
	ClaimForGetDto toClaimDto(Claim claim);

	/**
	 * Converts a ClaimForPostDto to a Claim entity.
	 *
	 * @param claimForPostDto The ClaimForPostDto to convert.
	 * @return The corresponding Claim entity.
	 */
	@Mapping(target = "modifiedBy", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "attachment", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "modificationDate", ignore = true)
	@Mapping(target = "reason", ignore = true)
	@Mapping(target = "response", ignore = true)
	Claim toClaim(ClaimForPostDto claimForPostDto);

	/**
	 * Converts a ClaimForPutDto to a Claim entity.
	 *
	 * @param claimForPutDto The ClaimForPutDto to convert.
	 * @return The corresponding Claim entity.
	 */
	@Mapping(target = "modifiedBy", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "attachment", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "modificationDate", ignore = true)
	@Mapping(target = "reason", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(target = "description", ignore = true)
	Claim toClaim(ClaimForPutDto claimForPutDto);

	/**
	 * Converts a list of Claim entities to a list of ClaimForGetDto objects.
	 *
	 * @param claimList The list of Claim entities to convert.
	 * @return The corresponding list of ClaimForGetDto objects.
	 */
	List<ClaimForGetDto> toClaimForGetDtoList(List<Claim> claimList);
}
