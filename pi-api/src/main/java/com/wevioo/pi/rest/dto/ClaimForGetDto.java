package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.ClaimStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * Data Transfer Object (DTO) representing details of a claim for retrieval.
 */
@Getter
@Setter
public class ClaimForGetDto {

	/**
	 * Unique identifier of the claim.
	 */
	private String id;

	/**
	 * Cell phone number associated with the claim.
	 */
	private String cellPhone;

	/**
	 * Email address associated with the claim.
	 */
	private String email;
	/**
	 * LastName of user associated with the claim.
	 */
	private String lastName;
	/**
	 * FirstName of user associated with the claim.
	 */
	private String firstName;
	/**
	 * User nature is FR associated with the claim.
	 */
	private String userNatureFr;
	/**
	 * User nature is EN associated with the claim.
	 */
	private String userNatureEn;

	/**
	 * User or entity to whom the claim is modifiedBy.
	 */
	private String modifiedBy;

	/**
	 * Status of the claim (e.g., RESOLVED, IN_PROGRESS).
	 */
	private ClaimStatusEnum status;

	/**
	 * Description of the claim.
	 */
	private String description;

	/**
	 * Response or resolution for the claim.
	 */
	private String response;

	/**
	 * Reason for the claim.
	 */
	private String reason;

	/**
	 * Date when the claim was created.
	 */
	private Date creationDate;

	/**
	 * Date when the claim was last modified.
	 */
	private Date modificationDate;


	/**
	 * Attachment details for the claim.
	 */
	private AttachmentRequestDto attachment;
}