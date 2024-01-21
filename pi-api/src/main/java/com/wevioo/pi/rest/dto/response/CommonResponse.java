package com.wevioo.pi.rest.dto.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CommonResponse  implements Serializable{

	/**
	 * Serial Number 
	 */
	private static final long serialVersionUID = 2885792910231178919L;
	/**
     * Represents the current page number.
     */
    private Integer page;
    /**
     * Represents the number of items per page.
     */
    private Integer pageSize;
  
    /**
     * Represents the total number of pages available.
     */
    private Integer totalPage;
    /**
     * Represents the total number of elements.
     */
    private long totalElement;
}
