package com.wevioo.pi.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectInvestRequestStepOneForPost {


    private InvestIdentificationForPostDto investIdentification;
    /**
     *  main Activity Declaration
     */
    private ActivityDeclarationForPostDto mainActivityDeclaration;

}
