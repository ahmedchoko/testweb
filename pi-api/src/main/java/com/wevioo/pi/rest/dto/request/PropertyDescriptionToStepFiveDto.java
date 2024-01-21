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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyDescriptionToStepFiveDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 5837797173993528561L;

    /**
     * Property Request's id
     */
    private String id;

    /**
     * Invest Type
     */
    private String investType;

    /**
     * Vocation
     */
    private String vocation;

    /**
     * Location
     */
    private String location;

    /**
     * Property Request's Usage
     */
    private String usage;

    /**
     * Property Request's Governorate
     */
    private String governorate;

    /**
     * Property Request's Delegation
     */
    private String delegation;

    /**
     * Property Request's Locality
     */
    private String locality;

    /**
     * Property Request's ZipCode
     */
    private String zipCode;

    /**
     * Property Request's Address
     */
    private String address;

    /**
     * Property Request's isRegistered
     */
    private Boolean isRegistered;

    /**
     * Property Request's name of land title
     */
    private String nameOfLandTitle;

    /**
     * Property Request's land title number
     */
    private Long landTitleNumber;

}
