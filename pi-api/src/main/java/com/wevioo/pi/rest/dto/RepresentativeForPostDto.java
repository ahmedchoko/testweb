package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepresentativeForPostDto implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = -1808574258607615449L;

    /**
     * Representative's investorType
     */
    private PersonType representativeType;

    /**
     * Representative's uniqueId
     */
    private String uniqueId;

    /**
     * Representative email.
     */
    private String email;

    /**
     * Representative first name.
     */
    private String firstName;

    /**
     * Representative last name.
     */
    private String lastName;

    /**
     * Representative cell phone.
     */
    private String cellPhone;

    /**
     * Representative's nationality.
     */
    private String nationality;


    /**
     * Representative's countryOfResidency.
     */
    private String countryOfResidency;

    /**
     * Representative's address
     */
    private String address;

    /**
     * Representative's nationalId
     */
    private String nationalId;

    /**
     * Representative's birthdayDate
     */
    private Date birthdayDate;

    /**
     * Representative's nationalIdReleaseDate
     */
    private Date nationalIdReleaseDate;

    /**
     * Representative's Social reason
     */
    private String socialReason;



}
