package com.wevioo.pi.rest.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for download attachment
 * 
 * @author kad
 *
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadDocumentDto implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -7423268878106447083L;

	/**
	 * DownloadDocumentDto's content
	 */
	private String content;
	/**
	 * DownloadDocumentDto's contentType
	 */
	private String contentType;
	/**
	 * DownloadDocumentDto's fileName
	 */
	private String fileName;
}
