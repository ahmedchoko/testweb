
package com.wevioo.pi.service.imp;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.ConflictException;
import com.wevioo.pi.rest.dto.InvestorForGetDto;
import com.wevioo.pi.rest.dto.InvestorForPostDto;
import com.wevioo.pi.rest.dto.GenericNotification;
import com.wevioo.pi.rest.dto.InvestorForGetListDto;
import com.wevioo.pi.rest.dto.InvestorForSelfPutDto;
import com.wevioo.pi.rest.dto.InvestorGetForPutDto;
import com.wevioo.pi.rest.dto.RepresentativeForPostDto;
import com.wevioo.pi.rest.dto.response.InvestorCheckForGetProjection;
import com.wevioo.pi.service.ParameterService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.ActivationLink;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.entity.account.Representative;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Country;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.LinkEnum;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.domain.enumeration.IdentificationTypeEnum;
import com.wevioo.pi.exception.AlreadyExistException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.mapper.InvestorMapper;
import com.wevioo.pi.mapper.RepresentativeMapper;
import com.wevioo.pi.repository.ActivationLinkRepository;
import com.wevioo.pi.repository.AttachmentsRepository;
import com.wevioo.pi.repository.CountryRepository;
import com.wevioo.pi.repository.InvestorRepository;
import com.wevioo.pi.repository.RepresentativeRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.EmailService;
import com.wevioo.pi.service.InvestorService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ReferentialService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.CommonsValidation;
import com.wevioo.pi.validation.InvestorValidator;

import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;

@Service
@Slf4j
public class InvestorServiceImpl implements InvestorService {

    @Autowired
    ActivationLinkRepository activationLinkRepository;

    /**
     * Injected bean {@link InvestorRepository}
     */
    @Autowired
    InvestorRepository investorRepository;

    /**
     * Injected bean {@link RepresentativeRepository}
     */
    @Autowired
    RepresentativeRepository representativeRepository;

    /**
     * Injected bean {@link AttachmentsRepository}
     */
    @Autowired
    AttachmentsRepository attachmentsRepository;

    /**
     * Injected bean {@link UserRepository}
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Injected bean {@link EmailService}
     */
    @Autowired
    EmailService emailService;

    /**
     * Inject {@link CountryRepository} bean
     */
    @Autowired
    private CountryRepository countryRepository;

    /**
     * Inject {@link ParameterService} bean
     */
    @Autowired
    private ParameterService parameterService;

    /**
     * Injected bean {@link ReferentialService}
     */
    @Autowired
    ReferentialService referentialService;

    /**
     * Injected bean {@link InvestorMapper}
     */
    @Autowired
    InvestorMapper investorMapper;

    /**
     * Injected bean {@link KeyGenService}
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     * Inject {@link ObjectMapper} bean
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Injected bean {@link RepresentativeMapper}
     */
    @Autowired
    RepresentativeMapper representativeMapper;

    /**
     * Injected bean {@link SecurityUtils}
     */
    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private MessageSource messageSource;

    /**
     * Injected bean {@link UtilityService}
     */
    @Autowired
    private UtilityService utilityService;

    /**
     * Inject {@link CommonService} bean
     */
    @Autowired
    private CommonService commonService;

    @Value("${otp-code-expiration-time}")
    private int otpCodeExpiration;

    @Value("${account-activation-url}")
    private String accountActivationUrl;

    /**
     * Inject {@link PasswordEncoder} bean
     */
    @Autowired
    private PasswordEncoder encoder;


    @Transactional
    @Override
    public
    InvestorForGetDto saveInvestor(String paramInvestorForPostDto, MultipartFile powerOfAttorney)
            throws IOException, MessagingException {
        log.info("Start saving investor");
        Investor investor;
        InvestorForPostDto investorForPostDto = objectMapper.readValue(paramInvestorForPostDto,
                InvestorForPostDto.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(investorForPostDto, "investorForPostDto");
        validateFile(powerOfAttorney , ApplicationConstants.PDF , bindingResult);
        InvestorValidator.validatePost(investorForPostDto, bindingResult);

        boolean hasRepresentative = investorForPostDto.isHasRepresentative();
        List<String> countriesIds = new ArrayList<>(hasRepresentative ? Arrays.asList(investorForPostDto.getCountryOfResidency(),
                investorForPostDto.getRepresentative().getCountryOfResidency(),
                investorForPostDto.getRepresentative().getNationality())
                : Collections.singletonList(investorForPostDto.getCountryOfResidency()));
        if (isInvestorForeign(investorForPostDto.getInvestorType())) {
            countriesIds.add(investorForPostDto.getNationality());
        }
       List<Country> countries = referentialService.findByIdIn(countriesIds);
        validateEmailExist(investorForPostDto.getEmail());
        Parameter param;
        String value;
        Country tnNationality = null;
        if (hasRepresentative) {
            InvestorValidator.validateRepresentativePost(investorForPostDto.getRepresentative(), bindingResult);
            validateCountries(investorForPostDto.getRepresentative().getNationality(),
                    investorForPostDto.getRepresentative().getCountryOfResidency(), countries);
        }
        if (isInvestorForeign(investorForPostDto.getInvestorType())) {
            validateCountries(investorForPostDto.getNationality(),investorForPostDto.getCountryOfResidency(), countries);
        } else {
            param = parameterService.getByKey(ApplicationConstants.TN_COUNTRY_KEY);
            value = param.getParameterValue();
            tnNationality = countryRepository.findByCode2(value);
            validateCountries(investorForPostDto.getCountryOfResidency(), countries);
            if (investorForPostDto.getNationality() != null && !investorForPostDto.getNationality().equals(tnNationality.getId())) {
                throw new ConflictException(ApplicationConstants.NATIONALITY_TN_CONFLICT,
                        ApplicationConstants.NATIONALITY_TN_CONFLICT_MSG);
            }
        }

        investor = investorMapper.postInvestorToInvestor(investorForPostDto);
        investor.setId(keyGenService.getNextKey(KeyGenType.INVESTOR_KEY , true , null));
        investor.setCountryOfResidency(getCountryByIdFromList(countries, investorForPostDto.getCountryOfResidency()));
        if (isInvestorForeign(investorForPostDto.getInvestorType())) {
            investor.setNationality(getCountryByIdFromList(countries, investorForPostDto.getNationality()));
        } else {
            investor.setNationality(tnNationality);
        }
        investor.setExpirationDate(instantToDate());
        investor.setUserType(UserTypeEnum.INVESTOR);
        investor.setPassword(encoder.encode(investorForPostDto.getPassword()));
        investor.setIsActive(false);


        if (hasRepresentative) {
            log.info("Start saving representative");
            RepresentativeForPostDto representativeForPostDto = investorForPostDto.getRepresentative();
            if (representativeForPostDto != null) {
                Representative representative = representativeMapper.postRepresentativeToRepresentative(representativeForPostDto);
                representative.setId(keyGenService.getNextKey(KeyGenType.REPRESENTATIVE_KEY , true , null));
                representative.setCountryOfResidency(getCountryByIdFromList(countries, representativeForPostDto.getCountryOfResidency()));
                representative.setNationality(getCountryByIdFromList(countries, representativeForPostDto.getNationality()));
                representative = representativeRepository.save(representative);
                investor.setRepresentative(representative);
                commonService.createAttachment(powerOfAttorney,investor,UserTypeEnum.INVESTOR);
            }
        }
        //unique existence of Unique Id , CIN , Passport Number
        if (investorRepository.existsByUniqueOrNationalIdOrPassportNumber(investorForPostDto.getUniqueId() , investorForPostDto.getNationalId() , investorForPostDto.getPassportNumber())){
            throw new ConflictException(ApplicationConstants.ERROR_NOT_UNIQUE , ApplicationConstants.ERROR_NOT_UNIQUE);
        }
        investor = investorRepository.save(investor);
        ActivationLink activationLink = generateActivationLink(investor, LinkEnum.ACCOUNT_CREATION);

        HashMap<String, String> myHashMap = new HashMap<>();

        GenericNotification genericNotification = GenericNotification.builder()
                .language(investorForPostDto.getLanguage())
                .label("ACTIVATION_LINK")
                .emailTo(investorForPostDto.getEmail())
                .build();

        myHashMap.put(ApplicationConstants.ACCOUNTACTIVATIONURL , accountActivationUrl);
        myHashMap.put(ApplicationConstants.ACTIVATIONKEY,activationLink.getActivationKey());
        myHashMap.put(ApplicationConstants.ID,investor.getId());
        myHashMap.put(ApplicationConstants.EMAIL,investorForPostDto.getEmail());
        myHashMap.put(ApplicationConstants.PASS,investor.getPassword());

        genericNotification.setAttributes(myHashMap);

           emailService.sendEmailSpecificTemplate(genericNotification);
       if (hasRepresentative) {
           genericNotification.setEmailTo(investorForPostDto.getRepresentative().getEmail());
           emailService.sendEmailSpecificTemplate(genericNotification);
       }
        return investorMapper.investorToInvestorForGet(investor);
    }

    /**
     * Validate File
     *
     * @param file      MultipartFile
     * @param extension file's extension
     */
    private void validateFile(MultipartFile file, String extension , Errors errors) {

        if( file != null && !file.isEmpty() && file.getSize() > 0) {
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            if (!extension.equals(ext)) {
                errors.rejectValue("file", ApplicationConstants.FILE_EXTENSION_INVALID, ApplicationConstants.FILE_EXTENSION_INVALID);
                throw new BadRequestException("400", errors);
            }
        }
    }

    private boolean isInvestorForeign(PersonTypeEnum investorType) {
        return investorType == PersonTypeEnum.PP_NON_RESIDENT_FOREIGN || investorType == PersonTypeEnum.PM_NON_RESIDENT_FOREIGN;
    }

    @Transactional
    @Override
    public InvestorForGetDto createInvestor(InvestorForPostDto investorForPostDto, BindingResult result) throws MessagingException {
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                       ApplicationConstants.USER_NOT_FOUND + userId));
        ///utilityService.isAuthorized(user,ApplicationConstants.USER_TYPE_BANKER);
        InvestorValidator.validatePost(investorForPostDto, result);
        List<String> countriesIds = new ArrayList<>(isInvestorForeign(investorForPostDto.getInvestorType()) ?
                Arrays.asList(investorForPostDto.getNationality(), investorForPostDto.getCountryOfResidency())
                : Collections.singletonList(investorForPostDto.getCountryOfResidency()));

        List<Country> countries = referentialService.findByIdIn(countriesIds);
        validateEmailExist(investorForPostDto.getEmail());

        Parameter param;
        String value;
        Country tnNationality = null;
        if(isInvestorForeign(investorForPostDto.getInvestorType())) {
            validateCountries(investorForPostDto.getNationality(), investorForPostDto.getCountryOfResidency(), countries);
        } else {
            validateCountries(investorForPostDto.getCountryOfResidency(), countries);
            param = parameterService.getByKey(ApplicationConstants.TN_COUNTRY_KEY);
            value = param.getParameterValue();
            tnNationality = countryRepository.findByCode2(value);
            if (investorForPostDto.getNationality() != null && !investorForPostDto.getNationality().equals(tnNationality.getId())) {
                throw new ConflictException(ApplicationConstants.NATIONALITY_TN_CONFLICT,
                        ApplicationConstants.NATIONALITY_TN_CONFLICT_MSG);
            }
        }


        Investor investor = investorMapper.postInvestorToInvestor(investorForPostDto);
        investor.setId(keyGenService.getNextKey(KeyGenType.INVESTOR_KEY, true , null));
        investor.setCountryOfResidency(getCountryByIdFromList(countries, investorForPostDto.getCountryOfResidency()));
        if (isInvestorForeign(investorForPostDto.getInvestorType())) {
            investor.setNationality(getCountryByIdFromList(countries, investorForPostDto.getNationality()));
        } else {
            investor.setNationality(tnNationality);
        }        investor.setExpirationDate(instantToDate());
        investor.setCreatedBy(user);
        investor.setUserType(UserTypeEnum.INVESTOR);
        investor.setPassword(encoder.encode(investorForPostDto.getPassword()));

        //unique existence of Unique Id , CIN , Passport Number
        if (investorRepository.existsByUniqueOrNationalIdOrPassportNumber(investorForPostDto.getUniqueId() , investorForPostDto.getNationalId() , investorForPostDto.getPassportNumber())){
            throw new ConflictException(ApplicationConstants.ERROR_NOT_UNIQUE , ApplicationConstants.ERROR_NOT_UNIQUE);
        }
        investor.setIsActive(true);
        investor = investorRepository.save(investor);
        ActivationLink activationLink = generateActivationLink(investor, LinkEnum.ACCOUNT_CREATION);

        HashMap<String, String> myHashMap = new HashMap<>();

        GenericNotification genericNotification = GenericNotification.builder()
                .language(investorForPostDto.getLanguage())
                .label("ACTIVATION_LINK")
                .emailTo(investorForPostDto.getEmail())
                .build();

        myHashMap.put(ApplicationConstants.ACCOUNTACTIVATIONURL,accountActivationUrl);
        myHashMap.put(ApplicationConstants.ACTIVATIONKEY,activationLink.getActivationKey());
        myHashMap.put(ApplicationConstants.ID,investor.getId());
        myHashMap.put(ApplicationConstants.EMAIL,investorForPostDto.getEmail());
        myHashMap.put(ApplicationConstants.PASS,investor.getPassword());

        genericNotification.setAttributes(myHashMap);

        emailService.sendEmailSpecificTemplate(genericNotification);
        return investorMapper.investorToInvestorForGet(investor);
    }

    @Override
    public InvestorForGetDto getInvestor() {
        String idUser = securityUtils.getCurrentUserId();

        Optional<Investor> investor = investorRepository.findById(idUser);
        if (!investor.isPresent()) {
            throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID,
                    ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID + idUser);
        }
        return investorMapper.investorToInvestorForGet(investor.get());
    }

    @Override
    public PaginatedResponse<InvestorForGetListDto> getInvestorsList(Integer page, Integer size, Sort sort,
                                                                     List<PersonTypeEnum> investorType , String systemId, String email , String investor , String identificaionValue , List<IdentificationTypeEnum> identificationTypes) {
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_ADMIN_BCT_BANKER);
        Sort sortingCriteria = utilityService.sortingCriteria(sort , Sort.Direction.DESC , ApplicationConstants.CREATION_DATE);
        Pageable pageable = utilityService.createPageable(page != null ? page : 1,  size != null ?  size : 10,
                sortingCriteria);
        PaginatedResponse<InvestorForGetListDto> response = new PaginatedResponse<>();
        Page<Investor> investorsPage;
        switch (user.getUserType()) {
            case BCT_ADMIN:
                investorsPage = investorRepository.findAllBySearch(investorType,systemId,email,investor,null, null,null,  pageable);
                break;
            case BANKER:
                List<String> identificationTypesStr ;
                /// test if not null and not empty ( if its passed empty in client side , to guarantee not empty : test the first element not null)
                if(identificationTypes!=null && identificationTypes.get(0) != null){
                    identificationTypesStr = identificationTypes.stream()
                            .map(IdentificationTypeEnum::name)
                            .collect(Collectors.toList());
                }
               else{
                    identificationTypesStr =null ;
                 }
                // TODO: TEST STRING IN A LIST OF ENUMS (JPQL)
                investorsPage = investorRepository.findAllBySearch(investorType, systemId, null, investor, userId, identificaionValue, identificationTypesStr, pageable);
                break;
            case BCT_AGENT:
                investorsPage = investorRepository.findAllBySearch(investorType,null,email,null , systemId, null ,null , pageable);
                break;
            default:
                throw new UnauthorizedException("403");

        }
        response.setTotalElement(investorsPage.getTotalElements());
        response.setTotalPage(investorsPage.getTotalPages());
        response.setPageSize(investorsPage.getSize());
        response.setPage(investorsPage.getNumber());
        response.setContent(investorsPage.getContent().stream().map(this::buildInvestorListDto).collect(Collectors.toList()));
        return  response;
    }
    @Override
    public PaginatedResponse<InvestorForGetListDto> getInvestorsListExport(List<PersonTypeEnum> investorType , String systemId, String email , String investor , String identificaionValue , List<IdentificationTypeEnum> identificationTypes) {
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        utilityService.isAuthorized(user, ApplicationConstants.USER_TYPE_ADMIN_BCT_BANKER);
        PaginatedResponse<InvestorForGetListDto> response = new PaginatedResponse<>();
        List<Investor> investorsPage;
        switch (user.getUserType()) {
            case BCT_ADMIN:
                investorsPage = investorRepository.findAllBySearchExport(investorType,systemId,email,investor,null, null,null);
                break;
            case BANKER:
                List<String> identificationTypesStr ;
                /// test if not null and not empty ( if its passed empty in client side , to guarantee not empty : test the first element not null)
                if(identificationTypes!=null && identificationTypes.get(0) != null){
                    identificationTypesStr = identificationTypes.stream()
                            .map(IdentificationTypeEnum::name)
                            .collect(Collectors.toList());
                }
                else{
                    identificationTypesStr =null ;
                }
                // TODO: TEST STRING IN A LIST OF ENUMS (JPQL)
                investorsPage = investorRepository.findAllBySearchExport(investorType, systemId, null, investor, userId, identificaionValue, identificationTypesStr);
                break;
            case BCT_AGENT:
                investorsPage = investorRepository.findAllBySearchExport(investorType,null,email,null , systemId, null ,null);
                break;
            default:
                throw new UnauthorizedException("403");

        }
        response.setContent(investorsPage.stream().map(this::buildInvestorListDto).collect(Collectors.toList()));
        return  response;
    }
    @Override
    public InvestorGetForPutDto getInvestorById(String id) {
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_ADMIN_BCT_BANKER);
        Optional<Investor> investorOptional = investorRepository.findById(id);
        if (investorOptional.isEmpty()) {
            throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID,
                    "Investor not found  with id: " + id);
        }
        return investorMapper.investorToInvestorForPut(investorOptional.get());
    }

    @Override
    public InvestorGetForPutDto disableInvestor(String id) {
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_ADMIN_BCT_BANKER);
        Optional<Investor> existingInvestor = investorRepository.findById(id);
        if (!existingInvestor.isPresent()) {
            throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID,
                    ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID + id);
        }
        Investor investor = existingInvestor.get();
        investor.setIsActive(false);
        return investorMapper.investorToInvestorForPut(investorRepository.save(investor));
    }

    @Transactional
    @Override
    public InvestorForGetDto updateInvestor(InvestorForSelfPutDto investorForSelfPutDto, BindingResult result) {
        String userId = securityUtils.getCurrentUserId();
        Optional<Investor> existingInvestor = investorRepository.findById(userId);
        if (!existingInvestor.isPresent()) {
            throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID,
                    ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID + userId);
        }
        CommonsValidation.validatePhoneNumber("cellPhone", result, investorForSelfPutDto.getCellPhone(), 15);
        List<Country> countries = referentialService.findByIdIn(Arrays.asList(investorForSelfPutDto.getNationality(), investorForSelfPutDto.getCountryOfResidency()));
        validateCountries(investorForSelfPutDto.getNationality(), investorForSelfPutDto.getCountryOfResidency(), countries);

        // validation unique passport num except Id element
        if(investorRepository.existsByPassportNumberAndIdIsNotLike(investorForSelfPutDto.getPassportNumber() , existingInvestor.get().getId())){
            throw new ConflictException(ApplicationConstants.ERROR_NOT_UNIQUE , ApplicationConstants.ERROR_NOT_UNIQUE);
        }
        Investor investor = existingInvestor.get();
        investor.setCountryOfResidency(getCountryByIdFromList(countries, investorForSelfPutDto.getCountryOfResidency()));
        investor.setNationality(getCountryByIdFromList(countries, investorForSelfPutDto.getNationality()));
        investor.setCellPhone(investorForSelfPutDto.getCellPhone());
        investor.setAddress(investorForSelfPutDto.getAddress());
        investor.setPassportNumber(investorForSelfPutDto.getPassportNumber());
        investor.setPassportExpirationDate(investorForSelfPutDto.getPassportExpirationDate());
        return investorMapper.investorToInvestorForGet(investorRepository.save(investor));
    }

    @Override
    public void verifyMail(String mail, BindingResult result) {
        CommonsValidation.emailValidation("email", result ,mail, 200);
        if (result.hasErrors()) {
            throw new BadRequestException("400", result);
        }
        validateEmailExist(mail );
    }

    @Override
    public InvestorCheckForGetProjection verifyInvestor(String id) {

      Optional <InvestorCheckForGetProjection> invest = investorRepository.checkInvestor(id);
        if (invest.isPresent()){
            return invest.get();
        }
        else{
            throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID,
                    ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID + id);
        }
    }


    @Override
    public boolean verifyNationality(PersonTypeEnum investorType, String nationality) {
        Parameter param = parameterService.getByKey(ApplicationConstants.TN_COUNTRY_KEY);
        String value = param.getParameterValue();
        Country country = countryRepository.findById(nationality).orElseThrow(()
                -> new DataNotFoundException(ApplicationConstants.COUNTRY_NOT_FOUND,
                ApplicationConstants.NO_COUNTRY_FOUNDED_WITH_ID + nationality));
        if ((investorType == PersonTypeEnum.PP_NON_RESIDENT_FOREIGN ||
                investorType == PersonTypeEnum.PM_NON_RESIDENT_FOREIGN)
                        && value.equals(country.getCode2())) {
            throw new ConflictException(ApplicationConstants.NATIONALITY_NOT_TN_CONFLICT,
                    ApplicationConstants.NATIONALITY_NOT_TN_CONFLICT_MSG);
        }
        if ((investorType == PersonTypeEnum.PP_NON_RESIDENT_TUNISIAN ||
                investorType == PersonTypeEnum.PM_NON_RESIDENT_TUNISIAN)
                && !value.equals(country.getCode2())) {
            throw new ConflictException(ApplicationConstants.NATIONALITY_TN_CONFLICT,
                    ApplicationConstants.NATIONALITY_TN_CONFLICT_MSG);
        }
        return true;
    }


    private InvestorForGetListDto buildInvestorListDto (Investor investor) {
        InvestorForGetListDto investorForGetListDto = new InvestorForGetListDto();
        investorForGetListDto.setId(investor.getId());
        investorForGetListDto.setCreationDate(investor.getCreationDate());
        if (PersonTypeEnum.PP_NON_RESIDENT_TUNISIAN == investor.getInvestorType() ||
                PersonTypeEnum.PP_NON_RESIDENT_FOREIGN == investor.getInvestorType()) {
            investorForGetListDto.setFullName(investor.getFirstName() + " " +investor.getLastName());
        }
        if (PersonTypeEnum.PM_NON_RESIDENT_TUNISIAN == investor.getInvestorType() ||
                PersonTypeEnum.PM_NON_RESIDENT_FOREIGN == investor.getInvestorType()) {
            investorForGetListDto.setFullName(investor.getSocialReason()!= null?investor.getSocialReason():investor.getNameForFund());
        }
        switch (investor.getInvestorType()) {
            case PP_NON_RESIDENT_TUNISIAN:
                investorForGetListDto.setInvestorType(ApplicationConstants.PP_NON_RESIDENT_TUNISIAN_LAB);
                break;
            case PP_NON_RESIDENT_FOREIGN:
                investorForGetListDto.setInvestorType(ApplicationConstants.PP_NON_RESIDENT_FOREIGN_LAB);
                break;
            case PM_NON_RESIDENT_TUNISIAN:
                investorForGetListDto.setInvestorType(ApplicationConstants.PM_NON_RESIDENT_TUNISIAN_LAB);
                break;
            case PM_NON_RESIDENT_FOREIGN:
                investorForGetListDto.setInvestorType(ApplicationConstants.PM_NON_RESIDENT_FOREIGN_LAB);
                break;
            default:
                break;
        }
        return investorForGetListDto;
    }

    public Country getCountryByIdFromList(List<Country> list, String id) {
        return list.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public void validateEmailExist(String email) {
        if (investorExistByEmail(email)) {
            throw new AlreadyExistException(ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
                    ApplicationConstants.EMAIL_ALREADY_EXIST_MSG);
        }
    }

    public void validateCountries(String investorNationality, String investorCountryOfResidency, List<Country> countries) {

        if (!existIdInCountries(countries, investorNationality)) {
            throw new DataNotFoundException(ApplicationConstants.COUNTRY_NOT_FOUND,
                    "No nationality founded with id: " + investorNationality);
        }

        if (!existIdInCountries(countries, investorCountryOfResidency)) {
            throw new DataNotFoundException(ApplicationConstants.COUNTRY_NOT_FOUND,
                    "No countryOfResidency founded with id: " + investorCountryOfResidency);
        }

    }

    public void validateCountries(String investorCountryOfResidency, List<Country> countries) {

        if (!existIdInCountries(countries, investorCountryOfResidency)) {
            throw new DataNotFoundException(ApplicationConstants.COUNTRY_NOT_FOUND,
                    "No countryOfResidency founded with id: " + investorCountryOfResidency);
        }

    }

    @Override
    public boolean investorExistByEmail(String email) {
        return userRepository.existsByLoginIgnoreCase(email);
    }

    boolean existIdInCountries(List<Country> countries, String id) {
        return countries.stream().anyMatch(c -> Objects.equals(c.getId(), id));
    }


    /**
     * Generate ActivationLink
     */
    private ActivationLink generateActivationLink(User user, LinkEnum type) {

        // get activation link
        ActivationLink activationLink = activationLinkRepository.findFirstByUserAndType(user, type);
        if (activationLink == null) {
            activationLink = new ActivationLink();
            activationLink.setUser(user);
            activationLink.setType(type);
        }

        // generate activation key
        activationLink
                .setActivationKey(RandomStringUtils.randomAlphanumeric(ApplicationConstants.ACTIVATION_CODE_LENGTH));

        activationLink.setExpirationDate(instantToDate());
        activationLink.setNumberAttempts(0);
        activationLink = activationLinkRepository.save(activationLink);
        return activationLink;

    }

    public Date instantToDate() {
        Instant now = Instant.now();
        Instant expirationTime = now.plus(otpCodeExpiration, ChronoUnit.SECONDS);
        return Date.from(expirationTime);
    }
}
