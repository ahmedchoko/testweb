package com.wevioo.pi.rest.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExistenceIdentificationTypeDto implements Serializable {

    private static final long serialVersionUID = -3194509775107788717L;

    /**
     * identificationType
     */
    String identificationType ;
    /**
     * uniqueIdentifier
     */
    String uniqueIdentifier ;

}
