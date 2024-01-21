package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wevioo.pi.domain.enumeration.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectInvestRequestStepZeroForPostDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = -9193199692996049806L;
    /**
     * ficheInvestId
     */
    private String ficheInvestId;
    /**
     * operationType
     */
    // Adding the Operation Type to Dto to dynamically determine the fields displayed in subsequent steps based on this type
    private OperationType operationType;
    /**
     * investorId
     */
    private String  investorId;
    /**
     * hasRequester
     */
    private Boolean hasRequester;
    /**
     * requester
     */
    private RequesterForPostDto requester;

    /**
     * file
     */
    @JsonIgnore
    private  String file;
}
