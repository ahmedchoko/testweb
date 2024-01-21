package com.wevioo.pi.service;

import com.wevioo.pi.rest.dto.MyProfileDto;
import com.wevioo.pi.rest.dto.UserDto;
import org.springframework.validation.BindingResult;

public interface IUserService {

    /**
     * Finds a User by username and isActive status.
     *
     * @param username  The username of the User to find
     * @param isActive  The isActive status indicating whether the User is active or not
     * @return UserDto representing the found User matching the provided username and isActive status
     */
    UserDto findUserByLoginAndIsActive(String username , Boolean isActive);

    MyProfileDto getProfile();

    /**
     * @param mail verify the existence of an email
     */
    void verifyMail (String mail , BindingResult result ) ;
}
