package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.ActivityTypeEnum;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class ActivitySupportDto {
    /**
     * id
     */
    private String id;

    /**
     * Type of activity support.
     */
    private  GenericModel typeActivitySupport;

    /**
     * Issuing authority of the support.
     */

    private GenericModel issuingAuthority;

    /**
     * Support number.
     */

    private String supportNumber;

    /**
     * Date of support issuance.
     */
    private Date dateSupport;

    /**
     * Type of support (Enum).
     */
    private ActivityTypeEnum activityType;

}
