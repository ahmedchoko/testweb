package com.wevioo.pi.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.DepositType;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.domain.enumeration.StepEnum;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.mapper.AttachmentsMapper;
import com.wevioo.pi.repository.ActionRepository;
import com.wevioo.pi.repository.InvestorRepository;
import com.wevioo.pi.rest.dto.AttachmentLightDto;
import com.wevioo.pi.rest.dto.DirectInvestToStepFive;
import com.wevioo.pi.rest.dto.DocumentConfigDto;
import com.wevioo.pi.rest.dto.SectionConfigDto;
import com.wevioo.pi.rest.dto.request.AffectedToBankerDto;
import com.wevioo.pi.rest.dto.DirectInvestRequestStepFourForPostDto;
import com.wevioo.pi.rest.dto.ValidateDirectInvestByBanker;
import com.wevioo.pi.rest.dto.GenericNotification;
import com.wevioo.pi.service.EmailService;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.config.Section;
import com.wevioo.pi.domain.entity.referential.Agency;
import com.wevioo.pi.domain.entity.referential.AgencyId;
import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.Requester;
import com.wevioo.pi.exception.ConflictException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.mapper.CurrencyInvestmentFinancingMapper;
import com.wevioo.pi.mapper.IRequestMapper;
import com.wevioo.pi.mapper.InvestIdentificationMapper;
import com.wevioo.pi.mapper.ParticipationIdentificationMapper;
import com.wevioo.pi.mapper.RequesterMapper;
import com.wevioo.pi.mapper.BankMapper;
import com.wevioo.pi.mapper.AgencyMapper;
import com.wevioo.pi.repository.AgencyRepository;
import com.wevioo.pi.repository.AttachmentsRepository;
import com.wevioo.pi.repository.BankRepository;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.IBankerRepository;
import com.wevioo.pi.repository.InvestIdentificationRepository;
import com.wevioo.pi.repository.SectionRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.request.DirectInvestRequestStepZeroForPostDto;
import com.wevioo.pi.rest.dto.request.RequestStepSixForPost;
import com.wevioo.pi.rest.dto.response.DirectInvestRequestForGet;
import com.wevioo.pi.rest.dto.response.DirectInvestStepOneForGet;
import com.wevioo.pi.rest.dto.response.RequestStepSixForGet;
import com.wevioo.pi.rest.dto.response.RequesterForGetDto;
import com.wevioo.pi.service.DirectInvestRequestService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.RequesterService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.FileUtils;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.DirectInvestRequesterValidation;

import lombok.extern.slf4j.Slf4j;

/**
 * Direct Invest Request Service Impl
 */
@Service
@Slf4j
public class DirectInvestRequestServiceImpl implements DirectInvestRequestService {

	/**
	 * Injected bean {@link DirectInvestRequestRepository}
	 */

	@Autowired
	private DirectInvestRequestRepository directInvestRequestRepository;

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
	 * Injected bean {@link InvestIdentificationRepository}
	 */

	@Autowired
	private RequesterMapper requesterMapper;

	/**
	 * Inject {@link CommonService} bean
	 */
	@Autowired
	private CommonService commonService;
	/**
	 * Injected bean {@link KeyGenService}
	 */

	@Autowired
	private KeyGenService keyGenService;

	/**
	 * Injected bean {@link RequesterService}
	 */

	@Autowired
	private RequesterService requesterService;

	/**
	 * Inject {@link ObjectMapper} bean
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Injected bean {@link AttachmentsRepository}
	 */
	@Autowired
	private AttachmentsRepository attachmentsRepository;
	/**
	 * Injected bean {@link InvestIdentificationMapper}
	 */
	@Autowired
	InvestIdentificationMapper investIdentificationMapper;
	/**
	 * Injected bean {@link ParticipationIdentificationMapper}
	 */
	@Autowired
	ParticipationIdentificationMapper participationIdentificationMapper;
	/**
	 * Injected bean {@link CurrencyInvestmentFinancingMapper}
	 */
	@Autowired
	CurrencyInvestmentFinancingMapper currencyInvestmentFinancingMapper;

	/**
	 * Injected bean {@link AttachmentsMapper}
	 */
	@Autowired
	AttachmentsMapper attachmentsMapper;

	/**
	 * Injected bean {@link UtilityService}
	 */
	@Autowired
	UtilityService utilityService;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private AgencyRepository agencyRepository;

	/**
	 * Injected bean {@link IRequestMapper}
	 */
	@Autowired
	private IRequestMapper iRequestMapper;

	/**
	 * Injected bean {@link IBankerRepository}
	 */
	@Autowired
	private IBankerRepository iBankerRepository;

	/**
	 * Injected bean {@link FileUtils}
	 */
	@Autowired
	private FileUtils fileUtils;

	/**
	 * Injected bean {@link SectionRepository}
	 */
	@Autowired
	private SectionRepository sectionRepository;

	/**
	 * Injected bean {@link SectionRepository}
	 */
	@Autowired
	private AgencyMapper agencyMapper;
	/**
	 * Injected bean {@link SectionRepository}
	 */
	@Autowired
	private BankMapper bankMapper;

	/**
	 * Injected bean {@link InvestIdentificationRepository}
	 */
	@Autowired
	private InvestorRepository investorRepository;
	/**
	 * Injected bean {@link ActionRepository}
	 */
	@Autowired
	private ActionRepository actionRepository;

	/**
	 * Injected bean {@link EmailService}
	 */
	@Autowired
	private EmailService emailService;

	@Transactional
	@Override
	public DirectInvestStepOneForGet saveDirectInvestStepZero(String directInvestRequestDto, MultipartFile file)
			throws IOException {

		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		// utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
		DirectInvestRequestStepZeroForPostDto directInvestRequestStepZeroForPostDto = objectMapper
				.readValue(directInvestRequestDto, DirectInvestRequestStepZeroForPostDto.class);
		BindingResult bindingResult = new BeanPropertyBindingResult(directInvestRequestStepZeroForPostDto,
				"directInvestRequestStepZeroForPostDto");
		DirectInvestRequesterValidation.validateDirectInvestRequester(directInvestRequestStepZeroForPostDto,
				bindingResult);
		if (directInvestRequestStepZeroForPostDto.getFicheInvestId() == null) {
			DirectInvestRequest directInvestRequest = new DirectInvestRequest();
			validateInvestor(directInvestRequest, directInvestRequestStepZeroForPostDto.getInvestorId(), user,
					bindingResult);
			directInvestRequest.setId(keyGenService.getNextKey(KeyGenType.DIRECT_INVEST_REQUEST_KEY, true, null));
			directInvestRequest.setCreatedBy(user);
			directInvestRequest.setStatus(RequestStatusEnum.DRAFT);
			directInvestRequest.setStep(StepEnum.STEP_ZERO);
			// Assigning the Operation Type in Step 0 to dynamically determine the fields
			// displayed in subsequent steps based on this type
			directInvestRequest.setOperationType(directInvestRequestStepZeroForPostDto.getOperationType());
			if (Boolean.TRUE.equals(directInvestRequestStepZeroForPostDto.getHasRequester())) {
				Requester requester = requesterService.saveNewRequester(
						directInvestRequestStepZeroForPostDto.getRequester(), user, file, bindingResult);
				directInvestRequest.setRequester(requester);
			}

			return requesterMapper.toDto(directInvestRequestRepository.save(directInvestRequest));
		} else {
			DirectInvestRequest directInvestRequest = directInvestRequestRepository
					.findById(directInvestRequestStepZeroForPostDto.getFicheInvestId())
					.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
							"DIRECT INVEST REQUEST NOT FOUND WITH ID :"
									+ directInvestRequestStepZeroForPostDto.getFicheInvestId()));

			validateInvestor(directInvestRequest, directInvestRequestStepZeroForPostDto.getInvestorId(), user,
					bindingResult);
			if (directInvestRequest.getRequester() != null && directInvestRequestStepZeroForPostDto.getHasRequester()) {
				requesterService.updateExistingRequester(directInvestRequestStepZeroForPostDto.getRequester(), user,
						file, bindingResult);
			} else if (directInvestRequest.getRequester() != null
					&& !directInvestRequestStepZeroForPostDto.getHasRequester()) {
				Requester requester = directInvestRequest.getRequester();
				directInvestRequest.setRequester(null);
				List<Attachment> attachments = attachmentsRepository.findAllByRequesterId(requester.getId());
				commonService.deleteAttachmentList(attachments);
				directInvestRequestRepository.save(directInvestRequest);
				requesterService.deleteRequesterById((requester.getId()));
			} else if (directInvestRequest.getRequester() == null
					&& directInvestRequestStepZeroForPostDto.getHasRequester()) {
				Requester requester = requesterService.saveNewRequester(
						directInvestRequestStepZeroForPostDto.getRequester(), user, file, bindingResult);
				directInvestRequest.setRequester(requester);
			}
			return requesterMapper.toDto(directInvestRequest);
		}
	}

	/**
	 * Search by given parameter : ID
	 *
	 * @param id DirectInvestRequest's id
	 * @return {@link RequesterForGetDto}
	 */
	@Override
	public DirectInvestStepOneForGet findById(String id) {
		DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + id));
		DirectInvestStepOneForGet responseStepZero = requesterMapper.toDto(directInvestRequest);
		if (responseStepZero != null && responseStepZero.getRequester() != null) {
			responseStepZero.getRequester().setAttachment(attachmentsMapper
					.toDto(attachmentsRepository.findByRequesterId(responseStepZero.getRequester().getId())));
		}
		return responseStepZero;
	}

	/**
	 * to Step Five
	 * 
	 * @return id
	 */
	@Override
	public DirectInvestToStepFive toStepFive(String id) {

		DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + id));

		DirectInvestToStepFive stepSix = new DirectInvestToStepFive();

		stepSix.setFicheInvestId(id);
		stepSix.setInvestIdentification(
				investIdentificationMapper.toStepFive(directInvestRequest.getInvestIdentification()));
		stepSix.setParticipationIdentification(participationIdentificationMapper.toDto(directInvestRequest));

		if (directInvestRequest.getCurrencyFinancing() != null
				&& directInvestRequest.getCurrencyFinancing().getCurrencyFinancingData() != null) {
			stepSix.setCurrencyFinancingData(currencyInvestmentFinancingMapper.toDtoForStepFiveDirectInvest(
					directInvestRequest.getCurrencyFinancing().getCurrencyFinancingData()));

		}
		/** set agency , bank and depositType */

		stepSix.setAgency(agencyMapper.toDto(directInvestRequest.getAgency()));
		stepSix.setBank(bankMapper.toDto(directInvestRequest.getBank()));
		stepSix.setDeposiType(directInvestRequest.getDepositType());

		/** Add list of section/document/attachments */
		List<SectionConfigDto> sectionDatas = findAttachmentsByRequestId(id, directInvestRequest.getOperationType());
		stepSix.setSectionDataDto(sectionDatas);
		return stepSix;
	}

	/**
	 * @param stepSixForPost
	 * @param result
	 * @return
	 */
	@Override
	@Transactional
	public RequestStepSixForGet saveDirectInvestStepSix(RequestStepSixForPost stepSixForPost, BindingResult result) throws MessagingException {

		log.info("save Direct Invest Step Six ......  ");
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		// utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
		DirectInvestRequesterValidation.validateDirectInvestStepSix(stepSixForPost, result);

		DirectInvestRequest directInvestRequest = directInvestRequestRepository
				.findById(stepSixForPost.getFicheInvestId())
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + stepSixForPost.getFicheInvestId()));
//
//		if (!user.equals(directInvestRequest.getCreatedBy())) {
//			throw new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST,
//					ApplicationConstants.UNAUTHORIZED_MSG);
//
//		}

		validateBeforeSaveStepSix(stepSixForPost.getBankId(), stepSixForPost.getAgencyId(), directInvestRequest, user);

		directInvestRequest.setStatus(RequestStatusEnum.PENDING);
		directInvestRequest.setStep(StepEnum.STEP_SIX);
		directInvestRequest.setExamineAcceptance(stepSixForPost.getExamineAcceptance());
		directInvestRequest.setModificationDate(new Date());
		directInvestRequest.setModifiedBy(user);
		directInvestRequest.setTransmissionDate(new Date());
		directInvestRequest.setLanguage(stepSixForPost.getLanguage());

		DirectInvestRequest savedDirectInvestRequest = directInvestRequestRepository.save(directInvestRequest);

		if (savedDirectInvestRequest.getId() != null) {
			RequestStepSixForGet directInvestRequestForGet = iRequestMapper.toDto(savedDirectInvestRequest);

			//sending mail
			GenericNotification genericNotification = GenericNotification.builder()
					.language(stepSixForPost.getLanguage())
					.label("TRANSMISSION")
					.emailTo(directInvestRequest.getAffectedTo().getLogin())
					.build();

			HashMap<String, String> myHashMap = new HashMap<>();
			myHashMap.put(ApplicationConstants.BANKREFERENCE, directInvestRequestForGet.getFicheInvestId());
			myHashMap.put(ApplicationConstants.DECLARATIONDATE, directInvestRequest.getCreationDate().toString());
			genericNotification.setAttributes(myHashMap);

			emailService.sendEmailSpecificTemplate(genericNotification);

			return directInvestRequestForGet;
		}
			throw new DataNotFoundException("Failed to save DirectInvestStepSix", "Failed to save DirectInvestStepSix");
}


	/**
	 * find Step Six By Id
	 * 
	 * @param id fiche id
	 * @return DirectInvestRequestForGet
	 */
	@Override
	public RequestStepSixForGet findStepSixById(String id) {

		utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
		DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + id));

		return iRequestMapper.toDto(directInvestRequest);
	}

	void validateBeforeSaveStepSix(String bankId, String agencyId, DirectInvestRequest directInvestRequest, User user) {
		log.info("validate Before Save Step Six ....");
		Bank bank = bankRepository.findById(bankId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.BANK_NOT_FOUND,
						ApplicationConstants.NO_BANK_FOUNDED_WITH_ID + bankId));

		AgencyId agencyEmbeddedId = new AgencyId();
		agencyEmbeddedId.setAgencyId(agencyId);
		agencyEmbeddedId.setBankId(bankId);

		Agency agency = agencyRepository.findById(agencyEmbeddedId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.AGENCY_NOT_FOUND,
						ApplicationConstants.AGENCY_NOT_FOUND + ":" + agencyId));

		switch (user.getUserType()) {
		case BANKER:
			directInvestRequest.setAffectedTo((Banker) user);
			break;
		case INVESTOR:
			Banker adminBanker = iBankerRepository.findFirstByApprovedIntermediaryIdAndIsAdmin(bankId, Boolean.TRUE)
					.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ADMIN_BANKER_NOT_FOUND,
							"Admin banker not found with bank id:" + bankId));
			directInvestRequest.setAffectedTo(adminBanker);
			break;
		default:
			throw new UnauthorizedException("403");

		}

		if (directInvestRequest.getDepositType() == null) {
			log.error("deposit type is null");
			throw new ConflictException(ApplicationConstants.DEPOSIT_TYPE_CAN_NOT_BE_NULL_AT_THIS_STEP,
					"At this step we cannot have a null DEPOSIT_TYPE");
		}

		if (directInvestRequest.getDepositType() == DepositType.DIRECT_DEPOSIT) {
			if (directInvestRequest.getBank() != null && !bank.equals(directInvestRequest.getBank())) {
				throw new ConflictException(ApplicationConstants.ERROR_CONFLICT_RELATE_BANK,
						"the bank linked to the request is different from input:  " + bank.getLabel());
			}
			if (directInvestRequest.getAgency() != null && !agency.equals(directInvestRequest.getAgency())) {
				throw new ConflictException(ApplicationConstants.ERROR_CONFLICT_RELATE_AGENCY,
						"the Agency linked to the request is different from input " + agency.getLabel());
			}
		}

		directInvestRequest.setAgency(agency);
		directInvestRequest.setBank(bank);
	}

	@Transactional
	@Override
	public DirectInvestRequestForGet saveDirectInvestStepFour(
			DirectInvestRequestStepFourForPostDto directInvestRequestStepFourForPostDto, BindingResult result) {

		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);

		DirectInvestRequesterValidation.validateDirectInvestFourStep(directInvestRequestStepFourForPostDto, result);
		DirectInvestRequest directInvestRequest = directInvestRequestRepository
				.findById(directInvestRequestStepFourForPostDto.getFicheInvestId())
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :"
								+ directInvestRequestStepFourForPostDto.getFicheInvestId()));

		if (directInvestRequestStepFourForPostDto.getDepositType() == DepositType.DIRECT_DEPOSIT) {
			Bank bank = bankRepository.findById(directInvestRequestStepFourForPostDto.getBank())
					.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.BANK_NOT_FOUND,
							ApplicationConstants.NO_BANK_FOUNDED_WITH_ID
									+ directInvestRequestStepFourForPostDto.getBank()));

			AgencyId agencyEmbeddedId = AgencyId.builder().agencyId(directInvestRequestStepFourForPostDto.getAgency())
					.bankId(directInvestRequestStepFourForPostDto.getBank()).build();

			Agency agency = agencyRepository.findById(agencyEmbeddedId).orElseThrow(() -> new DataNotFoundException(
					ApplicationConstants.AGENCY_NOT_FOUND,
					ApplicationConstants.AGENCY_NOT_FOUND + ":" + directInvestRequestStepFourForPostDto.getAgency()));

			directInvestRequest.setAgency(agency);
			directInvestRequest.setBank(bank);
			directInvestRequest.setStep(StepEnum.STEP_FOUR);
			/** delete all attachment **/
			List<Attachment> attachments = attachmentsRepository
					.findByIdDeclarationNature(directInvestRequestStepFourForPostDto.getFicheInvestId());
			deleteExistantAttchment(attachments);
		} else {
			directInvestRequest.setAgency(null);
			directInvestRequest.setBank(null);
		}

		directInvestRequest.setDepositType(directInvestRequestStepFourForPostDto.getDepositType());
		// TODO add step to fix notification step

		return iRequestMapper.toDtoForGet(directInvestRequestRepository.save(directInvestRequest));

	}

	/**
	 * Delete existent documents
	 * 
	 * @param attachments List<Attachment>
	 */
	private void deleteExistantAttchment(List<Attachment> attachments) {

		if (!ObjectUtils.isEmpty(attachments)) {
			attachments.forEach(attachment -> {
				try {
					fileUtils.deleteFile(attachment.getFileUrl() + File.separator + attachment.getFileName());
				} catch (Exception e) {
					log.error("An exception has bean thrown: ", e);
				}
			});
			attachmentsRepository.deleteAll(attachments);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DirectInvestRequestForGet findStepFourById(String id) {
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
		DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + id));

		return iRequestMapper.toDtoForGet(directInvestRequest);
	}

	/**
	 * @param validateDirectInvestByBanker
	 * @param result
	 * @return ValidateDirectInvestByBanker
	 */
	@Override
	@Transactional
	public ValidateDirectInvestByBanker validateDirectInvestByBanker(
			ValidateDirectInvestByBanker validateDirectInvestByBanker, BindingResult result) {

		log.info("Validate Direct Invest By Banker ......  ");

		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));

		if (!(user instanceof Banker)) {
			throw new UnauthorizedException("403");
		}

		DirectInvestRequesterValidation.validateDirectInvestByBanker(validateDirectInvestByBanker, result);

		DirectInvestRequest directInvestRequest = directInvestRequestRepository
				.findById(validateDirectInvestByBanker.getFicheInvestId())
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + validateDirectInvestByBanker.getFicheInvestId()));

		directInvestRequest.setStatus(RequestStatusEnum.VALIDATED);
		directInvestRequest.setModificationDate(new Date());
		directInvestRequest.setAffectedTo((Banker) user);
		directInvestRequest.setLanguage(validateDirectInvestByBanker.getLanguage());
		return iRequestMapper.toValidateDirectInvestByBanker(directInvestRequest);
	}


	/**
	 * Load section/document/attachment related to request ID
	 * 
	 * @param idForm Request's id
	 * @return List<SectionConfigDto>
	 */
	private List<SectionConfigDto> findAttachmentsByRequestId(String idForm, OperationType operationType) {

		List<Attachment> attachments = null;
		if (!ObjectUtils.isEmpty(idForm)) {
			attachments = attachmentsRepository.findByIdDeclarationNature(idForm);
		}
		Map<String, List<AttachmentLightDto>> mapResult = getMapAttchment(attachments);
		// TODO modify : get type from request
		List<Section> sectionList = sectionRepository.findByDeclarationNatureAndInvestmentTypeAndOperationType(
				DeclarationNature.INVESTMENT_FORM, InvestmentType.DIRECT_INVESTMENT, operationType);

		List<SectionConfigDto> sectionConfigDtoList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(sectionList))
			sectionList.forEach(section -> {
				List<DocumentConfigDto> documentConfigList = new ArrayList<>();
				if (!ObjectUtils.isEmpty(section.getSectionDocument())) {
					section.getSectionDocument().forEach(doc -> {
						if (mapResult.containsKey(doc.getSection().getId() + "_" + doc.getDocument().getId())) {
							DocumentConfigDto docDto = DocumentConfigDto.builder().id(doc.getDocument().getId())
									.label(doc.getDocument().getLabel()).description(doc.getDocument().getDescription())
									.isMandatory(doc.getIsMandatory()).requestedNumber(doc.getRequestedNumber())
									.build();

							docDto.setAttachments(
									mapResult.get(doc.getSection().getId() + "_" + doc.getDocument().getId()));
							documentConfigList.add(docDto);
						}

					});
				}
				if (!ObjectUtils.isEmpty(documentConfigList)) {
					SectionConfigDto sectionDto = SectionConfigDto.builder().id(section.getId())
							.label(section.getLabel()).documentConfigList(documentConfigList).build();
					sectionConfigDtoList.add(sectionDto);
				}
			});

		return sectionConfigDtoList;
	}

	/**
	 * Group attachment by section and document
	 * 
	 * @param attachments List<Attachment>
	 * @return Map<String, List<AttachmentLightDto>>
	 */
	private Map<String, List<AttachmentLightDto>> getMapAttchment(List<Attachment> attachments) {

		Map<String, List<AttachmentLightDto>> mapResult = new HashMap<>();
		if (!ObjectUtils.isEmpty(attachments)) {
			attachments.forEach(attachment -> {

				List<AttachmentLightDto> attachmentLightDtoList = new ArrayList<>();
				if (mapResult.containsKey(attachment.getSection().getId() + "_" + attachment.getDocument().getId())) {
					attachmentLightDtoList = mapResult
							.get(attachment.getSection().getId() + "_" + attachment.getDocument().getId());
					AttachmentLightDto attachmentDto = AttachmentLightDto.builder().attachmentId(attachment.getId())
							.fileName(attachment.getFileName()).build();
					attachmentLightDtoList.add(attachmentDto);
				} else {
					AttachmentLightDto attachmentDto = AttachmentLightDto.builder().attachmentId(attachment.getId())
							.fileName(attachment.getFileName()).build();
					attachmentLightDtoList.add(attachmentDto);
				}

				mapResult.put(attachment.getSection().getId() + "_" + attachment.getDocument().getId(),
						attachmentLightDtoList);

			});

		}
		return mapResult;
	}

	/**
	 * validate and set Investor
	 * 
	 * @param directInvestRequest directInvestRequest
	 * @param investorId          id of investor
	 */
	void validateInvestor(DirectInvestRequest directInvestRequest, String investorId, User user, Errors errors) {
		log.info("check investor with id :" + investorId);
		switch (user.getUserType()) {
		case INVESTOR:
			directInvestRequest.setInvistor((Investor) user);
			break;
		case BANKER:
			if (CommonUtilities.isNullOrEmpty(investorId)) {
				errors.rejectValue("investorId", ApplicationConstants.ERROR_MISSING_REQUIRED_DATA,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
				log.error("investor not found with id null ....");
				throw new BadRequestException("400", errors);
			}

			Investor investor = investorRepository.findById(investorId)
					.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND,
							"INVESTOR NOT FOUND WITH ID : " + investorId));

			Banker banker = iBankerRepository.findById(user.getId())
					.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.BANKER_NOT_FOUND,
							ApplicationConstants.BANKER_NOT_FOUND + ":" + user.getId()));

			directInvestRequest.setInvistor(investor);
			directInvestRequest.setAffectedTo(banker);
			directInvestRequest.setBank(banker.getApprovedIntermediary());
			break;
		default:
			throw new UnauthorizedException("403");
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public void deleteDirectInvest(String id) {

		DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + id));
		if (directInvestRequest.getRequester() != null) {
			List<Attachment> attachments = attachmentsRepository
					.findAllByRequesterId(directInvestRequest.getRequester().getId());
			try {
				commonService.deleteAttachmentList(attachments);
			} catch (IOException e) {
				log.error("An ex ception has been thrown ", e);
			}
			requesterService.deleteRequesterById(directInvestRequest.getRequester().getId());
		}
		List<Attachment> attachments = attachmentsRepository.findByIdDeclarationNature(id);
		try {
			commonService.deleteAttachmentList(attachments);
		} catch (IOException e) {
			log.error("An ex ception has been thrown ", e);
		}
		attachmentsRepository.deleteAll(attachments);
		actionRepository.deleteAllByFicheId(id);
		directInvestRequestRepository.deleteById(id);

	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public void complete(OperationType type, String idFrom, RequestStatusEnum requestStatus) {
		DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(idFrom)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + idFrom));

		switch (requestStatus) {

		case FORCED_CLOSED:
			if (directInvestRequest.getStatus() == RequestStatusEnum.DRAFT) {
				directInvestRequest.setStatus(RequestStatusEnum.FORCED_CLOSED);
				directInvestRequest.setModificationDate(new Date());
				directInvestRequestRepository.save(directInvestRequest);
			} else {
				throw new ConflictException(ApplicationConstants.ERROR_FORM_STATUS,
						ApplicationConstants.ERROR_FORM_STATUS);
			}
			break;
		case IN_PROGRESS:

			if (directInvestRequest.getStatus() == RequestStatusEnum.WAITING_TO_BE_TAKEN_ON) {
				directInvestRequest.setStatus(RequestStatusEnum.IN_PROGRESS);
				directInvestRequest.setModificationDate(new Date());
				directInvestRequestRepository.save(directInvestRequest);
			} else {
				throw new ConflictException(ApplicationConstants.ERROR_FORM_STATUS,
						ApplicationConstants.ERROR_FORM_STATUS);
			}
			break;

		default:
			throw new ConflictException(ApplicationConstants.ERROR_FORM_STATUS,
					ApplicationConstants.ERROR_FORM_STATUS);
		}

	}
}
