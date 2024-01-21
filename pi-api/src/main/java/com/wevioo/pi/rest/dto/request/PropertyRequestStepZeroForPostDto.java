package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PropertyRequestStepZeroForPostDto implements Serializable {


    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;

    /**
     * Property Request's id
     */
    private String ficheInvestId;


    /**
     * investorId
     */

    private String investorId;

    /**
     * Property Request's has requester?
     */
    private Boolean hasRequester;

    /**
     * Property Request's requester
     */
    private RequesterForPostDto requester;
    /**
     * file
     */
    @JsonIgnore
    private  String file;

}
