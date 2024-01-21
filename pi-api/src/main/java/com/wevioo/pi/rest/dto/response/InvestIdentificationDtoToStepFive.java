package com.wevioo.pi.rest.dto.response;


import com.wevioo.pi.rest.dto.GenericModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * InvestIdentification Dto To Step Six class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestIdentificationDtoToStepFive implements Serializable {

    private static final long serialVersionUID = 694380705509440805L;
    /**
     * id
     */
    private String id;

    /**
     * uniqueIdentifier
     */
    private String uniqueIdentifier;

    /**
     * companyName
     */
    private String companyName;

    /**
     * legalForm
     */
    private GenericModel legalForm;

    /**
     * quoted
     */
    private Boolean quoted;
    /**
     * resident
     */
    private boolean resident;
    /**
     * has secondary Activity Support ?
     */
    private boolean secondaryActivitySupport;
    /**
     * has main Activity Support?
     */
    private boolean mainActivitySupport;
    /**
     * has secondary Activity Declaration ?
     */
    private boolean secondaryActivityDeclaration;



    /**
     *  main Activity Declaration
     */
    private List<ActivityDeclarationDtoToStepFive> activityDeclarations;

    /**
     *  secondary Activity Declaration list
     */
    private List<ActivitySupportDtoToStepFive> activitySupports;
    /**
     * authority Number
     */
    private  String  authorityNumber ;

    /**
     * authority  Date
     */

    private Date authorityDate ;

    /**
     * bct Authorization
     */
    private boolean bctAuthorization;
}
