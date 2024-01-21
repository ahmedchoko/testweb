package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wevioo.pi.rest.dto.DelegationDto;
import com.wevioo.pi.rest.dto.GenericModel;
import com.wevioo.pi.rest.dto.GovernorateDto;
import com.wevioo.pi.rest.dto.LocationDto;
import com.wevioo.pi.rest.dto.ZipCodeDto;
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
public class PropertyDescriptionForGetDto implements Serializable {


    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;

    /**
     * Property Request's id
     */
    private String id;

    /**
     * Invest Type
     */
    private GenericModel investType;

    /**
     * Vocation
     */
    private GenericModel vocation;

    /**
     * Location
     */
    private GenericModel location;

    /**
     * Property Request's Usage
     */
    private GenericModel usage;

    /**
     * Property Request's Governorate
     */
    private GovernorateDto governorate;

    /**
     * Property Request's Delegation
     */
    private DelegationDto delegation;

    /**
     * Property Request's Locality
     */
    private LocationDto locality;

    /**
     * Property Request's ZipCode
     */
    private ZipCodeDto zipCode;

    /**
     * Property Request's Address
     */
    private  String address;

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

    /**
     * fiche Invest Id
     */
    private  String ficheInvestId ;


}
