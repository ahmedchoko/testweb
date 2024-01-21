package com.wevioo.pi.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class InvestorForPutDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;

    /**
     * Investor's id
     */
    private String id;

    /**
     * Investor's email
     */
    private String email;

    /**
     * Investor is active ?
     */
    private boolean active;

}
