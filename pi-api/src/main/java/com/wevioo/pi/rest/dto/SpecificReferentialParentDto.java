package com.wevioo.pi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Specific Referential Parent Dto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificReferentialParentDto {

    /**
     * id
     */
    private Long id;

    /**
     * Code of the   class.
     */
    private String code;
}
