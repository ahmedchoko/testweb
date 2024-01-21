package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.account.Representative;
import com.wevioo.pi.rest.dto.RepresentativeForPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * RepresentativeMapper
 */
@Mapper(componentModel = "spring")
public interface RepresentativeMapper {

    /**
     * Maps an RepresentativeDto to an Representative entity.
     *
     * @param representativeForPostDto The RepresentativeDto to map.
     * @return The Representative entity representing the mapped Representative.
     */
    @Mapping(target = "nationality", ignore = true)
    @Mapping(target = "countryOfResidency", ignore = true)
    Representative postRepresentativeToRepresentative(RepresentativeForPostDto representativeForPostDto);
}
