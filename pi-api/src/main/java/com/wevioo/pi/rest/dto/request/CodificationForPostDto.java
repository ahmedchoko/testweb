package com.wevioo.pi.rest.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CodificationForPostDto implements Serializable {

    private static final long serialVersionUID = -3834465204646182636L;

    /**
     * address
     */
    String address ;
    /**
     * identification Value
     */
    String uniqueIdentifier;

    /**
     *  social Reason
     */
    String socialReason;

}
