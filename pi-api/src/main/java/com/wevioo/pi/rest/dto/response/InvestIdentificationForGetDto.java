package com.wevioo.pi.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wevioo.pi.rest.dto.ActivityDeclarationDto;
import com.wevioo.pi.rest.dto.ActivitySupportDto;
import com.wevioo.pi.rest.dto.GenericModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * InvestIdentificationForGetDto class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestIdentificationForGetDto {
    /**
     * ficheInvestId
     */
    String ficheInvestId;
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
    private  GenericModel legalForm;

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
     * activity Declarations list
     */
    @JsonIgnore
    private List<ActivityDeclarationDto> activityDeclarations;
    /**
     * activitySupports
     */
    @JsonIgnore
    private List<ActivitySupportDto> activitySupports;
        /**
         *  main Activity Declaration
         */

        ActivityDeclarationDto  mainActivityDeclaration;


        /**
         *   main Activity Support List
         */
        private List<ActivitySupportDto> mainActivitySupportList;
        /**
         * activity Declaration Secondary
         */
        ActivityDeclarationDto activityDeclarationSecondary;


        /**
         *   main Activity Support List
         */
        private List<ActivitySupportDto> secondaryActivitySupportList;


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
