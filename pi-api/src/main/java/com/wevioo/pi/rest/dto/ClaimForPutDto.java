package com.wevioo.pi.rest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing details for updating an existing claim.
 */
@Getter
@Setter
public class ClaimForPutDto {

	/**
	 * Unique identifier of the claim to be updated.
	 */
	private String id;

	/**
	 * Response or resolution for the existing claim.
	 */
	private String response;
}
