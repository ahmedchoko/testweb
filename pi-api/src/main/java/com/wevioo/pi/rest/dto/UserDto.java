package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.ProfileEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * The {@code UserDto} class is a data transfer object (DTO) used for representing user data. It encapsulates user-related
 * information such as user ID, email, and password.
 *
 * @see lombok.Data
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see lombok.Builder
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable{
	/**
	 * Serial Number
	 */
    private static final long serialVersionUID = 5737129785261371032L;

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
     * User's type.
     */
    private UserTypeEnum userType;

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

    private ProfileEnum profile;


}
