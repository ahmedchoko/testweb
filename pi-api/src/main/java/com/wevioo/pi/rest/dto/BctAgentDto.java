package com.wevioo.pi.rest.dto;



import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BctAgentDto {

    /**
     * id
     */
    private String id;

    /**
     * User password.
     */

    private String password;

    /**
     * usser password Confirmation
     */
    private String passwordConfirmation;


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
     * User cell phone.
     */
    private String cellPhone;

    /**
     * User status.
     */
    private Boolean isActive;

    /**
     * expiration Date for otp Code
     */
    private Date expirationDate;

    /**
     * User's type.
     */
    private UserTypeEnum userType;

    /**
     * Creation date.
     */
    private Date creationDate;

    /**
     * Created by.
     */
    private UserDto createdBy;

    /**
     * Modification date.
     */
    private Date modificationDate;
    /**
     * Modified by.
     */
    private UserDto modifiedBy;
    /**
     * password last modificationDate
     */
    private Date pwdModificationDate;
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
     * IS Admin
     */

    private Boolean isAdmin;

    /**
     * locale language
     */
    private Locale locale ;


    /**
     * is first conextion
     */

    private  Boolean isFirstConnection;
}
