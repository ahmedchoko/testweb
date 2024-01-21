package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.ActivityTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * Activity Declaration Dto
 */
@Getter
@Setter
public class ActivityDeclarationDto {

    /**
     * ID of the activity declaration.
     */

    private String id;

    /**
     * Sector of the activity.
     */

    private  GenericModel activitySector;

    /**
     * Subsector of the activity.
     */

    private    GenericModel activitySubSector;

    /**
     * Group of the activity.
     */

    private  GenericModel activityGroup;

    /**
     * Class of the activity.
     */

    private  GenericModel activityClass;

    /**
     * Type of the activity.
     */

    private ActivityTypeEnum type;
}
