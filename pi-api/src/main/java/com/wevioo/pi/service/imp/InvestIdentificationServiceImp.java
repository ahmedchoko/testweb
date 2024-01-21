package com.wevioo.pi.service.imp;


import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.domain.entity.request.ActivityDeclaration;
import com.wevioo.pi.domain.entity.request.ActivitySupport;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.InvestIdentification;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.domain.enumeration.ActivityTypeEnum;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.StepEnum;
import com.wevioo.pi.exception.AlreadyExistException;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.mapper.InvestIdentificationMapper;
import com.wevioo.pi.repository.ActivityDeclarationRepository;
import com.wevioo.pi.repository.ActivitySupportRepository;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.InvestIdentificationRepository;
import com.wevioo.pi.repository.NatClassRepository;
import com.wevioo.pi.repository.NatGroupRepository;
import com.wevioo.pi.repository.NatSectionRepository;
import com.wevioo.pi.repository.NatSubSectionRepository;
import com.wevioo.pi.repository.SpecificReferentialRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.ActivityDeclarationDto;
import com.wevioo.pi.rest.dto.request.ActivityDeclarationForPostDto;
import com.wevioo.pi.rest.dto.request.ActivitySupportForPostDto;
import com.wevioo.pi.rest.dto.request.InvestIdentificationForPostDto;
import com.wevioo.pi.rest.dto.response.InvestIdentificationForGetDto;
import com.wevioo.pi.service.InvestIdentificationService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ParameterService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.ActivityDeclarationValidator;
import com.wevioo.pi.validation.ActivitySupportValidator;
import com.wevioo.pi.validation.InvestIdentificationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class InvestIdentificationServiceImp implements InvestIdentificationService {


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
     * Injected bean {@link UserRepository}
     */
    @Autowired
    private KeyGenService  keyGenService;

    /**
     * Injected bean {@link UtilityService}
     */
    @Autowired
    private UtilityService utilityService;

    /**
     * Injected bean {@link SpecificReferentialRepository}
     */
    @Autowired
    private SpecificReferentialRepository  specificReferentialRepository;

    /**
     * Injected bean {@link NatGroupRepository}
     */
    @Autowired
    NatGroupRepository natGroupRepository;
    /**
     * Injected bean {@link NatSectionRepository}
     */
    @Autowired
    NatSectionRepository natSectionRepository;
    /**
     * Injected bean {@link NatSubSectionRepository}
     */
    @Autowired
    NatSubSectionRepository natSubSectionRepository;
    /**
     * Injected bean {@link NatClassRepository}
     */
    @Autowired
    NatClassRepository natClassRepository;

    /**
     * Injected bean {@link InvestIdentificationMapper}
     */
    @Autowired
    InvestIdentificationMapper  investIdentificationMapper;

    /**
     * Injected bean {@link InvestIdentificationRepository}
     */
    @Autowired
    InvestIdentificationRepository  investIdentificationRepository;

    /**
     * Injected bean {@link ActivityDeclarationRepository}
     */
    @Autowired
    ActivityDeclarationRepository  activityDeclarationRepository;
    /**
     * Injected bean {@link DirectInvestRequestRepository}
     */

    @Autowired
    private DirectInvestRequestRepository directInvestRequestRepository;

    /**
     * Injected bean {@link ActivitySupportRepository}
     */
    @Autowired
    ActivitySupportRepository  activitySupportRepository;


    /**
     * Inject {@link ParameterService} bean
     */
    @Autowired
    private ParameterService parameterService;

    /**
     * @param stepOne
     * @param result
     * @return
     */
    @Override
    @Transactional
    public InvestIdentificationForGetDto saveStepOne(InvestIdentificationForPostDto stepOne, BindingResult result) {

        String userId = securityUtils.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));

        if(stepOne == null || stepOne.getFicheInvestId() == null){
            throw  new BadRequestException("400" , ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,  ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND );
        }
        DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(stepOne.getFicheInvestId()).orElseThrow(
                ()-> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND, "DIRECT INVEST REQUEST NOT FOUND WITH ID :"
                        + stepOne.getFicheInvestId()));
//        if (!user.equals(directInvestRequest.getCreatedBy())) {
//            throw  new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST,  ApplicationConstants.UNAUTHORIZED_MSG );
//
//        }
        Boolean  isCreate = Boolean.TRUE;
        String   investIdentificationId = null;
       if(directInvestRequest.getInvestIdentification() != null){
           isCreate = Boolean.FALSE;
           investIdentificationId = directInvestRequest.getInvestIdentification().getId();
       }
        Parameter parameter = parameterService.getByKey(ApplicationConstants.DECLARATION_CERTIFICATE_OF_DEPOSIT);
        String paramValue = parameter.getParameterValue();
        List<Long> paramValueLongList = Arrays.stream(paramValue.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        // Checking for Authorized User
    //    utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
        // validate invest
        InvestIdentificationValidator.validatePost(stepOne , result , user.getUserType());
        // validate Main declaration
        ActivityDeclarationValidator.validateActivityMain(stepOne.getMainActivityDeclaration() , result);
        // validate  main support activity  list
        ActivitySupportValidator.validateActivitySupportPost(stepOne.getMainActivitySupportList(), result , stepOne.isMainActivitySupport() , ApplicationConstants.MAIN_ACTIVITY_SUPPORT_LIST , paramValueLongList);
        // validate activity secondary
        ActivityDeclarationValidator.validateActivitySecondary(stepOne.getActivityDeclarationSecondary() ,result, stepOne.isSecondaryActivityDeclaration());
        // validate    support activity  secondary  list
        ActivitySupportValidator.validateActivitySupportPost(stepOne.getSecondaryActivitySupportList(), result , stepOne. isSecondaryActivitySupport() , ApplicationConstants.SECONDARY_ACTIVITY_SUPPORT_LIST , paramValueLongList);
          if(result.hasErrors()){
              throw  new BadRequestException("400",result);
          }
//        if (Boolean.TRUE.equals(isCreate)) {
//            validateUniqueIdentifierForCreate(stepOne.getUniqueIdentifier());
//        } else {
//            validateUniqueIdentifierForUpdate(stepOne.getUniqueIdentifier(), investIdentificationId);
//        }
        // extract Specific Referential Ids from stepOne
        List<Long> ids = extractSpecificReferentialIds(stepOne);
        // find All specific referential by ids
        List<SpecificReferential> specificReferential =  specificReferentialRepository.findAllByIds(ids);
        InvestIdentification investIdentification = getInstance(stepOne , investIdentificationId);
        if( Boolean.TRUE.equals(isCreate)){
            investIdentification.setCreatedBy(user);
            investIdentification.setId(keyGenService.getNextKey(KeyGenType.INVEST_IDENTIFICATION_KEY , true , null));
        }else{
            investIdentification.setModifiedBy(user);
        }
        investIdentification.setLegalForm(specificReferential.stream()
                        .filter(p -> p.getId().equals(stepOne.getLegalFormId()))
                        .findFirst().orElseThrow(
                        () -> new DataNotFoundException(ApplicationConstants.SPECIFIC_REFERENTIAL_NOT_FOUND, "SPECIFIC REFERENTIAL NOT FOUND" )
                ));
        List<ActivityDeclaration>  activityDeclarations = new ArrayList<>();
        activityDeclarations.add(toActivityDeclarationEntity(stepOne.getMainActivityDeclaration(), ActivityTypeEnum.PRIMARY , user , investIdentification));
        // if main activity  support  true
        List<ActivitySupport> activitySupports = new ArrayList<>();
        if(investIdentification.isMainActivitySupport()){
            activitySupports.addAll(
                   toActivitySupportEntity(stepOne.getMainActivitySupportList() , specificReferential ,user , ActivityTypeEnum.PRIMARY , investIdentification)
           );
        }
        // is Secondary Activity Declaration true
        if(investIdentification.isSecondaryActivityDeclaration()) {
            activityDeclarations.add(
                    toActivityDeclarationEntity(stepOne.getActivityDeclarationSecondary() , ActivityTypeEnum.SECONDARY , user ,  investIdentification)
            );
       //  is Secondary Activity Support
            if(investIdentification.isSecondaryActivitySupport()) {
                activitySupports.addAll(
                        toActivitySupportEntity(stepOne.getSecondaryActivitySupportList() , specificReferential ,user ,  ActivityTypeEnum.SECONDARY , investIdentification  )
                );
            }
        }
        Boolean isLegalFormSa =  Boolean.FALSE;
        SpecificReferential legalForm = investIdentification.getLegalForm();
        Parameter paramSa = parameterService.getByKey("legal.form.sa");
        String valueSA = paramSa.getParameterValue();
        if(valueSA.equals(legalForm.getCode())){
            isLegalFormSa = true;
        }

        if(!isLegalFormSa){
            investIdentification.setQuoted(null);
        }

        investIdentification.getActivityDeclarations().addAll(activityDeclarations);
        investIdentification.getActivitySupports().addAll(activitySupports);
        investIdentification.setDirectInvestRequest(directInvestRequest);
        investIdentification =  investIdentificationRepository.save(investIdentification);
        directInvestRequest.setInvestIdentification(investIdentification);
        if(Boolean.TRUE.equals(isCreate)){
            directInvestRequest.setStep(StepEnum.STEP_ONE);
        }
        directInvestRequest =  directInvestRequestRepository.save(directInvestRequest);
        return   investIdentificationMapper.toInvestIdentificationDto(
                directInvestRequest.getInvestIdentification()
        );
    }

    /**
     * @param id
     * @return
     */
    @Override
    public InvestIdentificationForGetDto findById(String id) {
        DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND, "DIRECT INVEST REQUEST NOT FOUND WITH ID :"
                        + id));

        InvestIdentificationForGetDto dto = investIdentificationMapper.toDto(directInvestRequest.getInvestIdentification());
        formatterData(dto);

           return  dto;
    }

    /**
     *  formatter data  to front
     * @param dto
     */
    private  void formatterData(InvestIdentificationForGetDto dto) {
        log.info("formatter data ..... ");
        if(dto != null){
            // formatter data to Front
            Optional<ActivityDeclarationDto>  mainActivity = dto.getActivityDeclarations()
                    .stream()
                    .filter(p -> ActivityTypeEnum.PRIMARY == p.getType()).findFirst();
            dto.setMainActivityDeclaration(mainActivity.isPresent() ? mainActivity.get() : null);

            Optional<ActivityDeclarationDto>   secondary = dto.getActivityDeclarations()
                    .stream()
                    .filter(p -> ActivityTypeEnum. SECONDARY == p.getType()).findFirst();

            dto.setActivityDeclarationSecondary(secondary.isPresent() ? secondary.get() : null);

            dto.setMainActivitySupportList(
                    dto.getActivitySupports().
                            stream()
                            .filter(p -> ActivityTypeEnum.PRIMARY == p.getActivityType())
                            .collect(Collectors.toList())
            );

            dto.setSecondaryActivitySupportList(
                    dto.getActivitySupports().
                            stream()
                            .filter(p -> ActivityTypeEnum.SECONDARY == p.getActivityType())
                            .collect(Collectors.toList())
            );
            dto.setActivitySupports(null);
            dto.setActivityDeclarations(null);
        }
    }


    private List<Long> extractSpecificReferentialIdsFromActivitySupport( List<ActivitySupportForPostDto>  list) {
        if (list == null || list.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> ids = new ArrayList<>();
        List<Long>   typeActivitySupportIds = list.stream().map(ActivitySupportForPostDto::getTypeActivitySupportId).collect(Collectors.toList());
        List<Long>   issuingAuthorityIds = list.stream().map(ActivitySupportForPostDto::getIssuingAuthorityId).collect(Collectors.toList());
        ids.addAll(typeActivitySupportIds);
        ids.addAll(issuingAuthorityIds);
        return ids;
    }

    private List<Long> extractSpecificReferentialIds( InvestIdentificationForPostDto stepOne){
        if(stepOne == null){
            return Collections.emptyList();
        }
        List<Long> ids = new ArrayList<>();
        ids.add(stepOne.getLegalFormId()  != null ? stepOne.getLegalFormId() : null );
        ids.addAll(extractSpecificReferentialIdsFromActivitySupport(stepOne.getMainActivitySupportList()));
        ids.addAll(extractSpecificReferentialIdsFromActivitySupport(stepOne.getSecondaryActivitySupportList()));
        return ids;
    }

    private List<ActivitySupport> toActivitySupportEntity(List<ActivitySupportForPostDto>   activitySupports ,
                                                          List<SpecificReferential> specificReferential,
                                                          User user,
                                                          ActivityTypeEnum activityTypeEnum ,
                                                          InvestIdentification identification  ){
        log.info("to Activity Support Entity ....");
        if(activitySupports == null || activitySupports.isEmpty()) {
            return  Collections.emptyList();
        }
        List<ActivitySupport> activitySupportEntity = new ArrayList<>();
        for (ActivitySupportForPostDto item : activitySupports){

            ActivitySupport activitySupport = new ActivitySupport();
            activitySupport.setCreatedBy(user);
            activitySupport.setId(keyGenService.getNextKey(KeyGenType.ACTIVITY_SUPPORT_KEY , true , null));
            activitySupport.setInvestIdentification(identification);
            activitySupport.setActivityType(activityTypeEnum);
            activitySupport.setTypeActivitySupport(
                    specificReferential.stream().filter(p-> p.getId().equals(item.getTypeActivitySupportId())).findFirst().orElseThrow(
                            () -> new DataNotFoundException(ApplicationConstants.SPECIFIC_REFERENTIAL_NOT_FOUND ,  "SPECIFIC REFERENTIAL NOT FOUND WITH ID : " + item.getTypeActivitySupportId())
                    )
            );
            activitySupport. setIssuingAuthority(
                    item. getIssuingAuthorityId() != null ?
                    specificReferential.stream().filter(p-> p.getId().equals(item. getIssuingAuthorityId())).findFirst().orElseThrow(
                            () -> new DataNotFoundException(ApplicationConstants.SPECIFIC_REFERENTIAL_NOT_FOUND ,  "SPECIFIC REFERENTIAL NOT FOUND WITH ID : " + item.getIssuingAuthorityId())
                    ) : null
            );
            activitySupport.setSupportNumber(item.getSupportNumber() != null ? item.getSupportNumber() : null);
            activitySupport.setDateSupport((item.getDateSupport() != null ? item.getDateSupport() : null));
            activitySupportEntity.add(activitySupport);

        }


        return activitySupportEntity;

    }


    private  ActivityDeclaration  toActivityDeclarationEntity( ActivityDeclarationForPostDto dto  ,
                                                               ActivityTypeEnum activityTypeEnum ,
                                                               User user ,
                                                               InvestIdentification identification){
        if(dto == null  ){
            return  null;
        }

         if(dto.getActivitySectorId() == null || dto.getActivitySectorId().isEmpty()){
             throw  new BadRequestException("400, sector id is null or empty !!!!");
         }

         ActivityDeclaration activityDeclaration = new ActivityDeclaration();

         activityDeclaration.setCreatedBy(user);
         activityDeclaration.setId(keyGenService.getNextKey(KeyGenType.ACTIVITY_DECLARATION_KEY , true , null));
         activityDeclaration.setInvestIdentification(identification);
         activityDeclaration.setActivitySector(natSectionRepository.findById(dto.getActivitySectorId()).orElseThrow(
                 () ->  new DataNotFoundException(ApplicationConstants.ACTIVITY_SECTOR_NOT_FOUND , "ACTIVITY SECTOR NOT FOUND WITH  ID :" + dto.getActivitySectorId())
         ));

        activityDeclaration. setActivitySubSector( dto.getActivitySubSectorId() != null  ? natSubSectionRepository.findById(dto.getActivitySubSectorId()).orElseThrow(
                () ->  new DataNotFoundException(ApplicationConstants. ACTIVITY_SUB_SECTOR_NOT_FOUND , "ACTIVITY SUB SECTOR NOT FOUND WITH  ID :" + dto.getActivitySubSectorId())
        ) : null);

        activityDeclaration.  setActivityGroup( dto.getActivityGroupId()!= null ? natGroupRepository.findById(dto.getActivityGroupId()).orElseThrow(
                () ->  new DataNotFoundException(ApplicationConstants.  ACTIVITY_GROUP_NOT_FOUND , " ACTIVITY GROUP NOT FOUND WITH  ID :" + dto.getActivityGroupId())
        ): null);

        activityDeclaration.  setActivityClass( dto.getActivityClassId()!=null ? natClassRepository .findById(dto.getActivityClassId()).orElseThrow(
                () ->  new DataNotFoundException(ApplicationConstants.  ACTIVITY_CLASS_NOT_FOUND , "ACTIVITY  CLASS NOT FOUND WITH  ID :" + dto.getActivityClassId())
        ): null);

        activityDeclaration.setType(activityTypeEnum);
        return  activityDeclaration;

    }

    /**
     *  getInstances for  InvestIdentification
     * @param   stepOne
     * @return InvestIdentification
     */



        private  InvestIdentification getInstance(InvestIdentificationForPostDto stepOne , String idIdentification){

            if(stepOne == null){
                throw  new BadRequestException("400");
            }

            if(idIdentification == null){
                return  investIdentificationMapper.toEntity(stepOne);
            }

            InvestIdentification investIdentification   = investIdentificationRepository.findById(idIdentification).orElseThrow(
                    () -> new DataNotFoundException(ApplicationConstants.INVEST_IDENTIFICATION_NOT_FOUND,
                            " INVEST IDENTIFICATION NOT FOUND WITH ID :" + idIdentification)
            );

            investIdentification.setUniqueIdentifier(stepOne.getUniqueIdentifier() );
            investIdentification.setCompanyName( stepOne.getCompanyName() );
            investIdentification.setQuoted( stepOne.getQuoted());
            investIdentification.setResident( stepOne.isResident() );
            investIdentification.setSecondaryActivitySupport( stepOne.isSecondaryActivitySupport() );
            investIdentification.setMainActivitySupport( stepOne.isMainActivitySupport() );
            investIdentification.setSecondaryActivityDeclaration( stepOne.isSecondaryActivityDeclaration() );
            investIdentification.setAuthorityNumber( stepOne.getAuthorityNumber() );
            investIdentification.setAuthorityDate( stepOne.getAuthorityDate() );
            investIdentification.setBctAuthorization(stepOne.getBctAuthorization());

             activityDeclarationRepository.deleteAllByInvestIdentificationId(investIdentification.getId());
             activitySupportRepository.deleteAllByInvestIdentificationId(investIdentification.getId());
            investIdentification.getActivitySupports().clear();
            investIdentification.getActivityDeclarations().clear();
            return investIdentification;

        }

        /**
         * Validates a unique identifier for creation.
         *
         * @param uniqueIdentifier The unique identifier to be validated
         * @throws AlreadyExistException If the unique identifier already exists in the repository
         */
        private  void  validateUniqueIdentifierForCreate( String uniqueIdentifier ){
            log.info("validate Unique Identifier  .......");
             if(investIdentificationRepository.findByUniqueIdentifier(uniqueIdentifier).isPresent()){
                 throw  new AlreadyExistException(ApplicationConstants.UNIQUE_IDENTIFIER_ALREADY_EXISTS , ApplicationConstants. UNIQUE_IDENTIFIER_ALREADY_EXISTS) ;
         }
    }

    /**
     * Validates a unique identifier for update.
     *
     * @param uniqueIdentifier The unique identifier to be validated
     * @param idStep           The identification step
     * @throws AlreadyExistException If the unique identifier already exists for an entity with a different ID
     */

    private  void  validateUniqueIdentifierForUpdate(  String uniqueIdentifier , String idStep ){
        log.info("validate Unique Identifier  .......");
        if(investIdentificationRepository.findByUniqueIdentifierAndIdNot(uniqueIdentifier , idStep).isPresent()){
            throw  new AlreadyExistException(ApplicationConstants.UNIQUE_IDENTIFIER_ALREADY_EXISTS , ApplicationConstants. UNIQUE_IDENTIFIER_ALREADY_EXISTS) ;
        }
    }
}
