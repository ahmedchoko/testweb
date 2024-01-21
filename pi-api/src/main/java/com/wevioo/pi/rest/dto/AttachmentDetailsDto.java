package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.OperationType;

import lombok.Getter;
import lombok.Setter;

/**
 * Attachment details
 * @author kad
 *
 */
@Getter
@Setter
public class AttachmentDetailsDto implements Serializable {
	
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 5491483884041153129L;

	/**
	 * AttachmentDetailsDto's declarationNature
	 */
	private DeclarationNature declarationNature;
	/**
	 * AttachmentDetailsDto's investmentType
	 */
	private InvestmentType investmentType;
	/**
	 * AttachmentDetailsDto's operationType
	 */
	private OperationType operationType;
	/**
	 * AttachmentDetailsDto's idForm
	 */
	private String idForm;
	/**
	 * AttachmentDetailsDto's sectionId
	 */
	private Long sectionId;
	/**
	 * AttachmentDetailsDto's documentId
	 */
	private Long documentId;
	
}
