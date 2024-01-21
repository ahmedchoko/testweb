package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wevioo.pi.rest.dto.response.RequesterForGetDto;
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
public class PropertyRequestStepZeroForGetDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;
    /**
     * ficheInvestId
     */

    String    ficheInvestId;

    /**
     * Property Request's has requester
     */
    private Boolean hasRequester;

    /**
     * Property Request's requester
     */
    private RequesterForGetDto requester;

    /**
     * investorId
     */
    private  String investorId;
}
