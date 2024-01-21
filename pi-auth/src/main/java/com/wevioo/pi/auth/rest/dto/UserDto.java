package com.wevioo.pi.auth.rest.dto;

import com.wevioo.pi.auth.domain.enumeration.ProfileEnum;
import com.wevioo.pi.auth.domain.enumeration.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    /**
     * id
     */
    private String id;

    /**
     * User password.
     */

    private String password;

    /**
     * User email.
     */
    private String email;

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
     * Creation date.
     */
    private Date creationDate;



    /**
     * Modification date.
     */
    private Date modificationDate;

    /**
     * password last modificationDate
     */
    private Date pwdModificationDate;

    /**
     * profile
     */
    private ProfileEnum profile;

    /**
     * User Type
     */

    private UserTypeEnum  userType;


}
