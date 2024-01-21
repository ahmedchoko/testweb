package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class AttachmentLightDto  implements Serializable{
	/**
	 * Serial Number.
	 */
	private static final long serialVersionUID = -4241864443531464345L;
	/**
	 * AttachmentLightDto's attachmentId
	 */
	private String attachmentId; 
	/**
	 * AttachmentLightDto's fileName
	 */
    private String fileName;
}
