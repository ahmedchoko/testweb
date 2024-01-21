package com.wevioo.pi.service;

import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.rest.dto.request.AuthorizationsRequiredForPostDto;
import com.wevioo.pi.rest.dto.response.AuthorizationRequiredForGetDto;
import org.springframework.validation.BindingResult;

public
interface AuthorizationRequiredService {

    /**
     * Saves or modifies Authorization Required information for a property request.
     *
     * @param authorizationsRequiredForPostDto The data for Authorization Required to be saved or modified.
     * @param result                           The binding result for validation.
     * @return The saved or modified Authorization Required data for the property request.
     */
    AuthorizationRequiredForGetDto saveAuthorizationRequired(AuthorizationsRequiredForPostDto authorizationsRequiredForPostDto , BindingResult result);

    /**
     * Retrieves Authorization Required information for a property request by its ID.
     *
     * @param ficheInvestId The ID of the property request.
     * @return The Authorization Required data for the specified property request.
     * @throws DataNotFoundException If Authorization Required data is not found for the specified property request.
     */
    AuthorizationRequiredForGetDto getAuthorizationRequired(String ficheInvestId) throws DataNotFoundException;
}
