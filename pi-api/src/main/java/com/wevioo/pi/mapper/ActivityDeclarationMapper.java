package com.wevioo.pi.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import com.wevioo.pi.domain.entity.request.ActivityDeclaration;
import com.wevioo.pi.rest.dto.response.ActivityDeclarationDtoToStepFive;

@Mapper(componentModel = "spring")
public interface ActivityDeclarationMapper {
    /**
     * Converts an ActivityDeclaration object to its corresponding ActivityDeclarationDtoToStepFive DTO for Step Five.
     *
     * @param activityDeclaration The ActivityDeclaration object to convert.
     * @return Corresponding ActivityDeclarationDtoToStepFive DTO for Step Five.
     */
    ActivityDeclarationDtoToStepFive toDto(ActivityDeclaration activityDeclaration);

    /**
     * Converts a list of ActivityDeclaration objects to a list of corresponding
     * ActivityDeclarationDtoToStepFive DTOs for Step Five.
     *
     * @param activityDeclaration List of ActivityDeclaration objects to convert.
     * @return List of corresponding ActivityDeclarationDtoToStepFive DTOs for Step Five.
     */
    List<ActivityDeclarationDtoToStepFive> toDto(List<ActivityDeclaration> activityDeclaration);
}
