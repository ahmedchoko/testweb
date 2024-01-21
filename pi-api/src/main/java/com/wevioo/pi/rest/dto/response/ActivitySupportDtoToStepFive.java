package com.wevioo.pi.rest.dto.response;

import com.wevioo.pi.domain.enumeration.ActivityTypeEnum;
import com.wevioo.pi.rest.dto.GenericModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**7
 * Activity Support Dto ToS tep Six
 */
@Getter
@Setter
public class ActivitySupportDtoToStepFive implements Serializable {


    private static final long serialVersionUID = 3490724810115809691L;

    /**
     * id
     */
    private String id;

    /**
     * Type of activity support.
     */
    private GenericModel typeActivitySupport;

    /**
     * Issuing authority of the support.
     */

    private  GenericModel issuingAuthority;

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
