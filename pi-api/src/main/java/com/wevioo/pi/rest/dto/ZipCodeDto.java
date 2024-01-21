package com.wevioo.pi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZipCodeDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 5837797173993528561L;

    /**
     * ZipCodeDto's id.
     */
    private String id;

    /**
     * ZipCodeDto's label
     */
    private String label;
}
