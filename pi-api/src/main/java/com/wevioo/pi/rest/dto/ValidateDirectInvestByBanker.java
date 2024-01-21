package com.wevioo.pi.rest.dto;


import com.wevioo.pi.domain.enumeration.Language;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Validate DirectInvest By Banker
 */
@Getter
@Setter
public class ValidateDirectInvestByBanker implements Serializable {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = -2428209779090527918L;
    /**
     * fiche Invest Id
     */
    private String ficheInvestId;
    /**
     * Language
     */
    private Language language;
}
