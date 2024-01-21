package com.wevioo.pi.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a paginated list of claims for retrieval.
 */
@Getter
@Setter
public class ClaimForGetListDto {

	/**
	 * Current page number.
	 */
	private Integer page;

	/**
	 * Number of items per page.
	 */
	private Integer pageSize;

	/**
	 * Total number of pages.
	 */
	private Integer totalPage;

	/**
	 * Total number of elements across all pages.
	 */
	private long totalElement;

	/**
	 * List of claims in the current page.
	 */
	private List<ClaimForGetDto> content = new ArrayList<>();
}
