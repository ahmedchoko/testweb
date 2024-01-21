package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.PropertyDescription;
import com.wevioo.pi.rest.dto.request.PropertyDescriptionForGetDto;
import com.wevioo.pi.rest.dto.request.PropertyDescriptionForPostDto;
import com.wevioo.pi.rest.dto.request.PropertyDescriptionToStepFiveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropertyDescriptionMapper {

    /**
     * Converts a PropertyDescriptionForPostDto object to a corresponding PropertyDescription entity.
     *
     * @param propertyDescriptionForPostDto The DTO containing information for creating a PropertyDescription entity.
     * @return PropertyDescription representing the converted entity.
     */
    @Mapping(target = "investType", ignore = true)
    @Mapping(target = "usage", ignore = true)
    @Mapping(target = "vocation", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "governorate", ignore = true)
    @Mapping(target = "delegation", ignore = true)
    @Mapping(target = "locality", ignore = true)
    @Mapping(target = "zipCode", ignore = true)
    PropertyDescription toEntity(PropertyDescriptionForPostDto propertyDescriptionForPostDto);

    /**
     * Converts a PropertyDescription entity to a corresponding PropertyDescriptionForGetDto.
     *
     * @param propertyDescription The entity to be converted to DTO.
     * @return PropertyDescriptionForGetDto representing the converted DTO.
     */
 /*   @Mapping(target = "investType", source = "investType.id")
    @Mapping(target = "usage", source = "usage.id")
    @Mapping(target = "vocation", source = "vocation.id")
    @Mapping(target = "location", source = "location.id")
    @Mapping(target = "governorate", source = "governorate.id")
    @Mapping(target = "delegation", ignore = true)
    @Mapping(target = "locality", ignore = true)
    @Mapping(target = "zipCode", ignore = true)
    PropertyDescriptionForGetDto toDto(PropertyDescription propertyDescription);*/

    @Mapping(target = "delegation", ignore = true)
    @Mapping(target = "locality", ignore = true)
    @Mapping(target = "zipCode", ignore = true)
    PropertyDescriptionForGetDto toDto(PropertyDescription propertyDescription);

    /**
     * Converts an instance of {@link PropertyDescription} to a summary representation in {@link PropertyDescriptionToStepFiveDto}.
     *
     * @param propertyDescription The source object to be converted.
     * @return An instance of {@link PropertyDescriptionToStepFiveDto} representing the summary data.
     */
    @Mapping(target = "investType", source = "investType.label")
    @Mapping(target = "usage", source = "usage.label")
    @Mapping(target = "vocation", source = "vocation.label")
    @Mapping(target = "location", source = "location.label")
    @Mapping(target = "governorate", source = "governorate.label")
    @Mapping(target = "delegation", ignore = true)
    @Mapping(target = "locality", ignore = true)
    @Mapping(target = "zipCode", ignore = true)
    PropertyDescriptionToStepFiveDto toDtoSummary(PropertyDescription propertyDescription);

}

