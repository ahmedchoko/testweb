package com.wevioo.pi.service;

import com.wevioo.pi.rest.dto.request.InvestIdentificationForPostDto;
import com.wevioo.pi.rest.dto.response.InvestIdentificationForGetDto;
import org.springframework.validation.BindingResult;

public interface InvestIdentificationService {
    /**
     * Saves step one of invest identification.
     *
     * @param stepOne The data for step one of invest identification.
     * @param result  The binding result for validation.
     * @return The InvestIdentificationForGetDto after saving step one.
     */
    InvestIdentificationForGetDto  saveStepOne(InvestIdentificationForPostDto stepOne ,  BindingResult result);
    /**
     * Finds InvestIdentificationForGetDto based on the provided ID.
     *
     * @param id The unique identifier used to find the InvestIdentificationForGetDto.
     * @return The InvestIdentificationForGetDto corresponding to the provided ID.
     */
    InvestIdentificationForGetDto findById(String  id);

}
