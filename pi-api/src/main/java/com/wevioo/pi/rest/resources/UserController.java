package com.wevioo.pi.rest.resources;


import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.exception.EmailSendingException;
import com.wevioo.pi.rest.dto.AccountSetPasswordDto;
import com.wevioo.pi.rest.dto.ActivationKey;
import com.wevioo.pi.rest.dto.ForgetPasswordDto;
import com.wevioo.pi.rest.dto.MessageDto;
import com.wevioo.pi.rest.dto.ModifyPasswordDto;
import com.wevioo.pi.rest.dto.MyProfileDto;
import com.wevioo.pi.rest.dto.UserDto;
import com.wevioo.pi.rest.dto.response.EmailDto;
import com.wevioo.pi.service.InvestorService;
import com.wevioo.pi.service.imp.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


/**
 * The {@code UserResources} class is a Spring REST controller that handles user-related operations and endpoints.
 *
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.web.bind.annotation.GetMapping
 * @see org.springframework.http.ResponseEntity
 * @see org.springframework.web.bind.annotation.RequestParam
 * @see UserDto
 */

@Slf4j
@RestController
@RequestMapping(value = "/users" , produces =  "application/json;charset=UTF-8")
public class UserController {

    /**
     * userService
     */
    @Autowired
    private UserService userService;

    /**
     * investorService
     */
    @Autowired
    private InvestorService investorService;

    /**
     *
     * @param forgetPasswordDto
     * @return
     */
    @PostMapping("/forgot/password")
    public Object sendResetMail(@RequestBody ForgetPasswordDto forgetPasswordDto){
        try {
            userService.sendResetMail(forgetPasswordDto);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDto(
                    "Reset link sent to you at the following email address:" + forgetPasswordDto.getEmail()));
        } catch (MessagingException e) {
            log.error("Error sending email:", e);
            throw new EmailSendingException(ApplicationConstants.ERROR_EMAIL_SEND  , "Error sending Email");
        }
    }



    /**
     * modify password
     */
    @PostMapping("/modify/password")
    public Object modifyPassword(@RequestBody ModifyPasswordDto modifyPasswordDto, BindingResult result) {
    	log.info("modify PWD");
        userService.modifyPassword(modifyPasswordDto, result);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("password successfully modified"));
    }

    /**
     *
     * @param accountSetPasswordDto
     * @param result
     * @return
     */
    @PostMapping("/reset/password")
    public Object resetPassword(@RequestBody AccountSetPasswordDto accountSetPasswordDto, BindingResult result) {
        userService.resetPassword(accountSetPasswordDto, result);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("password successfully reset"));
    }

    /**
     * Check an activation link
     */
    @PostMapping("/link")
    public Object checkActivationLink(@RequestBody ActivationKey activationKey) {
        userService.checkActivationLink(activationKey.getActivationKey());
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("Link checked"));
    }

    /**
     * https for get user by username
     * @param username username of user
     * @return UserDto
     */
    @GetMapping
    public UserDto findUserByLogin(@RequestParam String username){
        return userService.findUserByLoginAndIsActive(username , Boolean.TRUE);
    }

    @GetMapping(path = "/me")
    public MyProfileDto getUserInfos() {
        return userService.getProfile();

    }
    /**
     * Verify if email exists or not
     *
     * @param email  email to verify
     * @param result list of errors
     * @return 200/ 400
     */
    @PostMapping("/email/exists")
    public void verifyEmail(@RequestBody EmailDto email, BindingResult result) {
        userService.verifyMail(email.getEmail(), result);
    }

    /**
     * Handles HTTP GET requests to verify the nationality compatibility for a specific investor type.
     *
     * This endpoint checks if a person with the specified investor type and nationality is compatible.
     * The result is a boolean indicating whether the nationality is compatible with the investor type.
     *
     * @param investorType The type of the person, specified by {@link PersonTypeEnum}.
     * @param nationality The nationality of the person.
     * @return {@code true} if the specified nationality is compatible with the investor type, {@code false} otherwise.
     */
    @GetMapping("/nationality/check")
    public Boolean verifyNationalityCompatibility(@RequestParam(required = true) PersonTypeEnum investorType,
                                                  @RequestParam(required = true) String nationality){
        return investorService.verifyNationality(investorType, nationality);
    }
}
