package com.wevioo.pi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * Entity    Specific ReferentialDto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificReferentialDto {
    /**
     * id
     */
    private Long id;

    /**
     * Code of the   class.
     */
    private String code;

    /**
     * label of the   class.
     */

    private String label;
    /**
     *  description
     */
    private String description;
    /**
     *  parent
     */
    private SpecificReferentialParentDto parent;
}
