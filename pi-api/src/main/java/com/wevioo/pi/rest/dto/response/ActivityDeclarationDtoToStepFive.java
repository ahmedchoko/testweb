package com.wevioo.pi.rest.dto.response;

import com.wevioo.pi.domain.enumeration.ActivityTypeEnum;
import com.wevioo.pi.rest.dto.GenericModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *  Activity Declaration Dto To Step Six
 */
@Getter
@Setter
public class ActivityDeclarationDtoToStepFive implements Serializable {

    private static final long serialVersionUID = -4447343874758091048L;

    /**
     * ID of the activity declaration.
     */

    private String id;

    /**
     *  Sector of the activity.
     */

    private GenericModel activitySector;

    /**
     * Subsector of the activity.
     */

    private    GenericModel  activitySubSector;

    /**
     * Group of the activity.
     */

    private   GenericModel activityGroup;

    /**
     * Class of the activity.
     */

    private   GenericModel activityClass;

    /**
     * Type of the activity.
     */

    private ActivityTypeEnum type;
}
