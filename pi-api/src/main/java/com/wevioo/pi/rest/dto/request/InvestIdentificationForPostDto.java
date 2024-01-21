package com.wevioo.pi.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestIdentificationForPostDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = -9193199587483049806L;
    /**
     * fiche Invest Id
     */
    private String ficheInvestId;

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
    private Long legalFormId;

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
    private ActivityDeclarationForPostDto mainActivityDeclaration;

    /**
     *  main Activity Support list
     */
    private List<ActivitySupportForPostDto> mainActivitySupportList;

    /**
     *  secondary Activity Declaration list
     */
    private ActivityDeclarationForPostDto activityDeclarationSecondary;

    /**
     *  secondary Activity Support list
     */
    private List<ActivitySupportForPostDto> secondaryActivitySupportList;


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

    private Boolean bctAuthorization;


}
