package com.wevioo.pi.rest.dto.request;

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
public class FicheInvestDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = 9097845521129096249L;

    /**
     * ficheInvestId
     */
    private String reference;

    /**
     * operationType
     */
    private OperationType formInvest;
}
