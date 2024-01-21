package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AttachmentDetailsForGetDto implements Serializable{
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 4882658918396514831L;
	/**
	 * AttachmentDetailsForGetDto's idForm
	 */
	private String idForm;
	/**
	 * AttachmentDetailsForGetDto's sectionId
	 */
	private Long sectionId;
	/**
	 * AttachmentDetailsForGetDto's documentId
	 */
	private Long documentId;
	/**
	 * AttachmentDetailsForGetDto's attachmentId
	 */
	private String attachmentId;
}
