package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankDto  implements Serializable{
	
	/**
	 * Serial Number
	 */
    private static final long serialVersionUID = 2855262683302144252L;
	/**
     * id
     */
   private String id;
    /**
     * label
     */
   private  String label;
    /**
     * code
     */
   private String code;
}
