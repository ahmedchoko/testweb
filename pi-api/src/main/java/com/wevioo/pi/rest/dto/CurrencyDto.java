package com.wevioo.pi.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * CurrencyForGetDto
 *
 * @author shl
 *
 */

@Getter
@Setter
public class CurrencyDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -7811059427197411835L;

    /**
     * Currency's id.
     */
    private String id;


    /**
     * Currency's code
     */
    private String code;


    /**
     * Currency's label
     */
    private String label;

}