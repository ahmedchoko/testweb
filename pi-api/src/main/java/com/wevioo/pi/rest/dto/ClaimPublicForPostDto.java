package com.wevioo.pi.rest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing details required for creating a new public claim.
 * Extends {@link ClaimForPostDto} to inherit common fields.
 */
@Getter
@Setter
public class ClaimPublicForPostDto extends ClaimForPostDto {

	/**
	 * User first name.
	 */
	private String firstName;

	/**
	 * User last name.
	 */
	private String lastName;


}
