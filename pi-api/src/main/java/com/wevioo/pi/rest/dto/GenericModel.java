package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Generic Model
 */
@Getter
@Setter
public class GenericModel implements Serializable{

	/**
	 * Serial Number
	 */
    private static final long serialVersionUID = 1217894425226412553L;
	String id;
    String label;
}
