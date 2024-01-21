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
@JsonInclude
public class AuthorizationsRequiredForPostDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7712441602930366311L;

    /**
     * fiche invest id
     */
    private String ficheInvestId;

    /**
     * authorisation required id
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
