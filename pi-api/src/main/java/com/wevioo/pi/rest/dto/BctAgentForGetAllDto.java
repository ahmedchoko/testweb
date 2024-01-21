package com.wevioo.pi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BctAgentForGetAllDto {
    /**
     * id
     */
    private String id;

    /**
     * User first name.
     */
    private String firstName;
    /**
     * User last name.
     */
    private String lastName;
    /**
     * registration Number
     */
    private String  registrationNumber;
    /**
     * service Assignment
     */
    private  String serviceAssignment;
    /**
     * creation Date
     */
    private Date creationDate;
}
