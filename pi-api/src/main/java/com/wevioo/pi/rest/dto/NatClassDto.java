package com.wevioo.pi.rest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * NatClassDto
 */
@Getter
@Setter
public class NatClassDto {

    /**
     *  Nat  Group id.
     */
    private String id;


    /**
     *   Nat  class  label;
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


    /**
     *  Nat  group  id
     */
    private  String groupId;
}
