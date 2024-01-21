package com.wevioo.pi.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivitySupportForPostDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = -9193199692883049806L;

    private String id;

    /**
     *  id of  type activity support.
     */
    private Long typeActivitySupportId;

    /**
     * id of the Issuing authority  support.
     */
    private Long issuingAuthorityId;

    /**
     * Support number.
     */
    private String supportNumber;

    /**
     * Date of support issuance.
     */
    private Date dateSupport;



}
