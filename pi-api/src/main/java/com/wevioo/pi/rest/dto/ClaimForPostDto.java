package com.wevioo.pi.rest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing details required for creating a new claim.
 */
@Getter
@Setter
public class ClaimForPostDto {

	/**
	 * Cell phone number associated with the new claim.
	 */
	private String cellPhone;

	/**
	 * Email address associated with the new claim.
	 */
	private String email;

	/**
	 * Description of the new claim.
	 */
	private String description;

	/**
	 * Reason for the new claim.
	 */
	private String reason;
	/**
	 * User ID associated with the new claim.
	 */
	private String userId;
}
