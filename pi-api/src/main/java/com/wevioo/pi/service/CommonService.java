package com.wevioo.pi.service;

import com.wevioo.pi.rest.dto.request.AffectedToBankerDto;
import com.wevioo.pi.rest.dto.response.DirectInvestRequestForGet;
import org.springframework.validation.BindingResult;

public interface CommonService {

    /**
     * affected Direct Invest T oBanker
     *
     * @param affectedToBankerDto
     * @param result
     * @return
     */
    AffectedToBankerDto affectedFicheInvestToBanker(
            AffectedToBankerDto affectedToBankerDto, BindingResult result);
}
