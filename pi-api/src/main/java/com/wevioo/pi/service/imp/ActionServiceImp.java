package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.Action;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.ActionMapper;
import com.wevioo.pi.repository.ActionRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.ActionDto;
import com.wevioo.pi.service.ActionService;
import com.wevioo.pi.utility.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Action Service Imp
 */
@Service
public class ActionServiceImp implements ActionService {

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    ActionMapper  actionMapper;


    /**
     * Injected bean {@link UserService}
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Injected bean {@link SecurityUtils}
     */
    @Autowired
    private SecurityUtils securityUtils;

    /**
     * find action by connected user
     *
     * @return ActionDto
     */
    @Override
    public ActionDto findAction() {

        String userId = securityUtils.getCurrentUserId();
        Optional<User> user = userRepository.findById(userId);

        if(! user.isPresent()){
          throw new   DataNotFoundException(ApplicationConstants.USER_NOT_FOUND,
                    "user not found with id :"+userId) ;
        }
        Pageable pageable = PageRequest.of(0, 1);
        Page<Action> actionPage = actionRepository.findByUserIdOrderByDate(userId, pageable);
       if(!actionPage.getContent().isEmpty()){
           return  actionMapper.toDto(actionPage.getContent().get(0));
       }

    return  null;
    }
}
