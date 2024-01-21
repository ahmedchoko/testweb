package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.ParticipationIdentification;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.StepEnum;
import com.wevioo.pi.exception.AlreadyExistException;
import com.wevioo.pi.exception.ConflictException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.ParticipationIdentificationMapper;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.ParticipationIdentificationRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.rest.dto.response.ParticipationIdentificationStepTwoForGetDto;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ParameterService;
import com.wevioo.pi.service.ParticipationIdentificationService;
import com.wevioo.pi.utility.CommonUtilities;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.ParticipationIdentificationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * service  Participation Identification
 */
@Service
public class ParticipationIdentificationServiceImp  implements ParticipationIdentificationService {
    /**
     * @param requestStepTwo
     * @return
     */
    /**
     * Inject {@link DirectInvestRequestRepository} bean.
     */
    @Autowired
    private DirectInvestRequestRepository directInvestRequestRepository;

    /**
     * Inject {@link ParticipationIdentificationMapper} bean.
     */
    @Autowired
    private ParticipationIdentificationMapper  participationIdentificationMapper;

    /**
     * Inject {@link ParticipationIdentificationRepository} bean.
     */
    @Autowired
    private ParticipationIdentificationRepository participationIdentificationRepository;
  
    /**
     * {@link KeyGenService} bean.
     */
    @Autowired
    private KeyGenService keyGenService;

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
     * Inject {@link ParameterService} bean
     */
    @Autowired
    private ParameterService parameterService;


    /**
     * Saves Participation Identification for Step Two.
     *
     * @param requestStepTwo The Step Two DTO containing information for saving Participation Identification.
     * @param result         The BindingResult for validation.
     * @return The DTO representing the saved Participation Identification.
     * @throws DataNotFoundException   if data is not found.
     * @throws AlreadyExistException   if the Participation Identification already exists.
     */
    @Override
    @Transactional
    public ParticipationIdentificationStepTwoForGetDto saveParticipationIdentification(ParticipationIdentificationStepTwoForPostDto requestStepTwo, BindingResult result) {


        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));

//
//        if (!user.equals(directInvestRequest.getCreatedBy())) {
//            throw  new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST,  ApplicationConstants.UNAUTHORIZED_MSG );
//
//        }
        Boolean isLegalFormSa =  Boolean.FALSE;
        Boolean isLegalFormSuarl =  Boolean.FALSE;
        Boolean isLegalFormSarl =  Boolean.FALSE;
        Boolean isLegalFormScact = Boolean.FALSE;
        if (CommonUtilities.isNullOrEmpty(requestStepTwo.getFicheInvestId())){
            throw  new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,ApplicationConstants.NO_DIRECT_INVEST_REQUEST_FOUND +requestStepTwo.getFicheInvestId());
        }

        DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(requestStepTwo. getFicheInvestId())
                .orElseThrow(()-> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND ,ApplicationConstants.NO_DIRECT_INVEST_REQUEST_FOUND +requestStepTwo.getFicheInvestId() ));

        validateStepOne(directInvestRequest);
        SpecificReferential legalForm = directInvestRequest.getInvestIdentification().getLegalForm();

        Map<String, Parameter> getMapParamByPrefix  = parameterService.getMapParamByPrefix(ApplicationConstants.LEGAL_FORM);


        String valueSA = getMapParamByPrefix.get("legal.form.sa").getParameterValue();
        if(valueSA.equals(legalForm.getCode())){
            isLegalFormSa = true;
        }
        String valueSARL = getMapParamByPrefix.get("legal.form.sarl").getParameterValue();
        if(valueSARL.equals(legalForm.getCode())){
            isLegalFormSarl = true;
        }

        String valueSUARL = getMapParamByPrefix.get("legal.form.suarl").getParameterValue();
        if(valueSUARL.equals(legalForm.getCode())){
            isLegalFormSuarl = true;
        }

        String valueSCACT = getMapParamByPrefix.get("legal.form.scact").getParameterValue();
        if(valueSCACT.equals(legalForm.getCode())){
            isLegalFormScact = true;
        }

        if(directInvestRequest.getOperationType() == null){
            throw  new ConflictException(ApplicationConstants.OPERATION_TYPE_NOT_FOUND , "Operation Type not found");
        }

        ParticipationIdentificationValidator.validateStepTwoForPost(requestStepTwo,directInvestRequest.getOperationType(),getIsQuotedValue(directInvestRequest),result, isLegalFormSa ,isLegalFormSarl , isLegalFormSuarl ,isLegalFormScact);

        ParticipationIdentification  participation;
        if(directInvestRequest.getParticipationIdentification() != null){
            participation = participationIdentificationMapper.toEntity(requestStepTwo);
            participation.setId(directInvestRequest.getParticipationIdentification().getId());
            participation.setCreatedBy(directInvestRequest.getParticipationIdentification().getCreatedBy());
            participation.setCreationDate(directInvestRequest.getParticipationIdentification().getCreationDate());
            participation.setModifiedBy(user);
            handleCapitalIncrease(directInvestRequest,participation);
            handlePaidCapitalLogic(directInvestRequest, participation, isLegalFormSa);

            handleDateFieldsBasedOnLegalForm(participation, isLegalFormSa, isLegalFormSarl, isLegalFormScact);

        }else {
            participation = participationIdentificationMapper.toEntity(requestStepTwo);
            participation.setId(keyGenService.getNextKey(KeyGenType.PARTICIPATION_IDENTIFICATION_KEY , true , null));
            participation.setCreatedBy(user);
            directInvestRequest.setStep(StepEnum.STEP_TWO);
        }

        directInvestRequest.setParticipationIdentification(participation);
        directInvestRequestRepository.save(directInvestRequest);
        return  participationIdentificationMapper.toDto(directInvestRequest);
    }


    /**
     * Handles the adjustment of date fields in the {@code ParticipationIdentification} based on legal form conditions.
     *
     * If the legal form is not SA, sets the transaction notice date and registration certificate date to null.
     * If the legal form is not SARL or not SCACT, sets the acquisition contract registration date to null.
     *
     * @param participation The participation identification entity to update date fields for.
     * @param isLegalFormSa Flag indicating whether the legal form is SA.
     * @param isLegalFormSarl Flag indicating whether the legal form is SARL.
     * @param isLegalFormScact Flag indicating whether the legal form is SCACT.
     */
    private void handleDateFieldsBasedOnLegalForm(ParticipationIdentification participation, boolean isLegalFormSa, boolean isLegalFormSarl, boolean isLegalFormScact) {
        if (!isLegalFormSa) {
            participation.setTransactionNoticeDate(null);
            participation.setRegistrationCertificateDate(null);
        }

        if (!isLegalFormSarl || !isLegalFormScact) {
            participation.setAcquisitionContractRegistrationDate(null);
        }
    }

    /**
     * Safely retrieves the 'isQuoted' value from the provided DirectInvestRequest.
     *
     * This method performs null checks at each step to avoid potential
     * NullPointerExceptions. If any of the steps in the chain is null,
     * the method returns null. Otherwise, it returns the Boolean value
     * of 'isQuoted' from the InvestIdentification.
     *
     * @param directInvestRequest The DirectInvestRequest object from which to retrieve the 'isQuoted' value.
     * @return The Boolean value of 'isQuoted' if available, or null if any step in the chain is null.
     */
    private Boolean getIsQuotedValue(DirectInvestRequest directInvestRequest) {
        if (directInvestRequest != null
                && directInvestRequest.getInvestIdentification() != null) {
            // Return the Boolean value if it's not null, otherwise return null
            return directInvestRequest.getInvestIdentification().getQuoted();
        }
        // Handle the case when any of the steps is null
        return null;
    }

    /**
     * Handles logic specific to capital increase operation in a direct invest request.
     *
     * If the operation type is CAPITAL_INCREASE and the method increase is true,
     * certain participation fields are set to null; otherwise, different fields are set to null.
     *
     * @param directInvestRequest The direct invest request entity.
     * @param participation The participation identification entity.
     */
    private void handleCapitalIncrease(DirectInvestRequest directInvestRequest, ParticipationIdentification participation) {
        if (OperationType.CAPITAL_INCREASE.equals(directInvestRequest.getOperationType())) {
            if (Boolean.TRUE == participation.getMethodIncrease()) {
                participation.setTotalNumberIssued(null);
                participation.setInvestedAmountInC(null);
                participation.setPartTotalInvestorNumber(null);
                participation.setTotalAmountInvested(null);
            } else {
                participation.setContributionAmount(null);
                participation.setNominalValueInC(null);
            }
        }
    }

    /**
     * Handles logic related to paid capital in a direct invest request.
     *
     * If the operation type is CAPITAL_INCREASE or COMPANY_CREATION and isLegalFormUsa is true,
     * adjustments are made to the participation based on the values of paidCapitalByTranche and questFirstTranche.
     *
     * @param directInvestRequest The direct invest request entity.
     * @param participation The participation identification entity.
     * @param isLegalFormUsa Flag indicating whether the legal form is USA.
     */
    private void handlePaidCapitalLogic(DirectInvestRequest directInvestRequest, ParticipationIdentification participation, Boolean isLegalFormUsa) {
        if (OperationType.CAPITAL_INCREASE.equals(directInvestRequest.getOperationType()) || OperationType.COMPANY_CREATION.equals(directInvestRequest.getOperationType()) && (Boolean.TRUE.equals(isLegalFormUsa))) {
                if (Boolean.TRUE.equals(participation.getPaidCapitalByTranche())) {
                    if (Boolean.TRUE.equals(participation.getQuestFirstTranche())) {
                        // Case when paidCapitalByTranche is TRUE and questFirstTranche is TRUE
                        participation.setReferenceDeclaration(null);
                        participation.setDateDeclaration(null);
                    }
                } else {
                    // Case when paidCapitalByTranche is FALSE
                    participation.setQuestFirstTranche(null);
                    // Set other values as needed
                    participation.setReferenceDeclaration(null);
                    participation.setDateDeclaration(null);
                }

        }
    }



    /**
     * @param id
     * @return
     */
    @Override
    public ParticipationIdentificationStepTwoForGetDto findStepTwoById(String id) {

        DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND , " fiche invest not found with id : " + id ));

        if(directInvestRequest.getParticipationIdentification() == null){
                 throw  new DataNotFoundException(ApplicationConstants. PARTICIPATION_NOT_FOUND , "Participation not found with fiche invest id : " + id);
             }

        return participationIdentificationMapper.toDto(
                directInvestRequest
        );
    }

    /**
     * validateStepOne
     * @param directInvestRequest
     */
    private void validateStepOne( DirectInvestRequest directInvestRequest){
        if( directInvestRequest.getInvestIdentification() == null
                || directInvestRequest.getInvestIdentification().getLegalForm() ==  null
                || directInvestRequest.getInvestIdentification().getLegalForm().getCode() == null
                || directInvestRequest.getInvestIdentification().getLegalForm().getCode().isEmpty()){
            throw  new DataNotFoundException(ApplicationConstants.BAD_REQUEST_CODE ,"Step one not created or Legal Form is invalid");
        }
    }
}
