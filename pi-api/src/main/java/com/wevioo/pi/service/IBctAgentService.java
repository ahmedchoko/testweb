package com.wevioo.pi.service;

import com.wevioo.pi.rest.dto.BctAgentForGetAllDto;
import com.wevioo.pi.rest.dto.BctAgentForGetDto;
import com.wevioo.pi.rest.dto.BctAgentForPostDto;
import com.wevioo.pi.rest.dto.BctAgentForPutDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;

import javax.mail.MessagingException;


public interface IBctAgentService {

     /**
      * Saves a new BctAgent using the provided BctAgentForPostDto.
      *
      * @param bctAgentForPostDto The BctAgentForPostDto object containing information for creating a new BctAgent
      * @param result             The BindingResult object to handle validation errors
      * @return BctAgentForGetDto representing the saved BctAgent if successful, null otherwise
      */
     BctAgentForGetDto saveBctAgent (BctAgentForPostDto   bctAgentForPostDto , BindingResult result) throws MessagingException;
     /**
      * Retrieves a page of BctAgentForGetAllDto objects
      * .
       * @param id The id   bct agent
      * @param registrationNumber The  registration Number
      * @param page The page number to retrieve
      * @param size The number of items per page
      * @param sort The sorting order for the results
      * @return Page of BctAgentForGetAllDto objects
      */
     PaginatedResponse<BctAgentForGetAllDto> findAll(String id , String registrationNumber ,Integer page, Integer size, Sort  sort);
     /**
      * Updates an existing BctAgent using the provided BctAgentForPutDto.
      *
      * @param id          The unique identifier of the BctAgent to update
      * @param bctAgentDto The BctAgentForPutDto object containing updated information for the BctAgent
      * @param result      The BindingResult object to handle validation errors
      * @return BctAgentForGetDto representing the updated BctAgent if successful, null otherwise
      */
     BctAgentForGetDto updateBctAgent (String id , BctAgentForPutDto bctAgentDto , BindingResult result);

     /**
      * Finds a BctAgent by its unique identifier.
      *
      * @param id The unique identifier of the BctAgent to find
      * @return BctAgentForGetDto representing the found BctAgent, or null if not found
      */
     BctAgentForGetDto findBctAgentById(String id);
}
