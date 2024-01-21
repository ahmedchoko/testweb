package com.wevioo.pi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity  Category Specific ReferentialDto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategorySpecificReferentialDto {
    /**
     *  id
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
     * specific Referential Dto List
     */
    private List<SpecificReferentialDto> specificReferentialList;
}
