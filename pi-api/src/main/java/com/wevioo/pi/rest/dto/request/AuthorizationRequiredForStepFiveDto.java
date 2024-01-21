package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizationRequiredForStepFiveDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 5837797173993528561L;

    /**
     * id
     */
    private String id;

    /**
     * governor Authorisation Number
     */
    private Long governorAuthorisationNumber;

    /**
     * governor Authorisation Number
     */
    private Date governorAuthorisationDate;

    /**
     * authorisation BCT: YES / NO
     */
    private Boolean authorisationBCT;

    /**
     * reference Authorisation BCT
     */
    private String referenceAuthorisationBCT;

    /**
     * Authorisation BCT Date
     */
    private Date authorisationBCTDate;
}
