package com.wevioo.pi.rest.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocumentConfigDto implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 1489313317350114480L;
	/**
	 * DocumentConfigDto's id
	 */
	private Long id;
	/**
	 * DocumentConfigDto's label
	 */
	private String label;
	/**
	 * DocumentConfigDto's description
	 */
	private String description;

	/**
	 * DocumentConfigDto in this section is Mandatory? YES: NO
	 */
	private Boolean isMandatory;

	/**
	 * DocumentConfigDto in this section request number
	 */

	private Integer requestedNumber;
	/**
	 * DocumentConfigDto's attachments
	 */
	private List<AttachmentLightDto> attachments;
}
