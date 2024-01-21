package com.wevioo.pi.service.imp;

import com.wevioo.pi.domain.enumeration.StepEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.AuthorisationsRequired;
import com.wevioo.pi.domain.entity.request.PropertyRequest;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.AuthorisationRequiredMapper;
import com.wevioo.pi.repository.PropertyRequestRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.request.AuthorizationsRequiredForPostDto;
import com.wevioo.pi.rest.dto.response.AuthorizationRequiredForGetDto;
import com.wevioo.pi.service.AuthorizationRequiredService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.AuthorizationRequiredValidator;

@Service
public class AuthorizationRequiredServiceImpl implements AuthorizationRequiredService {

	/**
	 * Injected bean {@link SecurityUtils}
	 */

	@Autowired
	private SecurityUtils securityUtils;

	/**
	 * Injected bean {@link UtilityService}
	 */

	@Autowired
	private UtilityService utilityService;

	/**
	 * Injected bean {@link UserRepository}
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Injected bean {@link AuthorisationRequiredMapper}
	 */
	@Autowired
	private AuthorisationRequiredMapper authorisationRequiredMapper;

	/**
	 * Injected bean {@link KeyGenService}
	 */
	@Autowired
	private KeyGenService keyGenService;

	/**
	 * Injected bean {@link PropertyRequestRepository}
	 */
	@Autowired
	private PropertyRequestRepository propertyRequestRepository;

	@Override
	@Transactional
	public AuthorizationRequiredForGetDto saveAuthorizationRequired(
			AuthorizationsRequiredForPostDto authorizationsRequiredForPostDto, BindingResult result) {
		String userId = securityUtils.getCurrentUserId();

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));

		utilityService.isAuthorized(user,ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
		boolean isBanker = user.getUserType() == UserTypeEnum.BANKER;
		AuthorizationRequiredValidator.validatePost(authorizationsRequiredForPostDto, result, isBanker);
		PropertyRequest propertyRequest = propertyRequestRepository
				.findById(authorizationsRequiredForPostDto.getFicheInvestId())
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND,
						ApplicationConstants.NO_PROPERTY_REQUEST_FOUNDED_WITH_ID
								+ authorizationsRequiredForPostDto.getFicheInvestId()));

		utilityService.isAuthorizedForRequest(user, propertyRequest.getCreatedBy(),
				propertyRequest.getBanker(), propertyRequest.getStatus());

		AuthorisationsRequired authorisationsRequired = authorisationRequiredMapper
				.toEntity(authorizationsRequiredForPostDto);
		if (Boolean.TRUE.equals(authorizationsRequiredForPostDto.getAuthorisationBCT())) {
			authorisationsRequired
					.setReferenceAuthorisationBCT(authorizationsRequiredForPostDto.getReferenceAuthorisationBCT());
			authorisationsRequired.setAuthorisationBCTDate(authorizationsRequiredForPostDto.getAuthorisationBCTDate());
		}
		if (propertyRequest.getAuthorisationsRequired() == null) {
			authorisationsRequired.setId(keyGenService.getNextKey(KeyGenType.AUTHORISATION_REQUIRED_KEY, true, null));
			authorisationsRequired.setCreatedBy(user);
			propertyRequest.setStep(StepEnum.STEP_THREE);

		} else {

			authorisationsRequired.setModifiedBy(user);
			authorisationsRequired.setCreationDate(propertyRequest.getAuthorisationsRequired().getCreationDate());
			authorisationsRequired.setCreatedBy(propertyRequest.getAuthorisationsRequired().getCreatedBy());
			authorisationsRequired.setId(propertyRequest.getAuthorisationsRequired().getId());
		}
		propertyRequest.setAuthorisationsRequired(authorisationsRequired);
		propertyRequest = propertyRequestRepository.save(propertyRequest);
		AuthorizationRequiredForGetDto authorizationRequiredForGetDto = authorisationRequiredMapper
				.toDto(propertyRequest.getAuthorisationsRequired());
		authorizationRequiredForGetDto.setFicheInvestId(propertyRequest.getId());
		return authorizationRequiredForGetDto;
	}

	@Override
	public AuthorizationRequiredForGetDto getAuthorizationRequired(String ficheInvestId) {
		utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
		PropertyRequest propertyRequest = propertyRequestRepository.findById(ficheInvestId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND,
						ApplicationConstants.NO_PROPERTY_REQUEST_FOUNDED_WITH_ID + ficheInvestId));
		if (propertyRequest.getAuthorisationsRequired() == null) {
			throw new DataNotFoundException(ApplicationConstants.AUTHORISATION_REQUIRED_NOT_FOUND,
					ApplicationConstants.NO_AUTHORISATION_REQUIRED_WITH_ID + ficheInvestId);
		}
		AuthorizationRequiredForGetDto authorizationRequiredForGetDto = authorisationRequiredMapper
				.toDto(propertyRequest.getAuthorisationsRequired());
		authorizationRequiredForGetDto.setFicheInvestId(propertyRequest.getId());
		return authorizationRequiredForGetDto;

	}
}
