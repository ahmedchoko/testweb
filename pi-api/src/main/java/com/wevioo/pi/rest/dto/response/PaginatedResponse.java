package com.wevioo.pi.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * PaginatedResponse class represents a generic paginated response containing paginated data.
 *
 * @param <T> Type parameter for the content in the paginated response.
 */
@Getter
@Setter
public class PaginatedResponse <T> extends CommonResponse{
	/**
	 * Serial Number
	 */
	  private static final long serialVersionUID = 991806188942908720L;
	/**
     * Represents the content of the paginated response.
     */
    private List<T> content;
}
