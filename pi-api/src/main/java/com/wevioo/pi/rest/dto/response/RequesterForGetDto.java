package com.wevioo.pi.rest.dto.response;

import com.wevioo.pi.rest.dto.AttachmentLightDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * RequesterForGetDto
 */
@Getter
@Setter
public class RequesterForGetDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;


    /**
     * id
     */
    private String id;

    /**
     * identification Type
     */

    private String identificationType;
    /**
     * identification value
     */

    private String identificationValue;
    /**
     * first name
     */

    private String firstName;
    /**
     * last name
     */

    private String lastName;
    /**
     * social Reason
     */

    private String socialReason;

    /**
     * EMAIL
     */

    private String email;

    /**
     * ADDRESS
     */
    private String address;

    /**
     * PHONE NUMBER
     */

    private String  phoneNumber;
    /**
     * attachment
     */
    private AttachmentLightDto attachment;

}
