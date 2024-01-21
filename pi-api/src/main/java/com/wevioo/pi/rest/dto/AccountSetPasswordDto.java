package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountSetPasswordDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = -9193160692883049806L;


    /**
     * Activation key
     */
    private String activationKey;


    /**
     * user's password
     */
    private String password;
}
