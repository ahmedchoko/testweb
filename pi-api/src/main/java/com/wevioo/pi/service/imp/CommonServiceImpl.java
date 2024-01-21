package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.PropertyRequest;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.IBankerRepository;
import com.wevioo.pi.repository.PropertyRequestRepository;
import com.wevioo.pi.rest.dto.request.AffectedToBankerDto;
import com.wevioo.pi.rest.dto.request.FicheInvestDto;
import com.wevioo.pi.service.CommonService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.DirectInvestRequesterValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    /**
     * Injected bean {@link IBankerRepository}
     */
    @Autowired
    private IBankerRepository iBankerRepository;

    /**
     * Injected bean {@link DirectInvestRequestRepository}
     */
    @Autowired
    private DirectInvestRequestRepository directInvestRequestRepository;

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

    @Override
    @Transactional
    public AffectedToBankerDto affectedFicheInvestToBanker(AffectedToBankerDto affectedToBankerDto, BindingResult result) {

        log.info("affected Direct Invest To BankerDto ......  ");

        String userId = securityUtils.getCurrentUserId();
        Banker banker = iBankerRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.BANKER_NOT_FOUND,
                        ApplicationConstants.BANKER_NOT_FOUND_WITH_ID + userId));

        if (Boolean.FALSE.equals(banker.getIsAdmin())) {
            throw new UnauthorizedException("403");
        }

        DirectInvestRequesterValidation.validateAffectedDirectInvestToBanker(affectedToBankerDto, result);

        Banker affectedTo = iBankerRepository.findById(affectedToBankerDto.getBankerId())
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.BANKER_NOT_FOUND,
                        ApplicationConstants.BANKER_NOT_FOUND_WITH_ID + affectedToBankerDto.getBankerId()));

        for (FicheInvestDto ficheInvestDto: affectedToBankerDto.getFicheInvestDtoList()
             ) {
            switch (ficheInvestDto.getFormInvest()) {
                case COMPANY_CREATION:
                case CAPITAL_INCREASE:
                case ACQUISITION_SHARES:
                    DirectInvestRequest directInvestRequest = directInvestRequestRepository
                            .findById(ficheInvestDto.getReference())
                            .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
                                    "DIRECT INVEST REQUEST NOT FOUND WITH ID :"
                                            + ficheInvestDto.getReference()));
                    directInvestRequest.setAffectedTo(affectedTo);
                    directInvestRequest.setStatus(RequestStatusEnum.WAITING_TO_BE_TAKEN_ON);
                    directInvestRequestRepository.save(directInvestRequest);
                    break;
                case INVESTING_REAL_ESTATE:
                    PropertyRequest propertyRequest = propertyRequestRepository
                            .findById(ficheInvestDto.getReference())
                            .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.PROPERTY_REQUEST_NOT_FOUND,
                                    ApplicationConstants.NO_PROPERTY_REQUEST_FOUNDED_WITH_ID + ficheInvestDto.getReference()));
                    propertyRequest.setBanker(affectedTo);
                    propertyRequest.setStatus(RequestStatusEnum.WAITING_TO_BE_TAKEN_ON);
                    propertyRequestRepository.save(propertyRequest);
                    break;
                default:
                    break;

            }
            }

        return affectedToBankerDto;
    }
}
