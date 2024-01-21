package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.entity.referential.Currency;
import com.wevioo.pi.domain.entity.request.CurrencyFinancing;
import com.wevioo.pi.domain.entity.request.CurrencyFinancingData;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.PropertyRequest;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.StepEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.CurrencyInvestmentFinancingMapper;
import com.wevioo.pi.repository.BankRepository;
import com.wevioo.pi.repository.CurrencyInvestmentFinancingDataRepository;
import com.wevioo.pi.repository.CurrencyInvestmentFinancingRepository;
import com.wevioo.pi.repository.CurrencyRepository;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.PropertyRequestRepository;
import com.wevioo.pi.repository.SpecificReferentialRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.request.CurrencyInvestmentFinancingDto;
import com.wevioo.pi.rest.dto.request.CurrencyFinancingForPostDto;
import com.wevioo.pi.rest.dto.response.CurrencyFinancingForGetDto;
import com.wevioo.pi.service.CurrencyFinancingService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ReferentialService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.CurrencyFinancingValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CurrencyFinancingServiceImpl implements CurrencyFinancingService {

    /**
     * Injected bean {@link UserRepository}
     */

    @Autowired
    private UserRepository userRepository;

    /**
     * Injected bean {@link PropertyRequestRepository}
     */
    @Autowired
    private PropertyRequestRepository propertyRequestRepository;

    /**
     * Injected bean {@link BankRepository}
     */
    @Autowired
    private BankRepository bankRepository;

    /**
     * Injected bean {@link CurrencyRepository}
     */
    @Autowired
    private CurrencyRepository currencyRepository;

    /**
     * Injected bean {@link CurrencyInvestmentFinancingDataRepository}
     */
    @Autowired
    private CurrencyInvestmentFinancingDataRepository currencyInvestmentFinancingDataRepository;



    @Autowired
    private CurrencyInvestmentFinancingRepository currencyInvestmentFinancingRepository;
    /**
     * Injected bean {@link CurrencyInvestmentFinancingMapper}
     */
    @Autowired
    private CurrencyInvestmentFinancingMapper currencyInvestmentFinancingMapper;

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
     * Injected bean {@link KeyGenService}
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     * Inject {@link ReferentialService} service.
     */
    @Autowired
    private SpecificReferentialRepository specificReferentialRepository;

    /**
     * Inject {@link DirectInvestRequestRepository} bean.
     */
    @Autowired
    private DirectInvestRequestRepository directInvestRequestRepository;


    @Transactional
    @Override
    public CurrencyFinancingForGetDto savePropertyRequestCurrencyFinancingInformation(CurrencyFinancingForPostDto requestStepFour, BindingResult result) {

        log.info("save Property Request Currency Financing Information");
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        utilityService.isAuthorized(user,ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
        CurrencyFinancingValidator.validatePost(requestStepFour, result,user.getUserType());
        PropertyRequest propertyRequest = propertyRequestRepository.findById(requestStepFour.getFicheInvestId())
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND,
                        ApplicationConstants.NO_PROPERTY_REQUEST_FOUNDED_WITH_ID + requestStepFour.getFicheInvestId()));
        utilityService.isAuthorizedForRequest(user, propertyRequest.getCreatedBy(),
                propertyRequest.getBanker(), propertyRequest.getStatus());
        List<CurrencyFinancingData> currencyFinancingDataList;
        CurrencyFinancing currencyFinancing = new CurrencyFinancing();
        if (propertyRequest.getCurrencyFinancing() == null) {

            currencyFinancing.setId(keyGenService.getNextKey(KeyGenType.CURRENCY_FINANCING_KEY, true, null));
            currencyFinancing.setCreatedBy(user);
            currencyFinancing.setCreationDate(new Date());
            currencyFinancingDataList = buildCurrencyFinancingData(requestStepFour, user, currencyFinancing);
            propertyRequest.setCurrencyFinancing(currencyFinancing);
            propertyRequest.setStep(StepEnum.STEP_FOUR);
            propertyRequestRepository.save(propertyRequest);
            currencyFinancing.setCurrencyFinancingData(currencyFinancingDataList);
            currencyFinancing = currencyInvestmentFinancingRepository.save(currencyFinancing);
        } else {
            currencyFinancing = propertyRequest.getCurrencyFinancing();
            currencyFinancing. setModificationDate( new Date());
            currencyFinancing.setModifiedBy(  user);
            currencyFinancingDataList = buildCurrencyFinancingData(requestStepFour,  user, currencyFinancing);
            currencyInvestmentFinancingDataRepository.deleteAllByCurrencyFinancingId(currencyFinancing.getId());
            currencyFinancing.setCurrencyFinancingData(currencyFinancingDataList);
            currencyFinancing = currencyInvestmentFinancingRepository.save(currencyFinancing);
        }
         CurrencyFinancingForGetDto  currencyFinancingForGetDto   =  currencyInvestmentFinancingMapper.toDto( currencyFinancing );
         currencyFinancingForGetDto.setFicheInvestId(propertyRequest.getId());
         return  currencyFinancingForGetDto;

    }

    /**
     * build Currency Financing Data
     * @param requestStep
     * @param user
     * @param currencyFinancing
     * @return list of  CurrencyFinancingData
     */
    public List<CurrencyFinancingData> buildCurrencyFinancingData(CurrencyFinancingForPostDto requestStep, User user, CurrencyFinancing currencyFinancing) {

        List<CurrencyFinancingData> currencyFinancingDataList = new ArrayList<>();

        List<Long> specificReferentialIds = new ArrayList<>();
        List<String>  bankIds = new ArrayList<>();
        List<String> currencyIds = new ArrayList<>();
        // extract All ids
        extractAllIds(requestStep. getCurrencyFinancingData(),
                specificReferentialIds,
                bankIds,
                currencyIds
                );
        // find All
        List<SpecificReferential> specificReferentials = specificReferentialRepository.findAllByIds(specificReferentialIds);
        List<Bank> banks = bankRepository.findAllByIdIn(bankIds);
        List<Currency> currencies = currencyRepository.findAllByIdIn(currencyIds);

        // to Entity
        if(requestStep != null && requestStep.getCurrencyFinancingData() != null &&
               ! requestStep.getCurrencyFinancingData().isEmpty()
        ) {
            for (CurrencyInvestmentFinancingDto item : requestStep.getCurrencyFinancingData()) {
                CurrencyFinancingData currencyFinancingData = currencyInvestmentFinancingMapper.toEntity(item);
                currencyFinancingData.setId(keyGenService.getNextKey(KeyGenType.CURRENCY_FINANCING_DATA_KEY, true, null));
                currencyFinancingData.setCreatedBy(user);
                if (UserTypeEnum.BANKER.equals(user.getUserType())){
                    currencyFinancingData.setBank(
                            banks.stream().filter(p -> p.getId().equals(item.getBankId())).findFirst().orElseThrow(
                                    () -> new DataNotFoundException(ApplicationConstants.BANK_NOT_FOUND,
                                            ApplicationConstants.NO_BANK_FOUNDED_WITH_ID + item.getBankId())
                            )
                    );
                }
                currencyFinancingData.setFinancialCurrency(
                        currencies.stream().filter(p -> p.getId().equals(item.getFinancialCurrencyId())).findFirst().orElseThrow(
                                () -> new DataNotFoundException(ApplicationConstants.CURRENCY_NOT_FOUND,
                                        ApplicationConstants.NO_CURRENCY_FOUNDED_WITH_ID + item.getFinancialCurrencyId())
                        )
                );
                currencyFinancingData.setFinancialMode(
                        specificReferentials.stream().filter(p -> p.getId().equals(item.getFinancialModeId())).findFirst().orElseThrow(
                                () -> new DataNotFoundException(ApplicationConstants.FINANCIAL_MODE_NOT_FOUND,
                                        ApplicationConstants.NO_FINANCIAL_MODE_FOUNDED_WITH_ID + item.getFinancialModeId())
                        ));
                currencyFinancingData.setImportationPiece(
                        specificReferentials.stream().filter(p -> p.getId().equals(item.getImportationPieceId())).findFirst().orElseThrow(
                                () -> new DataNotFoundException(ApplicationConstants.IMPORTATION_PIECE_FOUND,
                                        ApplicationConstants.NO_IMPORTATION_PIECE_FOUNDED_WITH_ID + item.getImportationPieceId())
                        ));
                if(Boolean.TRUE.equals(item.getCurrencyCession())){
                    currencyFinancingData.setCessionDate(item.getCessionDate());
                }
                currencyFinancingData.setCounterValueOfImportedAmountTND(Boolean.TRUE.equals(item.getCurrencyCession()) ?
                        item.getImportedAmount().multiply(BigDecimal.valueOf(item.getCessionExchangeRate())) :
                        item.getCounterValueOfImportedAmountTND());
                currencyFinancingData.setCurrencyFinancing(currencyFinancing);
                currencyFinancingDataList.add(currencyFinancingData);
            }
        }
        return currencyFinancingDataList;
    }

    /**
     *
     * @param ficheInvestId The unique identifier used to retrieve property request currency financing information.
     * @return CurrencyFinancingForGetDto
     */

    @Override
    public CurrencyFinancingForGetDto getPropertyRequestCurrencyFinancingInformation(String ficheInvestId) {
     
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
        PropertyRequest propertyRequest = propertyRequestRepository.findById(ficheInvestId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND,
                        ApplicationConstants.NO_PROPERTY_REQUEST_FOUNDED_WITH_ID + ficheInvestId));
        CurrencyFinancingForGetDto currencyFinancingForGetDto = new CurrencyFinancingForGetDto();
        if (propertyRequest.getCurrencyFinancing() != null) {
             currencyFinancingForGetDto = currencyInvestmentFinancingMapper.toDto(propertyRequest.getCurrencyFinancing());
        }
        currencyFinancingForGetDto.setFicheInvestId(propertyRequest.getId());
        return currencyFinancingForGetDto;
    }

    /**
     * getCurrency Financing InformationBy Id
     * @param ficheInvestId
     * @return CurrencyFinancingForGetDto
     */
    @Override
    public CurrencyFinancingForGetDto getCurrencyFinancingInformationById(String ficheInvestId) {
         String userId = securityUtils.getCurrentUserId();
        userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
       // utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);


        DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(ficheInvestId)
                .orElseThrow(()-> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND ,
                        " fiche invest not found with id : " +ficheInvestId ));
        CurrencyFinancingForGetDto  currencyFinancingForGetDto = new CurrencyFinancingForGetDto();
        if (directInvestRequest.getCurrencyFinancing() != null ) {
            currencyFinancingForGetDto = currencyInvestmentFinancingMapper.toDto(directInvestRequest.getCurrencyFinancing());
        }
        currencyFinancingForGetDto.setFicheInvestId(directInvestRequest.getId());
        return currencyFinancingForGetDto;


    }

    /**
     * @param currencyFinancingForPostDto
     * @param result
     * @return
     */
    @Override
    @Transactional
    public CurrencyFinancingForGetDto saveCurrencyFinancingDirectInvest(CurrencyFinancingForPostDto currencyFinancingForPostDto, BindingResult result) {

        log.info("save Currency Financing DirectInvest ..... !");
        String userId = securityUtils.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));

       // utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);
        CurrencyFinancingValidator.validatePost(currencyFinancingForPostDto, result,user.getUserType());


        DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(currencyFinancingForPostDto. getFicheInvestId())
                .orElseThrow(()-> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND ,
                        " fiche invest not found with id : " +currencyFinancingForPostDto.getFicheInvestId() ));

//        if (!user.equals(directInvestRequest.getCreatedBy())) {
//            throw  new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST,  ApplicationConstants.UNAUTHORIZED_MSG );
//
//        }

        List<CurrencyFinancingData> currencyFinancingDataList ;
        CurrencyFinancing currencyFinancing = new CurrencyFinancing();
        if (directInvestRequest.getCurrencyFinancing() == null) {

            currencyFinancing.setId(keyGenService.getNextKey(KeyGenType.CURRENCY_FINANCING_KEY, true, null));
            currencyFinancing.setCreatedBy(user);
            currencyFinancing.setCreationDate(new Date());
            currencyFinancingDataList = buildCurrencyFinancingData(currencyFinancingForPostDto,  user, currencyFinancing);
            directInvestRequest.setCurrencyFinancing(currencyFinancing);
            directInvestRequestRepository.save(directInvestRequest);
            directInvestRequest.setStep(StepEnum.STEP_THREE);
            currencyFinancing.setCurrencyFinancingData(currencyFinancingDataList);
            currencyFinancing = currencyInvestmentFinancingRepository.save(currencyFinancing);
        } else {
            currencyFinancing = directInvestRequest.getCurrencyFinancing();
            currencyFinancing.setModifiedBy(user);
            currencyFinancing.setModificationDate(new Date());
            currencyFinancingDataList = buildCurrencyFinancingData(currencyFinancingForPostDto,  user, currencyFinancing);
            currencyInvestmentFinancingDataRepository.deleteAllByCurrencyFinancingId(currencyFinancing.getId());
            currencyFinancing.setCurrencyFinancingData(currencyFinancingDataList);
            currencyFinancing = currencyInvestmentFinancingRepository.save(currencyFinancing);
        }
             CurrencyFinancingForGetDto  currencyFinancingForGetDto=   currencyInvestmentFinancingMapper.toDto( currencyFinancing);
              currencyFinancingForGetDto.setFicheInvestId(directInvestRequest.getId());
              return currencyFinancingForGetDto;
    }

    /**
     * extract Specific Referential Ids
     * @param request
     * @return list of id
     */

    private List<Long> extractSpecificReferentialIds( CurrencyInvestmentFinancingDto  request){
        if(request == null){
            return Collections.emptyList();
        }
        List<Long> ids = new ArrayList<>();
        ids.add(request.getFinancialModeId()  != null ? request.getFinancialModeId() : null );
        ids.add(request. getImportationPieceId()  != null ? request.getImportationPieceId() : null );
        return ids;
    }

    /**
     * extract Bank Ids
     * @param request
     * @return lsit of ids bank
     */
    private List<String> extractBankIds( CurrencyInvestmentFinancingDto  request){
        if(request == null){
            return Collections.emptyList();
        }
        List<String> ids = new ArrayList<>();
        ids.add(request.  getBankId()  != null ? request.getBankId() : null );
        return ids;
    }

    /**
     * extract Currency Ids
     * @param request
     * @return List id  Currency
     */
    private List<String> extractCurrencyIds ( CurrencyInvestmentFinancingDto  request){
        if(request == null){
            return Collections.emptyList();
        }
        List<String> ids = new ArrayList<>();
        ids.add(request.   getFinancialCurrencyId()  != null ? request.getFinancialCurrencyId() : null );
        return ids;
    }

    /**
     * extract AllI ds ref , bank curency
     * @param investmentFinancingDtoList
     * @param specificReferentialIds
     * @param bankIds
     * @param currencyIds
     */

   private void extractAllIds(List<CurrencyInvestmentFinancingDto> investmentFinancingDtoList,
                       List<Long> specificReferentialIds ,
                       List<String> bankIds ,
                       List<String> currencyIds){


        if(investmentFinancingDtoList != null && !investmentFinancingDtoList.isEmpty()){

            for(CurrencyInvestmentFinancingDto item  : investmentFinancingDtoList){

                specificReferentialIds.addAll(extractSpecificReferentialIds(item));
                bankIds.addAll(extractBankIds(item));
                currencyIds.addAll(extractCurrencyIds(item));
            }
        }
    }

}
