package com.wevioo.pi.service.imp;

import java.util.Optional;

import javax.transaction.Transactional;

import com.wevioo.pi.domain.entity.referential.IdentificationType;
import com.wevioo.pi.service.IdentificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.referential.Codification;
import com.wevioo.pi.domain.entity.referential.CreMoralPerson;
import com.wevioo.pi.domain.entity.referential.RefCodificationPK;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.MoralPersonMapper;
import com.wevioo.pi.repository.CodificationRepository;
import com.wevioo.pi.repository.CreMoralPersonRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.request.CodificationForPostDto;
import com.wevioo.pi.service.CodificationService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.IdentificationTypeValidator;
import com.wevioo.pi.validation.MoralPersonValidation;

@Service
public class CodificationServiceImpl implements CodificationService {

	/**
	 * Injected bean {@link CodificationRepository}
	 */
	@Autowired
	CodificationRepository codificationRepository;
	/**
	 * Injected bean {@link MoralPersonValidation}
	 */
	@Autowired
	MoralPersonValidation moralPersonValidation;
	/**
	 * Injected bean {@link IdentificationTypeValidator}
	 */
	@Autowired
	IdentificationTypeValidator identificationTypeValidator;
	/**
	 * Injected bean {@link MoralPersonMapper}
	 */
	@Autowired
	MoralPersonMapper moralPersonMapper;
	/**
	 * Injected bean {@link UserRepository}
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * Injected bean {@link SecurityUtils}
	 */
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	CreMoralPersonRepository creMoralPersonRepository;

	/**
	 * Injected bean {@link IdentificationTypeService}
	 */
	@Autowired
	private IdentificationTypeService identificationTypeService;

	@Transactional
	@Override
	public CodificationForPostDto saveCodification(CodificationForPostDto codificationForPostDto,
			BindingResult result) {

		String userId = securityUtils.getCurrentUserId();
		if (!userRepository.existsById(userId)) {
			throw new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
					ApplicationConstants.USER_NOT_FOUND + userId);
		}

		// validate identification Type Existence in Identification  Type reference
		IdentificationType identificationTypeExist = identificationTypeService
				.findByType(ApplicationConstants.IDENTFIANT_UNIQUE);

		moralPersonValidation.validateMoralPerson(codificationForPostDto, result);

		identificationTypeValidator.validateIdentificationType(identificationTypeExist , ApplicationConstants.IDENTFIANT_UNIQUE,
				codificationForPostDto.getUniqueIdentifier(), result);

		if (result.hasErrors()) {
			throw new BadRequestException("400", result);
		}
		Codification codification = moralPersonMapper.toEntity(codificationForPostDto);
		codification.setId(new RefCodificationPK(codificationForPostDto.getUniqueIdentifier(),
				ApplicationConstants.MATRICULE_FISCAL));
		codificationRepository.save(codification);

		/** Add new data to CreMoralPerson 
		 *  TODO  TO BE deleted before BCT deployment 
		 * **/
		Optional<CreMoralPerson> crePSaved = creMoralPersonRepository
				.findCreMoralPersonByUniqueIdentification(codificationForPostDto.getUniqueIdentifier());
		if (!crePSaved.isPresent()) {
			CreMoralPerson creP = new CreMoralPerson();
			creP.setSocialReason(codificationForPostDto.getSocialReason());
			creP.setUniqueIdentification(codificationForPostDto.getUniqueIdentifier());
			Long idMax = creMoralPersonRepository.findMaximumRank();
			creP.setId(idMax + 1);
			creMoralPersonRepository.save(creP);
		}

		return codificationForPostDto;
	}
}
