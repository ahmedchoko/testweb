package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.rest.dto.SpecificReferentialDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Category specific Referential Mapper
 */
@Mapper(componentModel = "spring")
public interface SpecificReferentialMapper {
    /**
     * Converts a SpecificReferential entity to its respective DTO representation.
     *
     * @param specificReferential The SpecificReferential entity to be converted.
     * @return Corresponding SpecificReferentialDto object.
     */
    SpecificReferentialDto toDto(SpecificReferential specificReferential);
    /**
     * Converts a list of SpecificReferential entities to a list of corresponding DTOs.
     *
     * @param specificReferential The list of SpecificReferential entities to be converted.
     * @return List of SpecificReferentialDto objects.
     */
    List<SpecificReferentialDto> toDto(List<SpecificReferential> specificReferential);
}
