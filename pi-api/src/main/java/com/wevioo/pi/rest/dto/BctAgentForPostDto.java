package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BctAgentForPostDto {



    /**
     * User email.
     */
    private String  email;

    /**
     * User first name.
     */
    private String firstName;

    /**
     * User last name.
     */
    private String lastName;
    /**
     * User status.
     */
    private Boolean isActive;
    /**
     * registration Number
     */
    private String  registrationNumber;
    /**
     * General Management Assignment
     */
    private String generalManagementAssignment ;
    /**
     * service Assignment
     */
    private  String serviceAssignment;
    /**
     * locale language
     */
    private Language language;
    /**
     * User password.
     */

    private String password;

    /**
     * usser password Confirmation
     */
    private String passwordConfirmation;
}
