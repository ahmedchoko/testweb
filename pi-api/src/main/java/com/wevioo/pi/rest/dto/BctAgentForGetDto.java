package com.wevioo.pi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BctAgentForGetDto {


    /**
     * id
     */
    private String id;

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
}

