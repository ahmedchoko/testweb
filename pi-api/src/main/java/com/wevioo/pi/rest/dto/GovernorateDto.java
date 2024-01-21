package com.wevioo.pi.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GovernorateDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 5837797173993528561L;

    /**
     * GovernorateDto's id.
     */
    private String id;

    /**
     * GovernorateDto's label
     */
    private String label;
}
