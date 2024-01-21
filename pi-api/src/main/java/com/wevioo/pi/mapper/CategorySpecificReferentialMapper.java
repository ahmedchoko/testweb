package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.referential.CategorySpecificReferential;
import com.wevioo.pi.rest.dto.CategorySpecificReferentialDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Category specific Referential Mapper
 */
@Mapper(componentModel = "spring" , uses = {SpecificReferentialMapper.class})
public interface CategorySpecificReferentialMapper {
    /**
     * Converts a CategorySpecificReferential entity to its respective DTO representation.
     *
     * @param categorySpecificReferential The CategorySpecificReferential entity to be converted.
     * @return Corresponding CategorySpecificReferentialDto object.
     */
    CategorySpecificReferentialDto toDto(CategorySpecificReferential categorySpecificReferential);
    /**
     * Converts a list of CategorySpecificReferential entities to a list of corresponding DTOs.
     *
     * @param categorySpecificReferential The list of CategorySpecificReferential entities to be converted.
     * @return List of CategorySpecificReferentialDto objects.
     */
    List<CategorySpecificReferentialDto> toDto(List<CategorySpecificReferential> categorySpecificReferential);

}
