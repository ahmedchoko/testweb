package com.wevioo.pi.rest.resources;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.EmailSendingException;
import com.wevioo.pi.rest.dto.BctAgentForGetAllDto;
import com.wevioo.pi.rest.dto.BctAgentForGetDto;
import com.wevioo.pi.rest.dto.BctAgentForPostDto;
import com.wevioo.pi.rest.dto.BctAgentForPutDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.IBctAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * Rest controller for bct agent
 */
@RestController
@RequestMapping("/bctAgents")
@Slf4j
public class BctAgentController {


    @Autowired
    IBctAgentService iBctAgentService;


    /**
     * Endpoint to save a new BctAgent.
     *
     * @param bctAgentDto The BctAgentForPostDto object containing information for creating a new BctAgent
     * @param result      The BindingResult object to handle validation errors
     * @return   saved BctAgentForGetDto if successful, or an error response
     */
    @PostMapping
    public BctAgentForGetDto saveBctAgent(@RequestBody BctAgentForPostDto bctAgentDto   , BindingResult result)  {
        log.debug(" Start adding  bct agent  ....");
        try {
            return iBctAgentService.saveBctAgent(bctAgentDto , result);

        } catch (MessagingException e) {
            throw new EmailSendingException(ApplicationConstants.ERROR_EMAIL_SEND  , "Error sending Email");
        }
    }

    /**
     * Endpoint to retrieve all BctAgents with pagination and sorting.
     *
     * @param page The page number to retrieve
     * @param size The number of items per page
     * @param sort The sorting order for the results
     * @return   BctAgentForGetAllDto objects if successful, or an error response
     */

    @GetMapping
    public  PaginatedResponse<BctAgentForGetAllDto> findAll(
            @RequestParam(name = "page"  , required = false) Integer  page,
            @RequestParam(name = "size"  , required = false) Integer size,
            @RequestParam(name = "id"  , required = false) String  id,
            @RequestParam(name = "registrationNumber"  , required = false)  String registrationNumber,
            Sort  sort){
            log.debug(" Start   find all  bct   agent  .... !");
             return    iBctAgentService.findAll(id, registrationNumber, page , size , sort);

    }


    /**
     * Endpoint to update an existing BctAgent.
     *
     * @param id          The unique identifier of the BctAgent to update
     * @param bctAgentDto The BctAgentForPutDto object containing updated information for the BctAgent
     * @param result      The BindingResult object to handle validation errors
     * @return   BctAgentForGetDto if successful, or an error response
     */
    @PostMapping("/{id}")
    BctAgentForGetDto updateBctAgent(@PathVariable String id ,
                                               @RequestBody BctAgentForPutDto bctAgentDto ,
                                               BindingResult result){
        return   iBctAgentService.updateBctAgent(id , bctAgentDto , result);
    }

    /**
     * Endpoint to find a specific BctAgent by its unique identifier.
     *
     * @param id The unique identifier of the BctAgent to find
     * @return   the found BctAgentForGetDto if successful, or an error response
     */
    @GetMapping("/{id}")
    BctAgentForGetDto findBctAgentById(@PathVariable String id ){
        return  iBctAgentService.findBctAgentById(id);
    }
}
