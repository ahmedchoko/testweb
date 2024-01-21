package com.wevioo.pi.service.imp;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.Requester;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.RequesterMapper;
import com.wevioo.pi.repository.AttachmentsRepository;
import com.wevioo.pi.repository.RequesterRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.request.RequesterForPostDto;
import com.wevioo.pi.rest.dto.response.RequesterForGetDto;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.RequesterService;
import com.wevioo.pi.validation.RequesterValidation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RequesterServiceImp implements RequesterService {

	/**
	 * Inject {@link CommonService} bean
	 */
	@Autowired
	private CommonService commonService;

	/**
	 * Injected bean {@link UserRepository}
	 */
	@Autowired
	private KeyGenService keyGenService;

	/**
	 * Injected bean {@link RequesterRepository}
	 */
	@Autowired
	private RequesterRepository requesterRepository;

	/**
	 * Injected bean {@link AttachmentsRepository}
	 */
	@Autowired
	private AttachmentsRepository attachmentsRepository;

	/**
	 * Injected bean {@link RequesterRepository}
	 */
	@Autowired
	private RequesterMapper requesterMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequesterForGetDto findById(String id) {
		log.info("find requester by id ");
		Requester requester = requesterRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.REQUESTER_NOT_FOUND,
						"REQUESTER NOT FOUND WITH ID :" + id));

		return requesterMapper.toDto(requester);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Requester saveNewRequester(RequesterForPostDto requesterDto, User user, MultipartFile file,
			BindingResult result) throws IOException {

		validateFile(file, ApplicationConstants.PDF , result);
		RequesterValidation.validateRequester(requesterDto, result);
		Requester requester = requesterMapper.toEntity(requesterDto);
		requester.setId(keyGenService.getNextKey(KeyGenType.REQUESTER_KEY, true, null));
		requester.setCreatedBy(user);
		Attachment attachment = commonService.createAttachmentListForRequester(file, requester);
		requester = requesterRepository.save(requester);
		attachment.setRequester(requester);
		attachmentsRepository.save(attachment);

		return requester;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteRequesterById(String id) {
		attachmentsRepository.deleteAllByRequesterId(id);
		requesterRepository.deleteById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Requester updateExistingRequester(RequesterForPostDto requesterDto, User user, MultipartFile file,
			BindingResult result) throws IOException {
		RequesterValidation.validateRequester(requesterDto, result);
		Requester requester = requesterRepository.findById(requesterDto.getId())
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						" fiche invest not found with id : " + requesterDto.getId()));
		if( file != null && !file.isEmpty() && file.getSize() > 0) {
			validateFile(file , ApplicationConstants.PDF, result);
		}
		requester.setModifiedBy(user);
		requester.setIdentificationType(requesterDto.getIdentificationType());
		requester.setIdentificationValue(requesterDto.getIdentificationValue());
		requester.setFirstName(requesterDto.getFirstName());
		requester.setLastName(requesterDto.getLastName());
		requester.setSocialReason(requesterDto.getSocialReason());
		requester.setAddress(requesterDto.getAddress());
		requester.setPhoneNumber(requesterDto.getPhoneNumber());
		if( file != null && !file.isEmpty() && file.getSize() > 0) {
			attachmentsRepository.deleteAllByRequesterId(requesterDto.getId());
			Attachment attachment = commonService.createAttachmentListForRequester(file, requester);
			attachment.setRequester(requester);
			attachmentsRepository.save(attachment);
		}
		requester = requesterRepository.save(requester);
		return requesterRepository.save(requester);

	}

	/**
	 * Validate File
	 * 
	 * @param file      MultipartFile
	 * @param extension file's extension
	 */
	private void validateFile(MultipartFile file, String extension , Errors errors) {
		if (file == null || file.isEmpty()) {
			errors.rejectValue( "file",ApplicationConstants.FILE_IS_REQUIRED , ApplicationConstants.FILE_IS_REQUIRED );
			throw new BadRequestException("400", errors);
		}
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!extension.equals(ext)) {
			errors.rejectValue("file" , ApplicationConstants.FILE_EXTENSION_INVALID , ApplicationConstants.FILE_EXTENSION_INVALID);
			 throw new BadRequestException("400", errors);
		}
	}

}
