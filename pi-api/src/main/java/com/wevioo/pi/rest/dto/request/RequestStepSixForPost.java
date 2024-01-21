package com.wevioo.pi.rest.dto.request;

import com.wevioo.pi.domain.enumeration.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestStepSixForPost implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7732861602930366311L;
    /**
     * fiche Invest Id
     */
    private  String  ficheInvestId;
    /**
     * bankId
     */
    private  String bankId;
    /**
     * agencyId
     */
    private  String agencyId ;
    /**
     * language
     */
    private Language language;
    /**
     * examineAcceptance
     */
    private  Boolean examineAcceptance;
}
