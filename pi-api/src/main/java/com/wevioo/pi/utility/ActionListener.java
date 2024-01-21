package com.wevioo.pi.utility;

import com.wevioo.pi.domain.entity.request.Action;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.repository.ActionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PostUpdate;
import java.util.Date;
import java.util.Optional;

/**
 * Action Listener class
 */

@Slf4j
public class ActionListener {


    @PostUpdate
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onPostUpdate(Object entity) {

        ActionRepository actionRepository = AuditUtil.getBean(ActionRepository.class);

        if (entity instanceof DirectInvestRequest) {

            DirectInvestRequest directInvestRequest = (DirectInvestRequest) entity;

            String ficheId =    ((DirectInvestRequest) entity).getId();
            Optional<Action> actionOptional = actionRepository.findFirstByFicheId(ficheId);

            if(actionOptional.isPresent() ){
                if( RequestStatusEnum.PENDING == directInvestRequest.getStatus()) {
                    actionRepository.deleteById(actionOptional.get().getId());
                    log.info(" delete action  by id {}", actionOptional.get().getId());
                }else {

                    Action action = actionOptional.get();
                    action.setStep(directInvestRequest.getStep());
                    action.setModificationDate(new Date());
                    action.setUserId(getUserId(directInvestRequest));
                    actionRepository.save(action);
                    log.info(" update action  from direct invest request entity !!!!");

                }

            }else {
                 Action action = Action.builder()
                         .userId( getUserId(directInvestRequest))
                         .step(directInvestRequest.getStep())
                         .ficheId(ficheId)
                         .creationDate(new Date())
                         // TODO : set operation type in fiche
                         .operationType(OperationType.COMPANY_CREATION)
                         .status(directInvestRequest.getStatus())
                         .build();

                actionRepository.save(action);
                log.info(" save new action from direct invest request entity !!!!");
            }
        }
    }


    /**
     * get User Id
     * @param directInvestRequest
     * @return user id
     */
    String   getUserId(DirectInvestRequest directInvestRequest){
         SecurityUtils securityUtils = AuditUtil.getBean(SecurityUtils.class) ;
        if (directInvestRequest.getAffectedTo() != null) {
            return directInvestRequest.getAffectedTo().getId();
        } else if (directInvestRequest.getModifiedBy() != null) {
            return directInvestRequest.getModifiedBy().getId();
        } else {
            if (directInvestRequest.getCreatedBy() == null) {
                return      securityUtils.getCurrentUserId();
            }
            return directInvestRequest.getCreatedBy().getId();
        }
    }
}
