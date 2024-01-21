package com.wevioo.pi.service.imp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.domain.entity.config.Document;
import com.wevioo.pi.domain.entity.config.Section;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.enumeration.DepositType;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.exception.ConflictException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.repository.AttachmentsRepository;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.DocumentRepository;
import com.wevioo.pi.repository.SectionRepository;
import com.wevioo.pi.rest.dto.AttachmentDetailsDto;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;
import com.wevioo.pi.service.AttachmentService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ParameterService;
import com.wevioo.pi.utility.FileUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * this class implements {@link AttachmentService} service.
 */
@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {
	/**
	 * Injected bean {@link AttachmentsRepository }
	 */
	@Autowired
	AttachmentsRepository attachmentsRepository;
	/**
	 * Inject {@link ParameterService} bean
	 */
	@Autowired
	private ParameterService parameterService;

	/**
	 * Inject {@link ObjectMapper} bean
	 */
	@Autowired
	private ObjectMapper objectMapper;
	/**
	 * Injected bean {@link KeyGenService}
	 */
	@Autowired
	private KeyGenService keyGenService;

	/**
	 * Injected bean {@link SectionRepository}
	 */
	@Autowired
	private SectionRepository sectionRepository;

	/**
	 * Injected bean {@link DocumentRepository}
	 */
	@Autowired
	private DocumentRepository documentRepository;

	/**
	 * Injected bean {@link FileUtils}
	 */
	@Autowired
	private FileUtils fileUtils;
	/**
	 * Injected bean {@link DirectInvestRequestRepository}
	 */

	@Autowired
	private DirectInvestRequestRepository directInvestRequestRepository;

	/**
	 * {@inheritDoc}
	 */
	public DownloadDocumentDto dowloadAttachment(String attachmentId) {
		Optional<Attachment> attachment = attachmentsRepository.findById(attachmentId);

		if (!attachment.isPresent()) {
			throw new DataNotFoundException(ApplicationConstants.ATTACHMENT_NOT_FOUND_WITH_ID,
					"No Attachment found with id " + attachmentId);
		}
		Attachment attachmentSaved = attachment.get();
		ByteArrayResource resource = null;
		try {
			resource = new ByteArrayResource(Files.readAllBytes(
					Paths.get(attachmentSaved.getFileUrl() + File.separator + attachmentSaved.getFileName())));
			String base64Content = Base64.getEncoder().encodeToString(resource.getByteArray());
			return DownloadDocumentDto.builder().contentType(attachmentSaved.getContentType()).content(base64Content)
					.fileName(attachmentSaved.getFileName()).build();
		} catch (IOException e) {
			throw new DataNotFoundException(ApplicationConstants.ERROR_NO_FILE_FOUND,
					ApplicationConstants.NO_FILE_FOUND_WITH_PATH + attachmentSaved.getFileUrl());
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public void saveAttachment(String details, List<MultipartFile> attachmentFiles) {
		log.info("create Attachment");
		try {
			AttachmentDetailsDto attachmentDetailsDto = objectMapper.readValue(details, AttachmentDetailsDto.class);
			// BindingResult bindingResult = new
			// BeanPropertyBindingResult(attachmentDetailsDto, "attachmentDetailsDto");

			Parameter param = parameterService.getByKey(ApplicationConstants.REPOSITORY_URL);
			String basePath = param.getParameterValue();

			DirectInvestRequest directInvestRequest = directInvestRequestRepository
					.findById(attachmentDetailsDto.getIdForm())
					.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
							"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + attachmentDetailsDto.getIdForm()));
			Optional<Section> section = sectionRepository.findById(attachmentDetailsDto.getSectionId());
			if (!section.isPresent()) {
				throw new DataNotFoundException(ApplicationConstants.SECTION_NOT_FOUND_WITH_ID,
						"No Section found with id " + attachmentDetailsDto.getSectionId());
			}

			Optional<Document> document = documentRepository.findById(attachmentDetailsDto.getDocumentId());
			if (!document.isPresent()) {
				throw new DataNotFoundException(ApplicationConstants.DOCUMENT_NOT_FOUND_WITH_ID,
						"No Document found with id " + attachmentDetailsDto.getDocumentId());
			}

			String path = basePath + File.separator + attachmentDetailsDto.getDeclarationNature().toString();

			if (attachmentDetailsDto.getInvestmentType() != null) {
				path = path + File.separator + attachmentDetailsDto.getInvestmentType().toString();
			}
			if (attachmentDetailsDto.getOperationType() != null) {
				path = path + File.separator + attachmentDetailsDto.getOperationType().toString();
			}
			path = path + File.separator + attachmentDetailsDto.getIdForm();
			Path uploadDir = Paths.get(path);

			if (!Files.exists(uploadDir)) {
				Files.createDirectories(uploadDir);
			}

			List<Attachment> attachmenListSaved = attachmentsRepository
					.findByDocumentIdAndSectionIdAndIdDeclarationNature(document.get().getId(), section.get().getId(),
							attachmentDetailsDto.getIdForm());
			deleteExistantAttchment(attachmenListSaved);

			final String pathFinal = path;
			attachmentFiles.forEach(file -> {

				String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
				if (Files.exists(Paths.get(pathFinal + File.separator + fileName))) {
					fileName = getNameWithDate(fileName);
				}
				Path filePath = uploadDir.resolve(fileName);

				try {
					Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
					Files.write(filePath, file.getBytes());

					Attachment attachmentSaved = new Attachment();
					attachmentSaved.setId(keyGenService.getNextKey(KeyGenType.ATTACHMENT_KEY, true, null));
					attachmentSaved.setCreationDate(new Date());

					attachmentSaved.setFileName(fileName);
					attachmentSaved.setSection(section.get());
					attachmentSaved.setDocument(document.get());
					attachmentSaved.setIdDeclarationNature(attachmentDetailsDto.getIdForm());
					attachmentSaved.setDeclarationNature(attachmentDetailsDto.getDeclarationNature());
					attachmentSaved.setContentType(file.getContentType());
					attachmentSaved.setFileSize(file.getInputStream().available());
					attachmentSaved.setFileUrl(uploadDir.toString());
					file.getInputStream().close();
					attachmentsRepository.save(attachmentSaved);
				} catch (IOException e) {
					log.error("An exception has been thrown : ", e);
					throw new ConflictException(ApplicationConstants.ERROR_SAVING_ATTACHMENT,
							"a problem has occurred when saving the file");
				}

			});

			if (directInvestRequest.getDepositType()!= null && directInvestRequest.getDepositType() == DepositType.DIRECT_DEPOSIT) {
				directInvestRequest.setDepositType(DepositType.ONLINE_DEPOSIT);
				directInvestRequest.setBank(null);
				directInvestRequest.setAgency(null);
				directInvestRequestRepository.save(directInvestRequest);
			}

		} catch (Exception e) {
			log.error("An exception has been thrown : ", e);
			throw new ConflictException(ApplicationConstants.ERROR_SAVING_ATTACHMENT,
					"a problem has occurred when saving the file");
		}

	}

	public Attachment createAttachmentForClaim(MultipartFile  file, String claimId)  {
		try {
			Parameter param = parameterService.getByKey(ApplicationConstants.REPOSITORY_URL);
			String basePath = param.getParameterValue();

			Attachment attachment = new Attachment();

			Path  uploadDir = Paths.get(basePath + File.separator + "CLAIM" + File.separator + claimId);

			if (!Files.exists(uploadDir)) {
				Files.createDirectories(uploadDir);
			}

			String fileName = org.springframework.util.StringUtils
					.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
			Path filePath = uploadDir.resolve(fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			Files.write(filePath, file.getBytes());
			attachment.setId(keyGenService.getNextKey(KeyGenType.ATTACHMENT_KEY , true , null));
			attachment.setFileName(fileName);
			attachment.setContentType(file.getContentType());
			attachment.setCreationDate(new Date());
			attachment.setFileSize(file.getInputStream().available());
			attachment.setFileUrl(uploadDir.toString());
			file.getInputStream().close();

			return attachment;
		} catch (Exception e) {
			log.error("An exception has been thrown : ", e);
			throw new ConflictException(ApplicationConstants.ERROR_SAVING_ATTACHMENT,
					"a problem has occurred when saving the file");
		}
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getNameWithDate(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		String fileNameWithoutExtention = fileName.substring(0, dotIndex);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		fileNameWithoutExtention = fileNameWithoutExtention + "_" + dateFormat.format(new Date());
		fileName = fileNameWithoutExtention + ".pdf";
		return fileName;
	}

	/**
	 * Delete existent documents
	 * 
	 * @param attachments List<Attachment>
	 */
	private void deleteExistantAttchment(List<Attachment> attachments) {

		if (!ObjectUtils.isEmpty(attachments)) {
			attachments.forEach(attachment -> {
				fileUtils.deleteFile(attachment.getFileUrl() + File.separator + attachment.getFileName());

			});
			attachmentsRepository.deleteAll(attachments);
		}

	}

}
