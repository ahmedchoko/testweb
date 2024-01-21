package com.wevioo.pi.auth.rest.dto;

import com.wevioo.pi.auth.domain.enumeration.ProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationRequest {
    /**
     * Grant type
     */
    private String grantType;

    /**
     * User login
     */
    private String username;

    /**
     * User password
     */
    private String  password;

    /**
     * Token
     */
    private String token;

    /**
     * profile
     */
    private ProfileEnum profile;
}
