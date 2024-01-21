package com.wevioo.pi.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wevioo.pi.domain.enumeration.DeclarationNature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


/**
 * Requester For Post Dto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude
public class RequesterForPostDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = -9193194792996049806L;

    /**
     * id step
     */
    private String id;

    /**
     * identification Type
     */

    private String identificationType;
    /**
     * identification value
     */

    private String identificationValue;
    /**
     * first name
     */

    private String firstName;
    /**
     * last name
     */

    private String lastName;
    /**
     * social Reason
     */

    private String socialReason;

    /**
     * EMAIL
     */

    private String email;

    /**
     * ADDRESS
     */
    private String address;

    /**
     * PHONE NUMBER
     */

    private String  phoneNumber;
    /**
     * declarationNature
     */
    private DeclarationNature declarationNature;

}
