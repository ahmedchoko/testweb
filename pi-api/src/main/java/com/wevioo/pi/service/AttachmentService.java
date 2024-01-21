package com.wevioo.pi.service;

import java.util.List;

import com.wevioo.pi.domain.entity.request.Requester;
import org.springframework.web.multipart.MultipartFile;

import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;
/**
 * this interface defines methods to manage {@link Attachment}.
 *
 **/
public interface AttachmentService {

	/**
	 * Doawnload attachment by given parameter id
	 * 
	 * @param attachmentId {@link Attachment}'s ID
	 * @return {@link DownloadDocumentDto }
	 */
	DownloadDocumentDto dowloadAttachment(String attachmentId);

	/**
	 * Save attachment
	 * 
	 * @param details     details
	 * @param attachments list MultipartFile
	 */
	void saveAttachment(String details, List<MultipartFile> attachments);

	Attachment createAttachmentForClaim(MultipartFile  file, String claimId);
}
