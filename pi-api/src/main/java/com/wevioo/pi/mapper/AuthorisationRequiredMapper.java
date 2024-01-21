package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.AuthorisationsRequired;
import com.wevioo.pi.rest.dto.request.AuthorizationRequiredForStepFiveDto;
import com.wevioo.pi.rest.dto.request.AuthorizationsRequiredForPostDto;
import com.wevioo.pi.rest.dto.response.AuthorizationRequiredForGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface AuthorisationRequiredMapper {

    /**
     * Converts an instance of {@link AuthorizationsRequiredForPostDto} to an instance of {@link AuthorisationsRequired}.
     * This mapping method ignores the fields "referenceAuthorisationBCT" and "authorisationBCTDate".
     *
     * @param authorizationsRequiredForPostDto The source DTO to be converted.
     * @return An instance of {@link AuthorisationsRequired} representing the converted data.
     */
    @Mapping(target = "referenceAuthorisationBCT", ignore = true)
    @Mapping(target = "authorisationBCTDate", ignore = true)
    AuthorisationsRequired toEntity(AuthorizationsRequiredForPostDto authorizationsRequiredForPostDto);

    /**
     * Converts an instance of {@link AuthorisationsRequired} to an instance of {@link AuthorizationRequiredForGetDto}.
     *
     * @param authorisationsRequired The source object to be converted.
     * @return An instance of {@link AuthorizationRequiredForGetDto} representing the converted data.
     */
    AuthorizationRequiredForGetDto toDto(AuthorisationsRequired authorisationsRequired);

    /**
     * Converts an instance of {@link AuthorisationsRequired} to a summary representation in {@link AuthorizationRequiredForStepFiveDto}.
     *
     * @param authorisationsRequired The source object to be converted.
     * @return An instance of {@link AuthorizationRequiredForStepFiveDto} representing the summary data.
     */
    AuthorizationRequiredForStepFiveDto toDtoSummary(AuthorisationsRequired authorisationsRequired);

}
