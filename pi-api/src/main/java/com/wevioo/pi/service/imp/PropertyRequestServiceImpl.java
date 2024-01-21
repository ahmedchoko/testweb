package com.wevioo.pi.service.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Agency;
import com.wevioo.pi.domain.entity.referential.AgencyId;
import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.entity.referential.Delegation;
import com.wevioo.pi.domain.entity.referential.DelegationID;
import com.wevioo.pi.domain.entity.referential.Governorate;
import com.wevioo.pi.domain.entity.referential.Location;
import com.wevioo.pi.domain.entity.referential.LocationID;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.domain.entity.referential.ZipCode;
import com.wevioo.pi.domain.entity.referential.ZipCodeID;
import com.wevioo.pi.domain.entity.request.PropertyDescription;
import com.wevioo.pi.domain.entity.request.PropertyRequest;
import com.wevioo.pi.domain.entity.request.Requester;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.domain.enumeration.DepositType;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.domain.enumeration.StepEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.ConflictException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.mapper.AttachmentsMapper;
import com.wevioo.pi.mapper.AuthorisationRequiredMapper;
import com.wevioo.pi.mapper.CurrencyInvestmentFinancingMapper;
import com.wevioo.pi.mapper.IRequestMapper;
import com.wevioo.pi.mapper.MainContractualTermsMapper;
import com.wevioo.pi.mapper.PropertyDescriptionMapper;
import com.wevioo.pi.mapper.PropertyRequestMapper;
import com.wevioo.pi.repository.AgencyRepository;
import com.wevioo.pi.repository.AttachmentsRepository;
import com.wevioo.pi.repository.BankRepository;
import com.wevioo.pi.repository.DelegationRepository;
import com.wevioo.pi.repository.GovernorateRepository;
import com.wevioo.pi.repository.IBankerRepository;
import com.wevioo.pi.repository.InvestorRepository;
import com.wevioo.pi.repository.LocationRepository;
import com.wevioo.pi.repository.PropertyRequestRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.repository.ZipCodeRepository;
import com.wevioo.pi.rest.dto.DelegationDto;
import com.wevioo.pi.rest.dto.GenericNotification;
import com.wevioo.pi.rest.dto.LocationDto;
import com.wevioo.pi.rest.dto.ZipCodeDto;
import com.wevioo.pi.rest.dto.request.PropertyDescriptionForGetDto;
import com.wevioo.pi.rest.dto.request.PropertyDescriptionForPostDto;
import com.wevioo.pi.rest.dto.request.PropertyDescriptionToStepFiveDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepOneForGetDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepOneForPostDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepZeroForGetDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepZeroForPostDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestToSummaryDto;
import com.wevioo.pi.rest.dto.request.RequestStepSixForPost;
import com.wevioo.pi.rest.dto.response.RequestStepSixForGet;
import com.wevioo.pi.service.EmailService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ParameterService;
import com.wevioo.pi.service.PropertyRequestService;
import com.wevioo.pi.service.ReferentialService;
import com.wevioo.pi.service.RequesterService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.CommonUtilities;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.DirectInvestRequesterValidation;
import com.wevioo.pi.validation.PropertyDescriptionValidator;
import com.wevioo.pi.validation.PropertyRequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PropertyRequestServiceImpl implements PropertyRequestService {

    /**
     * Inject {@link PropertyDescriptionMapper} mapper.
     */
    @Autowired
    private PropertyDescriptionMapper propertyDescriptionMapper;

    /**
     * Inject {@link MainContractualTermsMapper} mapper.
     */
    @Autowired
    private MainContractualTermsMapper mainContractualTermsMapper;

    /**
     * Inject {@link CurrencyInvestmentFinancingMapper} mapper.
     */
    @Autowired
    private CurrencyInvestmentFinancingMapper currencyInvestmentFinancingMapper;

    /**
     * Inject {@link ReferentialService} service.
     */
    @Autowired
    private ReferentialService referentialService;

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
     * Injected bean {@link KeyGenService}
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     * Injected bean {@link PropertyRequestRepository}
     */
    @Autowired
    private PropertyRequestRepository propertyRequestRepository;

    /**
     * Injected bean {@link InvestorRepository}
     */
    @Autowired
    private InvestorRepository investorRepository;

    /**
     * Injected bean {@link IRequestMapper}
     */
    @Autowired
    IRequestMapper iRequestMapper;


    /**
     * Injected bean {@link GovernorateRepository}
     */
    @Autowired
    private GovernorateRepository governorateRepository;

    /**
     * Injected bean {@link DelegationRepository}
     */
    @Autowired
    private DelegationRepository delegationRepository;

    /**
     * Injected bean {@link LocationRepository}
     */
    @Autowired
    private LocationRepository locationRepository;

    /**
     * Injected bean {@link ZipCodeRepository}
     */
    @Autowired
    private ZipCodeRepository zipCodeRepository;


    /**
     * Injected bean {@link UtilityService}
     */
    @Autowired
    private UtilityService utilityService;

    /**
     * Injected bean {@link RequesterService}
     */
    @Autowired
    private RequesterService requesterService;

    /**
     * Injected bean {@link IBankerRepository}
     */
    @Autowired
    IBankerRepository iBankerRepository;

    /**
     * Injected bean {@link BankRepository}
     */
    @Autowired
    private BankRepository bankRepository;

    /**
     * Injected bean {@link AgencyRepository}
     */
    @Autowired
    private AgencyRepository agencyRepository;

    /**
     * Injected bean {@link AuthorisationRequiredMapper}
     */
    @Autowired
    private AuthorisationRequiredMapper authorisationRequiredMapper;


    /**
     * Inject {@link ObjectMapper} bean
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Inject {@link ObjectMapper} bean
     */
    @Autowired
    private PropertyRequestMapper propertyRequestMapper;

    /**
     * Injected bean {@link AttachmentsMapper}
     */
    @Autowired
    AttachmentsMapper attachmentsMapper;

    /**
     * Injected bean {@link AttachmentsRepository}
     */
    @Autowired
    private AttachmentsRepository attachmentsRepository;

    /**
     * Injected bean {@link EmailService}
     */
    @Autowired
    private EmailService emailService;

    /**
     * Inject {@link ParameterService} bean
     */
    @Autowired
    private ParameterService parameterService;


    @Transactional
    @Override
    public PropertyDescriptionForGetDto savePropertyRequestStepOne(PropertyRequestStepOneForPostDto propertyRequestForPostDto, BindingResult result) {

        log.info("save property request step one  ......");

        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);

        PropertyDescriptionForPostDto propertyDescriptionForPostDto = propertyRequestForPostDto.getPropertyDescription();
        PropertyRequestValidator.validatePost(propertyRequestForPostDto, result);
        Parameter investTypeAcquisitionTerrain = parameterService.getByKey(ApplicationConstants.INVEST_TYPE_ACQUISITION_TERRAIN);
        String investTypeAcquisitionTerrainValue = investTypeAcquisitionTerrain != null ? investTypeAcquisitionTerrain.getParameterValue() : null;
        boolean isBanker = user.getUserType() == UserTypeEnum.BANKER;
        PropertyDescriptionValidator.validatePost(propertyDescriptionForPostDto, result,
                investTypeAcquisitionTerrainValue, isBanker);

        PropertyRequest propertyRequest = propertyRequestRepository.findById(propertyRequestForPostDto.getFicheInvestId()).orElseThrow(
                () -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND, "PROPERTY REQUEST NOT FOUND WITH ID :"
                        + propertyRequestForPostDto.getFicheInvestId()));
        utilityService.isAuthorizedForRequest(user, propertyRequest.getCreatedBy(),
                propertyRequest.getBanker(), propertyRequest.getStatus());

        if (propertyRequest.getPropertyDescription() == null) {

            PropertyDescription propertyDescription = builPropertyDescription(propertyDescriptionForPostDto);
            propertyDescription.setId(keyGenService.getNextKey(KeyGenType.PROPERTY_DESCRIPTION_KEY, true, null));
            propertyDescription.setCreatedBy(user);
            propertyDescription.setCreationDate(new Date());
            propertyRequest.setPropertyDescription(propertyDescription);
            propertyRequest.setStep(StepEnum.STEP_ONE);
        } else {

            PropertyDescription propertyDescription = builPropertyDescription(propertyDescriptionForPostDto);
            propertyDescription.setId(propertyRequest.getPropertyDescription().getId());
            propertyDescription.setCreatedBy(propertyRequest.getPropertyDescription().getCreatedBy());
            propertyDescription.setCreationDate(propertyRequest.getPropertyDescription().getCreationDate());
            propertyDescription.setModifiedBy(user);
            propertyDescription.setModificationDate(new Date());
            propertyRequest.setPropertyDescription(propertyDescription);

        }


        propertyRequest = propertyRequestRepository.save(propertyRequest);
        PropertyDescriptionForGetDto response = propertyDescriptionMapper.toDto(propertyRequest.getPropertyDescription());
        response.setFicheInvestId(propertyRequestForPostDto.getFicheInvestId());
        return response;
    }

    @Transactional
    @Override
    public PropertyRequestStepZeroForGetDto savePropertyRequestStepZero(String propertyRequestForPostDto,
                                                                        MultipartFile file) throws IOException {
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
        PropertyRequestStepZeroForPostDto propertyRequestStepZeroForPostDto = objectMapper
                .readValue(propertyRequestForPostDto, PropertyRequestStepZeroForPostDto.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(propertyRequestStepZeroForPostDto,
                "propertyRequestStepZeroForPostDto");
        boolean isBanker = user.getUserType() == UserTypeEnum.BANKER;
        PropertyRequestValidator.validatePropertyRequestRequester(propertyRequestStepZeroForPostDto, bindingResult, isBanker);

        PropertyRequest propertyRequest = new PropertyRequest();
        if (propertyRequestStepZeroForPostDto.getFicheInvestId() == null) {
            validateInvestor(propertyRequest, propertyRequestStepZeroForPostDto.getInvestorId(), user);
            propertyRequest.setId(keyGenService.getNextKey(KeyGenType.PROPERTY_REQUEST_KEY, true, null));
            propertyRequest.setCreatedBy(user);
            propertyRequest.setStatus(RequestStatusEnum.DRAFT);
            propertyRequest.setStep(StepEnum.STEP_ZERO);
            if (Boolean.TRUE.equals(propertyRequestStepZeroForPostDto.getHasRequester())) {
                Requester requester = requesterService.saveNewRequester(propertyRequestStepZeroForPostDto.getRequester(), user, file, bindingResult);
                propertyRequest.setRequester(requester);
            }
        } else {
            propertyRequest = propertyRequestRepository.findById(propertyRequestStepZeroForPostDto.getFicheInvestId()).orElseThrow(
                    () -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND, "PROPERTY REQUEST NOT FOUND WITH ID :"
                            + propertyRequestStepZeroForPostDto.getFicheInvestId()));
            if (!user.equals(propertyRequest.getCreatedBy())) {
                throw  new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST,  ApplicationConstants.UNAUTHORIZED_MSG );
            }
            validateInvestor(propertyRequest, propertyRequestStepZeroForPostDto.getInvestorId(), user);
            if (propertyRequest.getRequester() != null && propertyRequestStepZeroForPostDto.getHasRequester()) {
                requesterService.updateExistingRequester(
                        propertyRequestStepZeroForPostDto.getRequester(),
                        user,
                        file,
                        bindingResult
                );
            } else if (propertyRequest.getRequester() != null && !propertyRequestStepZeroForPostDto.getHasRequester()) {
                Requester requester = propertyRequest.getRequester();
                propertyRequest.setRequester(null);
                propertyRequest   = propertyRequestRepository.save(propertyRequest);
                requesterService.deleteRequesterById(requester.getId());
                return propertyRequestMapper.toDto(
                        propertyRequest
                );
            } else if (propertyRequest.getRequester() == null && propertyRequestStepZeroForPostDto.getHasRequester()) {
                Requester requester = requesterService.saveNewRequester(
                        propertyRequestStepZeroForPostDto.getRequester(),
                        user,
                        file,
                        bindingResult
                );
                propertyRequest.setRequester(requester);
            }
        }
        propertyRequest = propertyRequestRepository.save(propertyRequest);
        return propertyRequestMapper.toDto(propertyRequest);

    }

    private PropertyDescription builPropertyDescription(PropertyDescriptionForPostDto propertyDescriptionForPostDto) {


        // extract ref id
        List<SpecificReferential> specificReferentials = referentialService.findSpecificReferentialByIdIn(
                Arrays.asList(propertyDescriptionForPostDto.getInvestTypeId(), propertyDescriptionForPostDto.getLocationId(),
                        propertyDescriptionForPostDto.getVocationId(), propertyDescriptionForPostDto.getUsageId()));


        validateSpecificReferential(propertyDescriptionForPostDto.getInvestTypeId(), propertyDescriptionForPostDto.getVocationId(),
                propertyDescriptionForPostDto.getLocationId(), propertyDescriptionForPostDto.getUsageId(), specificReferentials);

        validateInvestmentAddress(propertyDescriptionForPostDto.getGovernorateId(), propertyDescriptionForPostDto.getDelegationId(),
                propertyDescriptionForPostDto.getLocalityId(), propertyDescriptionForPostDto.getZipCodeId());

        PropertyDescription propertyDescription = propertyDescriptionMapper.toEntity(propertyDescriptionForPostDto);

        propertyDescription.setInvestType(getSpecificReferentialByIdFromList(specificReferentials, propertyDescriptionForPostDto.getInvestTypeId()));
        if (propertyDescription.getVocation() != null) {
            propertyDescription.setVocation(getSpecificReferentialByIdFromList(specificReferentials, propertyDescriptionForPostDto.getVocationId()));
        }
        propertyDescription.setLocation(getSpecificReferentialByIdFromList(specificReferentials, propertyDescriptionForPostDto.getLocationId()));
        propertyDescription.setUsage(getSpecificReferentialByIdFromList(specificReferentials, propertyDescriptionForPostDto.getUsageId()));
        Optional<Governorate> governorateOptional = governorateRepository.findById(propertyDescriptionForPostDto.getGovernorateId());
        governorateOptional.ifPresent(propertyDescription::setGovernorate);

        propertyDescription.setGovernorate(
                governorateRepository.findById(propertyDescriptionForPostDto.getGovernorateId()).orElseThrow(
                        () -> new DataNotFoundException(ApplicationConstants.GOVERNORATE_NOT_FOUND,
                                "GOVERNORATE NOT FOUND WITH ID :" + propertyDescriptionForPostDto.getGovernorateId())
                )
        );



        if (!ObjectUtils.isEmpty(propertyDescriptionForPostDto.getDelegationId())) {
            //create DelegationId
            DelegationID delegationID = DelegationID.builder()
                    .governorateId(propertyDescriptionForPostDto.getGovernorateId())
                    .delegationId(propertyDescriptionForPostDto.getDelegationId())
                    .build();

            delegationRepository.findById(delegationID).orElseThrow(
                    () -> new DataNotFoundException(ApplicationConstants.DELEGATION_NOT_FOUND,
                            "DELEGATION NOT FOUND  WITH ID : " + delegationID)
            );
            propertyDescription.setDelegation(propertyDescriptionForPostDto.getGovernorateId() + "_" + propertyDescriptionForPostDto.getDelegationId());

            if (!ObjectUtils.isEmpty(propertyDescriptionForPostDto.getLocalityId())) {

                //create LocationId
                LocationID locationID = LocationID.builder()
                        .governorateId(propertyDescriptionForPostDto.getGovernorateId())
                        .delegationId(propertyDescriptionForPostDto.getDelegationId())
                        .locaId(propertyDescriptionForPostDto.getLocalityId())
                        .build();

                locationRepository.findById(locationID).orElseThrow(
                        () -> new DataNotFoundException(ApplicationConstants.LOCATION_NOT_FOUND, "LOCATION NOT FOUND WITH ID :"
                                + propertyDescriptionForPostDto.getLocalityId()));

                propertyDescription.setLocality(propertyDescriptionForPostDto.getGovernorateId() + "_"
                        + propertyDescriptionForPostDto.getDelegationId() + "_" + propertyDescriptionForPostDto.getLocalityId());

                if (!ObjectUtils.isEmpty(propertyDescriptionForPostDto.getZipCodeId())) {
                    //create zipCodeID
                    ZipCodeID zipCodeID = ZipCodeID.builder()
                            .governorateId(propertyDescriptionForPostDto.getGovernorateId())
                            .delegationId(propertyDescriptionForPostDto.getDelegationId())
                            .locaId(propertyDescriptionForPostDto.getLocalityId())
                            .zipCodeId(propertyDescriptionForPostDto.getZipCodeId())
                            .build();

                    zipCodeRepository.findById(zipCodeID).orElseThrow(
                            () -> new DataNotFoundException(ApplicationConstants.ZIP_CODE_NOT_FOUND,
                                    "ZIP CODE NOT FOUND WITH ID" + zipCodeID));

                    propertyDescription.setZipCode(propertyDescriptionForPostDto.getGovernorateId() + "_"
                            + propertyDescriptionForPostDto.getDelegationId() + "_" + propertyDescriptionForPostDto.getLocalityId() +
                            "_" + propertyDescriptionForPostDto.getZipCodeId());
                }
            }
        }

        return propertyDescription;
    }


    @Override
    public PropertyRequestStepOneForGetDto getPropertyRequestStepOneInformation(String id) {
        Optional<PropertyRequest> propertyRequestOptional = propertyRequestRepository.findById(id);
        PropertyRequestStepOneForGetDto propertyRequestForGetDto = new PropertyRequestStepOneForGetDto();
        if (propertyRequestOptional.isPresent()) {
            PropertyRequest propertyRequest = propertyRequestOptional.get();
            PropertyDescription propertyDescription = propertyRequest.getPropertyDescription();
            propertyRequestForGetDto.setId(id);
            if (propertyDescription != null) {
                PropertyDescriptionForGetDto propertyDescriptionForGetDto = propertyDescriptionMapper
                        .toDto(propertyDescription);
                if (propertyDescription.getDelegation() != null) {
                    DelegationID delegationID = DelegationID.builder()
                            .governorateId(propertyDescription.getGovernorate().getId())
                            .delegationId(propertyDescription.getDelegation().split("_")[1])
                            .build();

                    Delegation delegation = delegationRepository.findById(delegationID).orElseThrow(
                            () -> new DataNotFoundException(ApplicationConstants.DELEGATION_NOT_FOUND,
                                    "DELEGATION NOT FOUND  WITH ID : " + delegationID.getDelegationId())
                    );
                    propertyDescriptionForGetDto.setDelegation(new DelegationDto(delegation.getId().getDelegationId(),
                            delegation.getLabel()));
                    if (propertyDescription.getLocality() != null) {
                        LocationID locationID = LocationID.builder()
                                .governorateId(propertyDescription.getGovernorate().getId())
                                .delegationId(propertyDescription.getDelegation().split("_")[1])
                                .locaId(propertyDescription.getLocality().split("_")[2])
                                .build();

                        Location location = locationRepository.findById(locationID).orElseThrow(
                                () -> new DataNotFoundException(ApplicationConstants.LOCATION_NOT_FOUND, "LOCATION NOT FOUND WITH ID :"
                                        + locationID.getLocaId()));
                        propertyDescriptionForGetDto.setLocality(new LocationDto(location.getId().getLocaId(), location.getLabel()));
                        if (propertyDescription.getZipCode() != null) {
                            ZipCodeID zipCodeID = ZipCodeID.builder()
                                    .governorateId(propertyDescription.getGovernorate().getId())
                                    .delegationId(propertyDescription.getDelegation().split("_")[1])
                                    .locaId(propertyDescription.getLocality().split("_")[2])
                                    .zipCodeId(propertyDescription.getZipCode().split("_")[3])
                                    .build();

                            ZipCode zipCode = zipCodeRepository.findById(zipCodeID).orElseThrow(
                                    () -> new DataNotFoundException(ApplicationConstants.ZIP_CODE_NOT_FOUND,
                                            "ZIP CODE NOT FOUND WITH ID" + zipCodeID.getZipCodeId()));
                            propertyDescriptionForGetDto.setZipCode(new ZipCodeDto(zipCode.getId().getZipCodeId(), zipCode.getLabel()));
                        }
                    }
                }
                propertyRequestForGetDto.setPropertyDescription(propertyDescriptionForGetDto);
            }
            return propertyRequestForGetDto;
        } else {
            throw new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND,
                    ApplicationConstants.NO_PROPERTY_REQUEST_FOUNDED_WITH_ID + id);
        }
    }

    @Override
    public PropertyRequestStepZeroForGetDto getPropertyRequestStepZeroInformation(String id) {

        log.info(" Property Request  , find  by id step zero .....");
        PropertyRequest propertyRequest = propertyRequestRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND,
                        ApplicationConstants.NO_PROPERTY_REQUEST_FOUNDED_WITH_ID + id)
        );
        PropertyRequestStepZeroForGetDto  responseStepZero = propertyRequestMapper.toDto(propertyRequest);
        if(responseStepZero != null && responseStepZero.getRequester() != null) {
            responseStepZero.getRequester().setAttachment(
                    attachmentsMapper.toDto(
                            attachmentsRepository.findByRequesterId( responseStepZero.getRequester().getId())
                    ));
        }

        return  responseStepZero;
    }

    @Override
    public PropertyRequestToSummaryDto toStepSix(String id) {

        PropertyRequest propertyRequest = propertyRequestRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND,
                        ApplicationConstants.NO_PROPERTY_REQUEST_FOUNDED_WITH_ID + id));

        PropertyRequestToSummaryDto stepFive = new PropertyRequestToSummaryDto();

        stepFive.setFicheInvestId(id);
        PropertyDescription propertyDescription = propertyRequest.getPropertyDescription();
        PropertyDescriptionToStepFiveDto propertyDescriptionDto = propertyDescriptionMapper.toDtoSummary(propertyRequest.getPropertyDescription());
        if (propertyDescription.getDelegation() != null) {
            Optional<Delegation> delegation = delegationRepository.findById(
                    new DelegationID(propertyDescription.getGovernorate().getId(), propertyDescription.getDelegation().split("_")[1]));
            delegation.ifPresent(value -> propertyDescriptionDto.setDelegation(value.getLabel()));
        }
        if (propertyDescription.getLocality() != null) {
            Optional<Location> locality = locationRepository.findById(
                    new LocationID(propertyDescription.getGovernorate().getId(), propertyDescription.getLocality().split("_")[1],
                            propertyDescription.getLocality().split("_")[2]));
            locality.ifPresent(location -> propertyDescriptionDto.setLocality(location.getLabel()));
        }
        if (propertyDescription.getZipCode() != null) {
            Optional<ZipCode> zipCode = zipCodeRepository.findById(
                    new ZipCodeID(propertyDescription.getGovernorate().getId(), propertyDescription.getZipCode().split("_")[1],
                            propertyDescription.getZipCode().split("_")[2], propertyDescription.getZipCode().split("_")[3]));
            zipCode.ifPresent(code -> propertyDescriptionDto.setZipCode(code.getLabel()));
        }
        stepFive.setPropertyDescription(propertyDescriptionDto);
        stepFive.setMainContractualTerms(
                mainContractualTermsMapper.toDtoSummary(propertyRequest.getMainContractualTerms())
        );
        stepFive.setAuthorizationRequired(authorisationRequiredMapper.toDtoSummary(propertyRequest.getAuthorisationsRequired()));

        if (propertyRequest.getCurrencyFinancing() != null && propertyRequest.getCurrencyFinancing().getCurrencyFinancingData() != null) {
            stepFive.setCurrencyFinancingList(
                    currencyInvestmentFinancingMapper.toDtoForStepFiveDirectInvest(propertyRequest.getCurrencyFinancing().getCurrencyFinancingData())
            );

        }
        return stepFive;
    }

    public void validateSpecificReferential(Long investType, Long vocation, Long location, Long usage,
                                            List<SpecificReferential> specificReferentials) {

        if (!CommonUtilities.existIdInSpecificReferential(specificReferentials, investType)) {
            throw new DataNotFoundException(ApplicationConstants.INVEST_TYPE_NOT_FOUND,
                    ApplicationConstants.NO_INVEST_TYPE_FOUNDED_WITH_ID + investType);
        }

        if (vocation != null && !CommonUtilities.existIdInSpecificReferential(specificReferentials, vocation)) {
            throw new DataNotFoundException(ApplicationConstants.VOCATION_NOT_FOUND,
                    ApplicationConstants.NO_VOCATION_FOUNDED_WITH_ID + vocation);
        }
        if (!CommonUtilities.existIdInSpecificReferential(specificReferentials, location)) {
            throw new DataNotFoundException(ApplicationConstants.LOCATION_NOT_FOUND,
                    ApplicationConstants.NO_LOCATION_FOUNDED_WITH_ID + location);
        }
        if (!CommonUtilities.existIdInSpecificReferential(specificReferentials, usage)) {
            throw new DataNotFoundException(ApplicationConstants.USAGE_NOT_FOUND,
                    ApplicationConstants.NO_USAGE_FOUNDED_WITH_ID + usage);
        }

    }

    public void validateInvestmentAddress(String governorateId, String delegationId, String localityId,
                                          String zipCodeId) {

        if (!governorateRepository.existsById(governorateId)) {
            throw new DataNotFoundException(ApplicationConstants.GOVERNORATE_NOT_FOUND,
                    ApplicationConstants.NO_GOVERNORATE_FOUNDED_WITH_ID + governorateId);
        }
        if (!ObjectUtils.isEmpty(delegationId) && delegationRepository.findById(new DelegationID(governorateId, delegationId)) == null) {
            throw new DataNotFoundException(ApplicationConstants.DELEGATION_NOT_FOUND,
                    ApplicationConstants.NO_DELEGATION_FOUNDED_WITH_ID + delegationId);
        }
        if (!ObjectUtils.isEmpty(localityId) && locationRepository.findById(new LocationID(governorateId, delegationId, localityId)) == null) {
            throw new DataNotFoundException(ApplicationConstants.LOCALITY_NOT_FOUND,
                    ApplicationConstants.NO_LOCALITY_FOUNDED_WITH_ID + localityId);
        }
        if (!ObjectUtils.isEmpty(zipCodeId) && zipCodeRepository.findById(new ZipCodeID(governorateId, delegationId, localityId, zipCodeId)) == null) {
            throw new DataNotFoundException(ApplicationConstants.ZIP_CODE_NOT_FOUND,
                    ApplicationConstants.NO_ZIP_CODE_FOUNDED_WITH_ID + localityId);
        }

    }

    public SpecificReferential getSpecificReferentialByIdFromList(List<SpecificReferential> list, Long id) {
        return list.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(
                () -> new DataNotFoundException(ApplicationConstants.SPECIFIC_REFERENTIAL_NOT_FOUND ,
                        ApplicationConstants.SPECIFIC_REFERENTIAL_NOT_FOUND_WITH_ID + id)
        );
    }

    @Override
    @Transactional
    public RequestStepSixForGet savePropertyRequestStepSeven(RequestStepSixForPost stepSixForPost, BindingResult result) throws MessagingException {

        log.info( "save Direct Invest Step Six ......  ");
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
        DirectInvestRequesterValidation.validateDirectInvestStepSix(stepSixForPost, result);

        PropertyRequest propertyRequest = propertyRequestRepository.findById(stepSixForPost.getFicheInvestId() )
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
                        "DIRECT INVEST REQUEST NOT FOUND WITH ID :" + stepSixForPost.getFicheInvestId()));
        utilityService.isAuthorizedForRequest(user, propertyRequest.getCreatedBy(),
                propertyRequest.getBanker(), propertyRequest.getStatus());

        validateBeforeSaveStepSix(
                stepSixForPost.getBankId(),
                stepSixForPost.getAgencyId(),
                propertyRequest, user

        );

        propertyRequest.setStatus(RequestStatusEnum.PENDING);
        propertyRequest.setExamineAcceptance(stepSixForPost.getExamineAcceptance());
        propertyRequest.setModifiedBy(user);
        propertyRequest.setLanguage(stepSixForPost.getLanguage());
        propertyRequest.setStep(StepEnum.STEP_SEVEN);
        propertyRequest.setTransmissionDate(new Date());
        propertyRequest = propertyRequestRepository.save(propertyRequest);

        if (propertyRequest.getId() != null) {
            RequestStepSixForGet propertyRequestForGet = iRequestMapper.toDto(propertyRequest);

            //sending mail
            GenericNotification genericNotification = GenericNotification.builder()
                    .language(stepSixForPost.getLanguage())
                    .label("TRANSMISSION")
                    .emailTo(propertyRequest.getBanker().getLogin())
                    .build();

            HashMap<String, String> myHashMap = new HashMap<>();
            myHashMap.put(ApplicationConstants.BANKREFERENCE, propertyRequestForGet.getFicheInvestId());
            myHashMap.put(ApplicationConstants.DECLARATIONDATE, propertyRequest.getCreationDate().toString());
            genericNotification.setAttributes(myHashMap);

            emailService.sendEmailSpecificTemplate(genericNotification);
            return propertyRequestForGet;

        }
        throw new DataNotFoundException("Failed to save DirectInvestStepSix", "Failed to save DirectInvestStepSix");

    }

    @Override
    public RequestStepSixForGet findStepSevenById(String id) {

        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
        PropertyRequest propertyRequest = propertyRequestRepository.findById(id )
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND,
                        ApplicationConstants.NO_PROPERTY_REQUEST_FOUNDED_WITH_ID +  id));

        return iRequestMapper.toDto(propertyRequest);
    }

    void validateBeforeSaveStepSix(String bankId , String agencyId , PropertyRequest propertyRequest, User user ){
        log.info("validate Before Save Step Six ....");
        Bank bank  = bankRepository.findById(bankId).orElseThrow(
                ()->  new DataNotFoundException(ApplicationConstants.BANK_NOT_FOUND , ApplicationConstants.NO_BANK_FOUNDED_WITH_ID + bankId)
        );

        AgencyId agencyEmbeddedId = new AgencyId();
        agencyEmbeddedId.setAgencyId(agencyId);
        agencyEmbeddedId.setBankId(bankId);

        Agency agency = agencyRepository.findById(agencyEmbeddedId).orElseThrow(
                ()-> new DataNotFoundException(ApplicationConstants.AGENCY_NOT_FOUND , ApplicationConstants.AGENCY_NOT_FOUND + ":" + agencyId)
        );
        switch (user.getUserType()) {
            case BANKER :
                propertyRequest.setBanker((Banker) user);
                break;
            case INVESTOR:
                Banker adminBanker = iBankerRepository.findFirstByApprovedIntermediaryIdAndIsAdmin(bankId , Boolean.TRUE).orElseThrow(
                        () -> new DataNotFoundException(ApplicationConstants.USER_NOT_FOUND , "Admin banker not found with bank id:"+bankId)
                );
                propertyRequest.setBanker(adminBanker);
            break;
            default:
                break;

        }

        // TODO remove this comment block

        /*if (propertyRequest.getDepositType() == null) {
            log.error("deposit type is null");
            throw new ConflictException(ApplicationConstants.DEPOSIT_TYPE_CAN_NOT_BE_NULL_AT_THIS_STEP,
                    "At this step we cannot have a null DEPOSIT_TYPE");
        }

        if (propertyRequest.getDepositType() == DepositType.DIRECT_DEPOSIT) {
            if (propertyRequest.getBank() != null && !bank.equals(propertyRequest.getBank())) {
                throw new ConflictException(ApplicationConstants.ERROR_CONFLICT_RELATE_BANK,
                        "the bank linked to the request is different from input:  " + bank.getLabel());
            }
            if (propertyRequest.getAgency() != null && !agency.equals(propertyRequest.getAgency())) {
                throw new ConflictException(ApplicationConstants.ERROR_CONFLICT_RELATE_AGENCY,
                        "the Agency linked to the request is different from input " + agency.getLabel());
            }
        }*/

        propertyRequest.setAgency(agency);
        propertyRequest.setBank(bank);
    }

    /**
     * validate and set Investor
     * @param propertyRequest propertyRequest
     * @param investorId id of investor
     */
    void validateInvestor( PropertyRequest  propertyRequest , String investorId , User user ){
        log.info("check investor with id :" + investorId);
        switch (user.getUserType()) {
            case INVESTOR:
                propertyRequest. setInvestor((Investor) user);
                break;
            case BANKER:
                if (CommonUtilities.isNullOrEmpty(investorId)) {
                    log.error("investor not found with id null ....");
                    throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND, "investor not found with id null ....");
                }

                Investor investor = investorRepository.findById(investorId)
                        .orElseThrow(() -> new DataNotFoundException(
                                ApplicationConstants.INVESTOR_NOT_FOUND,
                                "INVESTOR NOT FOUND WITH ID : " + investorId));

                propertyRequest.setInvestor(investor);
                break;
            default:
                throw new UnauthorizedException("403");
        }

    }
}
