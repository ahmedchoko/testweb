package com.wevioo.pi.rest.dto;


import com.wevioo.pi.domain.entity.request.Action;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.domain.enumeration.StepEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Action Dto
 */
@Getter
@Setter
public class ActionDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 1055124688322435013L;
    /**
     * {@link  Action}    ID.
     */
    private  Long id;
    /**
     * {@link  Action}  USER ID.
     */
    private  String userId;
    /**
     * {@link  Action} step.
     */
    private StepEnum step;
    /**
     * {@link  Action} ficheId.
     */
    private  String ficheId;
    /**
     * {@link  Action} OperationType.
     */
    private OperationType operationType;
    /**
     *  * {@link  Action}   status
     */
    private RequestStatusEnum status;
}
