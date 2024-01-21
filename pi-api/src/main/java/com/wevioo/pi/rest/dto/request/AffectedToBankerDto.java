package com.wevioo.pi.rest.dto.request;

import com.wevioo.pi.domain.enumeration.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AffectedToBankerDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = -6725016822602342755L;
    /**
     * banker id
     */
    private   String  bankerId;

    private List<FicheInvestDto> ficheInvestDtoList;
}
