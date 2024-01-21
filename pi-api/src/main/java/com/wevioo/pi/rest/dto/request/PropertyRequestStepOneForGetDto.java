package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude
public class PropertyRequestStepOneForGetDto implements Serializable {


    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;

    /**
     * Property Request's id
     */
    private String id;

    /**
     * Property Request's description
     */
    private PropertyDescriptionForGetDto propertyDescription;


}
