package com.wevioo.pi.service;


import com.wevioo.pi.rest.dto.request.MainContractualTermsStepTwoForPutDto;
import com.wevioo.pi.rest.dto.response.MainContractualTermsStepTwoForGetDto;
import org.springframework.validation.BindingResult;

public interface MainContractualTermsService {

    /**
     * @param requestStepTwo
     * @param result
     * @return MainContractualTermsStepTwo
     */
    MainContractualTermsStepTwoForGetDto saveMainContractualTerms(
            MainContractualTermsStepTwoForPutDto requestStepTwo,
            BindingResult result
    );

    /**
     * @param id
     * @return MainContractualTermsStepTwo
     */
    MainContractualTermsStepTwoForGetDto getMainContractualTerms(String id);


}
