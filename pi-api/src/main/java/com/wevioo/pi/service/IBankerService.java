package com.wevioo.pi.service;


import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.rest.dto.BankerForGetAllDto;
import com.wevioo.pi.rest.dto.BankerForGetDto;
import com.wevioo.pi.rest.dto.BankerForPostDto;
import com.wevioo.pi.rest.dto.BankerForPutDto;
import com.wevioo.pi.rest.dto.response.BankerLightForGetDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;

import javax.mail.MessagingException;
import java.util.List;

public interface IBankerService {

    /**
     * Saves a new Banker using the provided BankerForPostDto.
     *
     * @param bankerDto The BankerForPostDto object containing information for creating a new Banker
     * @param result    The BindingResult object to handle validation errors
     * @return BankerForGetDto representing the saved Banker if successful, null otherwise
     */
     BankerForGetDto saveBanker (BankerForPostDto bankerDto , BindingResult result) throws MessagingException;
    /**
     * Retrieves a page of BankerForGetAllDto objects based on search criteria.
     *
     * @param page     The page number to retrieve
     * @param size     The number of items per page
     * @param sort     The sorting order for the results
     * @param bankerId The unique identifier of the banker (optional)
     * @param bankId   The unique identifier of the bank (optional)
     * @return Page of BankerForGetAllDto objects meeting the search criteria
     */
    PaginatedResponse<BankerForGetAllDto> findAllBySearch(Integer page, Integer size, Sort sort , String bankerId , String bankId);


    /**
     * Retrieves all BankerForGetAllDto objects based on search criteria.
     *
     * @param bankerId The unique identifier of the banker (optional)
     * @param bankId   The unique identifier of the bank (optional)
     * @return Page of BankerForGetAllDto objects meeting the search criteria
     */
    PaginatedResponse<BankerForGetAllDto> findAllBySearchExport (String bankerId , String bankId);
    /**
     * Updates an existing Banker using the provided BankerForPutDto.
     *
     * @param id         The unique identifier of the Banker to update
     * @param bankerDto  The BankerForPutDto object containing updated information for the Banker
     * @param result     The BindingResult object to handle validation errors
     * @return BankerForGetDto representing the updated Banker if successful, null otherwise
     */
    BankerForGetDto updateBanker (String id , BankerForPutDto bankerDto , BindingResult result) throws MessagingException;
    /**
     * Finds a Banker by its unique identifier.
     *
     * @param id The unique identifier of the Banker to find
     * @return BankerForGetDto representing the found Banker, or null if not found
     */
    BankerForGetDto findBankerById(String id);


    /**

     * @return list of bankers of certain bank not admin and active
     */
    List<BankerLightForGetDto> findByApprovedIntermediaryIdAndIsActiveAndIsAdmin (OperationType operationType);
}
