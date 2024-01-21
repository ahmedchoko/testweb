package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * AgencyDto
 */
@Getter
@Setter
public class AgencyDto implements Serializable {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7659556534619721646L;
    /**
     * AGENCY id.
     */
    private String id;


    /**
     * AGENCY's label
     */
    private String label;
}
