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
public class PropertyDescriptionForPostDto implements Serializable {


    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;

    /**
     * Invest Type
     */
    private Long investTypeId;

    /**
     * Vocation
     */
    private Long vocationId;

    /**
     * Location
     */
    private Long locationId;

    /**
     * Property Request's Usage
     */
    private Long usageId;

    /**
     * Property Request's Governorate
     */
    private String governorateId;

    /**
     * Property Request's Delegation
     */
    private String delegationId;

    /**
     * Property Request's Locality
     */
    private String localityId;

    /**
     * Property Request's ZipCode
     */
    private String zipCodeId;

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
