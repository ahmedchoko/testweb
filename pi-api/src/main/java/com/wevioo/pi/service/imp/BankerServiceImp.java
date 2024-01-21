package com.wevioo.pi.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.wevioo.pi.domain.enumeration.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.AlreadyExistException;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.mapper.IBankerMapper;
import com.wevioo.pi.repository.BankRepository;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.IBankerRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.BankerForGetDto;
import com.wevioo.pi.rest.dto.BankerForPostDto;
import com.wevioo.pi.rest.dto.GenericNotification;
import com.wevioo.pi.rest.dto.BankerForPutDto;
import com.wevioo.pi.rest.dto.response.BankerLightForGetDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.rest.dto.BankerForGetAllDto;
import com.wevioo.pi.service.EmailService;
import com.wevioo.pi.service.IBankerService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.BankerValidation;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BankerServiceImp implements IBankerService {

	/**
	 * Injected bean {@link IBankerMapper }
	 */
	@Autowired
	IBankerMapper iBankerMapper;
	/**
	 * Injected bean {@link IBankerRepository }
	 */
	@Autowired
	IBankerRepository iBankerRepository;

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
	 * Injected bean {@link PasswordEncoder}
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Injected bean {@link MessageSource}
	 */
	@Autowired
	private MessageSource messageSource;
	/**
	 * Injected bean {@link EmailService}
	 */
	@Autowired
	private EmailService emailService;
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
	 * Injected bean {@link BankRepository}
	 */
	@Autowired
	private BankRepository bankRepository;
	/**
	 * Injected bean {@link DirectInvestRequestRepository}
	 */

	@Autowired
	private DirectInvestRequestRepository directInvestRequestRepository;

	/**
	 * Saves a new banker after performing various validations and processing.
	 *
	 * @param bankerDto The DTO (Data Transfer Object) containing banker information
	 *                  to be saved.
	 * @param result    The binding result for validation.
	 * @return BankerDto The DTO of the saved banker.
	 */
	@Transactional
	@Override
	public
	BankerForGetDto saveBanker(BankerForPostDto bankerDto, BindingResult result) throws MessagingException {
		log.info("start addBanker ...... ");
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		BankerValidation.validateBankerForPost(bankerDto, result);
		checkAuthority(user, bankerDto.getIsAdmin());
		validateEmailExist(bankerDto);
		Banker banker = iBankerMapper.toEntity(bankerDto);
		if (Boolean.TRUE.equals(banker.getIsAdmin())) {
			validateBank(banker, bankerDto.getBankId());
			validateIsActive(bankerDto.getBankId(), bankerDto.getIsActive());
		}
		banker.setPassword(passwordEncoder.encode(banker.getPassword()));

		banker.setUserType(
				banker.getIsAdmin() != null && banker.getIsAdmin() ? UserTypeEnum.ADMIN_BANKER : UserTypeEnum.BANKER);

			banker.setParent(Boolean.TRUE.equals(bankerDto.getIsAdmin()) ? null : (Banker) user);

		setBankToBanker(userId, bankerDto.getIsAdmin(), banker);
		banker.setCreatedBy(user);
		banker.setId(keyGenService.getNextKey(KeyGenType.USER_KEY, true, null));
		banker = iBankerRepository.save(banker);
		if (Boolean.TRUE.equals(banker.getIsAdmin())) {
			updateParentBanker(bankerDto.getBankId(), bankerDto.getIsActive(), banker);
		}
		if (Boolean.TRUE.equals(bankerDto.getIsActive())) {
			HashMap<String, String> myHashMap = new HashMap<>();

			if(Boolean.TRUE.equals(bankerDto.getIsAdmin())) {
				// sending mail for BANKER ADMIN
				GenericNotification genericNotification = GenericNotification.builder()
						.language(banker.getLanguage())
						.label("ACCOUNT_ADMIN_BANKER")
						.emailTo(banker.getLogin())
						.build();
				myHashMap.put(ApplicationConstants.INTERMIDIAIRE,banker.getApprovedIntermediary().getLabel());
				myHashMap.put(ApplicationConstants.TYPEADMIN , banker.getTypeAdministrator().name());
				myHashMap.put(ApplicationConstants.ID, banker.getId());
				myHashMap.put(ApplicationConstants.NOM, banker.getFirstName() + " " + banker.getLastName());
				myHashMap.put(ApplicationConstants.LOGIN, banker.getLogin());
				myHashMap.put(ApplicationConstants.PSWD, bankerDto.getPassword());
				genericNotification.setAttributes(myHashMap);

				emailService.sendEmailSpecificTemplate(genericNotification);

			}else {
				// sending mail for BANKER
				GenericNotification genericNotification = GenericNotification.builder()
						.language(banker.getLanguage())
						.label("ACCOUNT_BANKER")
						.emailTo(banker.getLogin())
						.build();

				myHashMap.put(ApplicationConstants.LOGIN, banker.getLogin());
				myHashMap.put(ApplicationConstants.PSWD, bankerDto.getPassword());
				genericNotification.setAttributes(myHashMap);
				emailService.sendEmailSpecificTemplate(genericNotification);
			}
		}
		return iBankerMapper.toBankerForGetDto(banker);
	}

	/**
	 * Retrieves a page of BankerDto objects based on search criteria, page number,
	 * size, and sorting options.
	 *
	 * @param page     The page number to retrieve (default is 1).
	 * @param size     The size of each page (default is 10).
	 * @param sort     The sorting order for the results.
	 * @param bankerId The ID of the banker to search for.
	 * @param bankId   The ID of the bank associated with the banker.
	 * @return PaginatedResponse<BankerForGetAllDto> A paginated list of BankerDto
	 *         objects based on the search criteria.
	 */
	@Override
	public PaginatedResponse<BankerForGetAllDto> findAllBySearch(Integer page, Integer size, Sort sort, String bankerId,
			String bankId) {
		Sort sortingCriteria = utilityService.sortingCriteria(sort, Sort.Direction.DESC,
				ApplicationConstants.CREATION_DATE);
		Pageable pageable = utilityService.createPageable(page != null ? page : 1, size != null ? size : 10,
				sortingCriteria);
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_BCT_ADMIN_AND_ADMIN_BANKER_BCT_AGENT);

		PaginatedResponse<BankerForGetAllDto> response = new PaginatedResponse<>();
		Page<Banker> bankersPage;

		switch (user.getUserType()) {
		case BCT_ADMIN:
			bankersPage = iBankerRepository.findAllBySearch(bankerId, bankId, Boolean.TRUE, pageable);
			break;
		case ADMIN_BANKER:
        	bankersPage = iBankerRepository.findAllBanker(bankerId, user.getId(), Boolean.FALSE, pageable);
			break;
		case BCT_AGENT:
			bankersPage = iBankerRepository.findAllBySearch(bankerId, bankId, Boolean.TRUE, pageable);
			break;
		default:
			throw new UnauthorizedException("403");
		}

		response.setTotalElement(bankersPage.getTotalElements());
		response.setTotalPage(bankersPage.getTotalPages());
		response.setPageSize(bankersPage.getSize());
		response.setPage(bankersPage.getNumber());
		response.setContent(iBankerMapper.toBankerForGetAllDto(bankersPage.getContent()));
		return response;

	}
	/**
	 * Retrieves a page of BankerDto objects based on search criteria, page number,
	 * size, and sorting options.
	 *
	 * @param bankerId The ID of the banker to search for.
	 * @param bankId   The ID of the bank associated with the banker.
	 * @return PaginatedResponse<BankerForGetAllDto> A paginated list of BankerDto
	 *         objects based on the search criteria.
	 */
	@Override
	public PaginatedResponse<BankerForGetAllDto> findAllBySearchExport(String bankerId, String bankId) {
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_BCT_ADMIN_AND_ADMIN_BANKER_BCT_AGENT);

		PaginatedResponse<BankerForGetAllDto> response = new PaginatedResponse<>();
		List <Banker> bankersPage;

		switch (user.getUserType()) {
			case BCT_ADMIN:
				bankersPage = iBankerRepository.findAllBySearchExport(bankerId, bankId, Boolean.TRUE);
				break;
			case ADMIN_BANKER:
				bankersPage = iBankerRepository.findAllBankerExport(bankerId, user.getId(), Boolean.FALSE);
				break;
			case BCT_AGENT:
				bankersPage = iBankerRepository.findAllBySearchExport(bankerId, bankId, Boolean.TRUE);
				break;
			default:
				throw new UnauthorizedException("403");
		}

		///response.setTotalElement(bankersPage.getTotalElements());
		//response.setTotalPage(bankersPage.getTotalPages());
		///response.setPageSize(bankersPage.getSize());
		//response.setPage(bankersPage.getNumber());
		response.setContent(iBankerMapper.toBankerForGetAllDto(bankersPage));
		return response;

	}
	/**
	 * Updates banker information based on the provided ID and DTO, subject to
	 * authorization.
	 *
	 * @param id        The ID of the banker to be updated.
	 * @param bankerDto The DTO containing updated banker information.
	 * @param result    The binding result for validation.
	 * @return BankerDto The DTO of the updated banker.
	 */
	@Transactional
	@Override
	public BankerForGetDto updateBanker(String id, BankerForPutDto bankerDto, BindingResult result) throws MessagingException {
		log.info("start update Banker ...... ");
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		Banker banker = iBankerRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + id));
		BankerValidation.validateBankerForUpdate(bankerDto, result, banker.getIsAdmin());
		checkAuthority(user, banker.getIsAdmin());
		validateEmailExistAndIdNot(bankerDto.getEmail(), id, result);
		setBankToBanker(userId, banker.getIsAdmin(), banker);
		banker.setModifiedBy(user);
		if (Boolean.TRUE.equals(banker.getIsAdmin())) {
			validateBank(banker, bankerDto.getBankId());
			validateIsActiveForUpdate(id, bankerDto.getBankId(), bankerDto.getIsActive(), result);
			banker.setTypeAdministrator(bankerDto.getTypeAdministrator());
			banker.setDirection(bankerDto.getDirection());
			banker.setFunction(bankerDto.getFunction());
		} else {
			banker.setSenioritySector(bankerDto.getSenioritySector());
			banker.setLogin(bankerDto.getEmail());
		}
		banker.setFirstName(bankerDto.getFirstName());
		banker.setLastName(bankerDto.getLastName());
		banker.setUniversityDegree(bankerDto.getUniversityDegree());
		banker.setCellPhone(bankerDto.getCellPhone());
		banker.setHomePhoneNumber(bankerDto.getHomePhoneNumber());
		banker.setInvestmentDirectAndRealEstate(bankerDto.getInvestmentDirectAndRealEstate());
		banker.setInvestmentPortfolio(bankerDto.getInvestmentPortfolio());
		banker.setExternalLoans(bankerDto.getExternalLoans());
		if (Boolean.FALSE.equals(banker.getIsActive()) && Boolean.TRUE.equals(bankerDto.getIsActive())) {

			HashMap<String, String> myHashMap = new HashMap<>();

			GenericNotification genericNotification = GenericNotification.builder()
					.language(banker.getLanguage())
					.label("ACCOUNT_CREATED")
					.emailTo(banker.getLogin())
					.build();

			myHashMap.put(ApplicationConstants.EMAIL,bankerDto.getEmail());
			myHashMap.put(ApplicationConstants.PSWD, banker.getPassword());
			genericNotification.setAttributes(myHashMap);

			emailService.sendEmailSpecificTemplate(genericNotification);
			// TODO password decoding
		}
		banker.setIsActive(bankerDto.getIsActive());
		banker = iBankerRepository.save(iBankerRepository.save(banker));
		if (Boolean.TRUE.equals(banker.getIsAdmin())) {
			updateParentBanker(bankerDto.getBankId(), bankerDto.getIsActive(), banker);
		}
		return iBankerMapper.toBankerForGetDto(banker);
	}

	/**
	 * Retrieves a banker based on the provided ID.
	 *
	 * @param id The ID of the banker to retrieve.
	 * @return BankerDto The DTO of the retrieved banker.
	 * @throws DataNotFoundException If the banker with the provided ID is not
	 *                               found.
	 */
	@Override
	public BankerForGetDto findBankerById(String id) {
		utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BCT_ADMIN_AND_ADMIN_BANKER);
		Banker banker = iBankerRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + id));
		return iBankerMapper.toBankerForGetDto(banker);
	}

	@Override
	public List<BankerLightForGetDto> findByApprovedIntermediaryIdAndIsActiveAndIsAdmin(OperationType operationType) {

		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		if (user.getUserType() != UserTypeEnum.ADMIN_BANKER) {
			throw new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST);
		}

		Banker banker = iBankerRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));

		List<Banker> bankers = null;
		switch (operationType) {

		case COMPANY_CREATION:
		case CAPITAL_INCREASE:
		case ACQUISITION_SHARES:
			bankers = iBankerRepository
					.findByApprovedIntermediaryIdAndIsActiveAndIsAdminAndInvestmentDirectAndRealEstate(
							banker.getApprovedIntermediary().getId(),  Boolean.TRUE);
			break;
		case INVESTING_REAL_ESTATE:
			bankers = iBankerRepository.findByApprovedIntermediaryIdAndIsActiveAndIsAdminAndInvestmentPortfolio(
					banker.getApprovedIntermediary().getId(), Boolean.TRUE);
			break;
		default:
			bankers = new ArrayList<>();
			break;

		}

		return iBankerMapper.toDtoBankerLight(bankers);
	}

	/**
	 * Validate the existence of login
	 *
	 * @param bankerDto BankerBaseDto
	 */
	private void validateEmailExist(BankerForPostDto bankerDto) {
		if (userRepository.existsByLoginIgnoreCase(bankerDto.getEmail())) {
			throw new AlreadyExistException(ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
					ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS);
		}

	}

	/**
	 * Validate the existence of is active and bank id
	 *
	 * @param bankId String
	 * 
	 */
	private void validateIsActive(String bankId, Boolean isActive) {
		if (Boolean.TRUE.equals(isActive)) {
			Optional<Banker> banker = iBankerRepository.findFirstByApprovedIntermediaryIdAndIsActive(bankId,
					Boolean.TRUE);
			if (banker.isPresent()) {
				throw new AlreadyExistException(ApplicationConstants.ERROR_IS_ACTIVE_ALREADY_EXISTS,
						ApplicationConstants.ERROR_IS_ACTIVE_ALREADY_EXISTS);
			}

		}
	}

	/**
	 * Validate the existence of is active and bank id
	 *
	 * @param bankId String
	 * @param errors Errors
	 */
	private void validateIsActiveForUpdate(String id, String bankId, Boolean isActive, Errors errors) {
		if (Boolean.TRUE.equals(isActive)) {
			Optional<Banker> banker = iBankerRepository.findFirstByApprovedIntermediaryIdAndIsActiveAndIdNot(bankId,
					Boolean.TRUE, id);
			if (banker.isPresent()) {
				errors.rejectValue("isActive", ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
						ApplicationConstants.ERROR_IS_ACTIVE_ALREADY_EXISTS);
				throw new BadRequestException("400", errors);
			}

		}
	}

	/**
	 *
	 * @param bankId bank id
	 * @param banker banker
	 */
	private void updateParentBanker(String bankId, Boolean isActive, Banker banker) {
		if (Boolean.TRUE.equals(isActive)) {
			List<Banker> bankers = iBankerRepository.findAllByApprovedIntermediaryIdAndParentIsNotNull(bankId);
			if (!bankers.isEmpty()) {
				for (Banker item : bankers) {
					item.setParent(banker);
				}
				iBankerRepository.saveAll(bankers);
			}
		}
	}

	/**
	 * VAlidate the existence of Bank
	 *
	 * @param banker Banker
	 * @param id     Bank's code
	 */
	private Banker validateBank(Banker banker, String id) {
		Bank bank = bankRepository.findById(id).orElseThrow(
				() -> new DataNotFoundException(ApplicationConstants.BANK_NOT_FOUND, "Bank not found with id " + id));
		banker.setApprovedIntermediary(bank);
		return banker;
	}

	/**
	 * check Authority
	 * 
	 * @param user
	 * @param isAdmin
	 */
	private void checkAuthority(User user, Boolean isAdmin) {
		switch (user.getUserType()) {
		case BCT_ADMIN:
			if (Boolean.FALSE.equals(isAdmin)) {
				throw new UnauthorizedException("403");
			}
			break;
		case ADMIN_BANKER:
			if (Boolean.TRUE.equals(isAdmin)) {
				throw new UnauthorizedException("403");
			}
			break;
		default:
			throw new UnauthorizedException("403");
		}
	}

	/**
	 * validate Email Exist And Id Not
	 * 
	 * @param email  address mail
	 * @param id     User's id
	 * @param errors {@link Errors}
	 */
	private void validateEmailExistAndIdNot(String email, String id, Errors errors) {
		if (userRepository.existsByLoginIgnoreCaseAndAndIdNot(email, id)) {
			errors.rejectValue("email", ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
					ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS);
			throw new AlreadyExistException(ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS, errors);
		}
	}

	/**
	 * set Bank To Banker
	 * 
	 * @param bankerParentId
	 * @param isAdmin
	 * @param banker
	 */
	private void setBankToBanker(String bankerParentId, Boolean isAdmin, Banker banker) {
		if (Boolean.FALSE.equals(isAdmin)) {
			Banker bankerAdmin = iBankerRepository.findById(bankerParentId)
					.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ADMIN_BANKER_NOT_FOUND,
							ApplicationConstants.ADMIN_BANKER_NOT_FOUND_FOR_THIS + bankerParentId));

			banker.setApprovedIntermediary(
					bankerAdmin.getApprovedIntermediary() != null ? bankerAdmin.getApprovedIntermediary() : null);

		}
	}

}
