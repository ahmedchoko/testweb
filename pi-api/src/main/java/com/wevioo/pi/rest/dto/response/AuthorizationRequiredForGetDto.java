package com.wevioo.pi.rest.dto.response;

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
public class AuthorizationRequiredForGetDto implements Serializable {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = -7139054719695454100L;

    /**
     * fiche invest id
     */
    private String ficheInvestId;

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
