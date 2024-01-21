package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.BctAgent;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.AlreadyExistException;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.IBctAgentMapper;
import com.wevioo.pi.repository.IBctAgentRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.BctAgentForGetDto;
import com.wevioo.pi.rest.dto.BctAgentForPostDto;
import com.wevioo.pi.rest.dto.BctAgentForPutDto;
import com.wevioo.pi.rest.dto.GenericNotification;
import com.wevioo.pi.rest.dto.BctAgentForGetAllDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.EmailService;
import com.wevioo.pi.service.IBctAgentService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.BctAgentValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.HashMap;


/**
 * Bct Agent Service Imp
 */
@Service
public class BctAgentServiceImp implements IBctAgentService {

	/**
	 * Injected bean {@link IBctAgentRepository}
	 */
	@Autowired
	private IBctAgentRepository iBctAgentRepository;

	/**
	 * Injected bean {@link IBctAgentMapper}
	 */
	@Autowired
	private IBctAgentMapper iBctAgentMapper;

	/**
	 * Injected bean {@link SecurityUtils}
	 */
	@Autowired
	private SecurityUtils securityUtils;

	/**
	 * Injected bean {@link UserRepository}
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * Injected bean {@link EmailService}
	 */
	@Autowired
	private EmailService emailService;

	/**
	 * Injected bean {@link MessageSource}
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Injected bean {@link PasswordEncoder}
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Injected bean {@link UtilityService}
	 */
	@Autowired
	private UtilityService utilityService;

	/**
	 * Injected bean {@link KeyGenService}
	 */
	@Autowired
	private KeyGenService keyGenService;
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public BctAgentForGetDto saveBctAgent(BctAgentForPostDto bctAgentDto, BindingResult result) throws MessagingException {
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_ADMIN_BCT);
		BctAgentValidation.validateBctAgentForPost(bctAgentDto, result);
		validateEmailExist(bctAgentDto.getEmail(), result);
		validateRegistrationNumberExist(bctAgentDto.getRegistrationNumber(), result);
		BctAgent bctAgent = iBctAgentMapper.toEntity(bctAgentDto);
		bctAgent.setId(keyGenService.getNextKey(KeyGenType.USER_KEY, true, null));
		bctAgent.setUserType(UserTypeEnum.BCT_AGENT);
		bctAgent.setIsAdmin(Boolean.FALSE);
		bctAgent.setPassword(passwordEncoder.encode(bctAgentDto.getPassword()));
		bctAgent.setCreatedBy(user);
		// send mail
		StringBuilder fullName = new StringBuilder();
		fullName.append(bctAgentDto.getFirstName());
		fullName.append(" ");
		fullName.append(bctAgentDto.getLastName());

		GenericNotification genericNotification = GenericNotification.builder()
				.language(bctAgentDto.getLanguage())
				.label("ACCOUNT_BCT_AGENT")
				.emailTo(bctAgentDto.getEmail())
				.build();

		HashMap<String, String> myHashMap = new HashMap<>();

		myHashMap.put(ApplicationConstants.GENERALDIRECTION, bctAgentDto.getGeneralManagementAssignment());
		myHashMap.put(ApplicationConstants.SERVICE, bctAgentDto.getServiceAssignment());
		myHashMap.put(ApplicationConstants.ID, bctAgent.getId());
		myHashMap.put(ApplicationConstants.FIRSTNAME, String.valueOf(fullName));

		myHashMap.put(ApplicationConstants.LOGIN, bctAgentDto.getEmail());
		myHashMap.put(ApplicationConstants.PSWD, bctAgentDto.getPassword());
		genericNotification.setAttributes(myHashMap);

		emailService.sendEmailSpecificTemplate(genericNotification);

		return iBctAgentMapper.toForGetDto(iBctAgentRepository.save(bctAgent));
	}

	@Override
	public PaginatedResponse<BctAgentForGetAllDto> findAll(String id, String registrationNumber, Integer page,
			Integer size, Sort sort) {
		Sort sortingCriteria = utilityService.sortingCriteria(sort, Sort.Direction.DESC,
				ApplicationConstants.CREATION_DATE);
		Pageable pageable = utilityService.createPageable(page != null ? page : 1, size != null ? size : 10,
				sortingCriteria);
		utilityService.isAuthorized(ApplicationConstants.USER_TYPE_ADMIN_BCT);
		PaginatedResponse<BctAgentForGetAllDto> response = new PaginatedResponse<>();
		Page<BctAgent> bctAgentPage = iBctAgentRepository.findAllBySearch(id, registrationNumber,
				UserTypeEnum.BCT_AGENT, pageable);
		response.setTotalElement(bctAgentPage.getTotalElements());
		response.setTotalPage(bctAgentPage.getTotalPages());
		response.setPageSize(bctAgentPage.getSize());
		response.setPage(bctAgentPage.getNumber());
		response.setContent(iBctAgentMapper.toForGetAllDto(bctAgentPage.getContent()));
		return response;
	}

	/**
	 * @param bctAgentDto
	 * @param result
	 * @return
	 */
	@Override
	@Transactional
	public BctAgentForGetDto updateBctAgent(String id, BctAgentForPutDto bctAgentDto, BindingResult result) {
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		utilityService.isAuthorized(user,ApplicationConstants.USER_TYPE_ADMIN_BCT);
		BctAgent bctAgent = iBctAgentRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						"No BctAgent not found with id: " + id));

		BctAgentValidation.validateBctAgentForPut(bctAgentDto, result);
		validateEmailExistAndIdNot(bctAgentDto.getEmail(), id, result);
		validateRegistrationNumberExistAndIdNot(bctAgentDto.getRegistrationNumber(), id, result);
		bctAgent.setModifiedBy(user);
		bctAgent.setFirstName(bctAgentDto.getFirstName());
		bctAgent.setLastName(bctAgentDto.getLastName());
		bctAgent.setGeneralManagementAssignment(bctAgentDto.getGeneralManagementAssignment());
		bctAgent.setServiceAssignment(bctAgentDto.getServiceAssignment());
		bctAgent.setIsActive(bctAgentDto.getIsActive());
		bctAgent.setLogin(bctAgentDto.getEmail());
		return iBctAgentMapper.toForGetDto(iBctAgentRepository.save(bctAgent));

	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	public BctAgentForGetDto findBctAgentById(String id) {
		utilityService.isAuthorized(ApplicationConstants.USER_TYPE_ADMIN_BCT);
		BctAgent bctAgent = iBctAgentRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + id));
		return iBctAgentMapper.toForGetDto(bctAgent);
	}

	/**
	 * Validate the existence of login
	 *
	 * @param email  String
	 * @param errors Errors
	 */
	private void validateEmailExist(String email, Errors errors) {
		if (userRepository.existsByLoginIgnoreCase(email)) {
			errors.rejectValue("email", ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
					ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS);
			throw new AlreadyExistException(ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
					ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS);
		}
	}

	private void validateEmailExistAndIdNot(String email, String id, Errors errors) {
		if (userRepository.existsByLoginIgnoreCaseAndAndIdNot(email, id)) {
			errors.rejectValue("email", ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
					ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS);
			throw new AlreadyExistException(ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
					ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS);
		}
	}

	/**
	 * validate Email registrationNumber And Id Not
	 * 
	 * @param registrationNumber
	 * @param id
	 * @param errors
	 */
	private void validateRegistrationNumberExistAndIdNot(String registrationNumber, String id, Errors errors) {
		if (iBctAgentRepository.findFirstByRegistrationNumberIgnoreCaseAndIdNot(registrationNumber, id).isPresent()) {
			errors.rejectValue("registrationNumber", ApplicationConstants.REGISTRATION_NUMBER_ALREADY_EXISTS,
					ApplicationConstants.REGISTRATION_NUMBER_ALREADY_EXISTS);
			throw new BadRequestException("400", errors);
		}
	}

	/**
	 * validate registrationNumber Exist
	 * 
	 * @param registrationNumber
	 * @param errors
	 */
	private void validateRegistrationNumberExist(String registrationNumber, Errors errors) {
		if (iBctAgentRepository.findFirstByRegistrationNumberIgnoreCase(registrationNumber).isPresent()) {
			errors.rejectValue("registrationNumber", ApplicationConstants.REGISTRATION_NUMBER_ALREADY_EXISTS,
					ApplicationConstants.REGISTRATION_NUMBER_ALREADY_EXISTS);
			throw new BadRequestException("400", errors);
		}
	}

}
