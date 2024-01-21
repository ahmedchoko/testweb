package com.wevioo.pi.service.imp;


import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.MainContractualTerms;
import com.wevioo.pi.domain.entity.request.PropertyRequest;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.PersonType;
import com.wevioo.pi.domain.enumeration.StepEnum;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.MainContractualTermsMapper;
import com.wevioo.pi.repository.MainContractualTermsRepository;
import com.wevioo.pi.repository.PropertyRequestRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.request.MainContractualTermsStepTwoForPutDto;
import com.wevioo.pi.rest.dto.response.MainContractualTermsStepTwoForGetDto;
import com.wevioo.pi.service.IdentificationTypeService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ReferentialService;
import com.wevioo.pi.service.MainContractualTermsService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.utility.SpecificMapper;
import com.wevioo.pi.validation.MainContractualTermsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;


@Service
public
class MainContractualTermsServiceImpl implements MainContractualTermsService {


    /**
     * Injected bean {@link PropertyRequestRepository}
     */
    @Autowired
    private PropertyRequestRepository propertyRequestRepository;

    /**
     * Injected bean {@link SecurityUtils}
     */
    @Autowired
    private SecurityUtils securityUtils;

    /**
     * Injected bean {@link KeyGenService}
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     * Injected bean {@link IdentificationTypeService}
     */
    @Autowired
    private IdentificationTypeService identificationTypeService;

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
     * Injected bean {@link MainContractualTermsRepository}
     */
    @Autowired
    private MainContractualTermsRepository mainContractualTermsRepository;


    /**
     * Injected bean {@link SpecificMapper}
     */
    @Autowired
    SpecificMapper specificMapper;

    /**
     * Injected bean {@link SpecificMapper}
     */
    @Autowired
    MainContractualTermsMapper mainContractualTermsMapper;

    /**
     * Injected bean {@link ReferentialService}
     */
    @Autowired
    ReferentialService referentialService;


    /**
     * Injected bean {@link MainContractualTermsMapper}
     */
    @Autowired
    MainContractualTermsValidator validation;


    @Override
    @Transactional
    public
    MainContractualTermsStepTwoForGetDto saveMainContractualTerms(MainContractualTermsStepTwoForPutDto requestStepTwo, BindingResult result) {

        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
       utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
        MainContractualTerms mainContractualTerms ;
        validation.validateStepTwoForPost(requestStepTwo, result);
        PropertyRequest propertyRequest = propertyRequestRepository.findById(requestStepTwo.getIdPropertyRequest())
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND, " Property request not found with id : " + requestStepTwo.getIdPropertyRequest()));
        utilityService.isAuthorizedForRequest(user, propertyRequest.getCreatedBy(),
                propertyRequest.getBanker(), propertyRequest.getStatus());
        /// Mapping modified element
        if (propertyRequest.getMainContractualTerms() != null) {
            mainContractualTerms = mainContractualTermsMapper.toEntity(requestStepTwo);
            if(PersonType.PP == requestStepTwo.getPersonType()){
                mainContractualTerms.setSocialReason(null);
            }
            else{
                mainContractualTerms.setFirstName(null);
                mainContractualTerms.setLastName(null);
            }
            mainContractualTerms.setId(propertyRequest.getMainContractualTerms().getId());
            mainContractualTerms.setModifiedBy(user);
            mainContractualTerms.setCreationDate(propertyRequest.getMainContractualTerms().getCreationDate());
            mainContractualTerms.setCreatedBy(propertyRequest.getMainContractualTerms().getCreatedBy());
            /// Mapping created element
        } else {
            mainContractualTerms = mainContractualTermsMapper.toEntity(requestStepTwo);
            mainContractualTerms.setId(keyGenService.getNextKey(KeyGenType.MAIN_CONTRACTUAL_TERMS_KEY, true, null));
            mainContractualTerms.setCreatedBy(user);
        }
        mainContractualTerms.setStep(StepEnum.STEP_TWO);
        MainContractualTerms mainContractualTermsSaved = mainContractualTermsRepository.save(mainContractualTerms);
            propertyRequest.setMainContractualTerms(mainContractualTermsSaved);
            propertyRequestRepository.save(propertyRequest);
        // Mapping saved element
        return specificMapper.toMainContractualTermsStepTwoForGetDto(mainContractualTermsSaved, propertyRequest.getId());
    }

    @Override
    public
    MainContractualTermsStepTwoForGetDto getMainContractualTerms(String id) {
        PropertyRequest propertyRequest = propertyRequestRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND, " Property request not found with id : " + id ));
        return specificMapper.toMainContractualTermsStepTwoForGetDto(propertyRequest.getMainContractualTerms(), propertyRequest.getId());

    }
    
}


