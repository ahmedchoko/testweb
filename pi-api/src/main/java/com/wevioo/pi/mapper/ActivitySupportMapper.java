package com.wevioo.pi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.wevioo.pi.domain.entity.request.ActivitySupport;
import com.wevioo.pi.rest.dto.response.ActivitySupportDtoToStepFive;

@Mapper(componentModel = "spring")
public interface ActivitySupportMapper {

    /**
     * Converts an ActivitySupport object to its corresponding ActivitySupportDtoToStepFive DTO for Step Five.
     *
     * @param activitySupport The ActivitySupport object to convert.
     * @return Corresponding ActivitySupportDtoToStepFive DTO for Step Five.
     */

    ActivitySupportDtoToStepFive toDto (ActivitySupport activitySupport);
    /**
     * Converts a list of ActivitySupport objects to a list of corresponding
     * ActivitySupportDtoToStepFive DTOs for Step Five.
     *
     * @param activitySupport List of ActivitySupport objects to convert.
     * @return List of corresponding ActivitySupportDtoToStepFive DTOs for Step Five.
     */
    List<ActivitySupportDtoToStepFive> toDto (List<ActivitySupport> activitySupport);
}
