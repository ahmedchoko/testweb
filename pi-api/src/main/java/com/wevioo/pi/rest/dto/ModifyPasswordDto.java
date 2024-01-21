package com.wevioo.pi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author shl
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyPasswordDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = -423245530464302257L;

    /**
     * oldPassword
     */
    private String oldPassword;


    /**
     * newPassword
     */
    private String newPassword;

}

