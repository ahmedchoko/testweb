package com.wevioo.pi.rest.dto;


import lombok.Getter;
import lombok.Setter;


/**
 * Nat Group Dto
 */
@Getter
@Setter
public class NatGroupDto {
    /**
     *  Nat  Group id.
     */
    private String id;


    /**
     *   Nat Group label;
     */
    private String  label;
    /**
     *  Nat  section id
     */
    private  String natSectionId;

    /**
     *  Nat sub   section id
     */
    private  String natSubSectionId;
}
