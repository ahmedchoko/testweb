package com.wevioo.pi.rest.resources;

import com.wevioo.pi.rest.dto.ActionDto;
import com.wevioo.pi.service.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Action Controller
 */
@Slf4j
@RestController
@RequestMapping(value = "/actions", produces = "application/json;charset=UTF-8")
public class ActionController {


    @Autowired
    ActionService actionService;

    /**
     * Retrieves the action details by  user connected.
     *
     * @return ActionDto containing the action details.
     */
    @GetMapping
    ActionDto findAction(){
        return actionService.findAction();
    }

}
