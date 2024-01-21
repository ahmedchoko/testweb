package com.wevioo.pi.service;

import com.wevioo.pi.domain.enumeration.ClaimStatusEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.rest.dto.ClaimForGetDto;
import com.wevioo.pi.rest.dto.ClaimForPutDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service interface for managing claims.
 */
public interface ClaimService {

    /**
     * Adds a new claim with an optional file attachment.
     *
     * @param paramClaimForPostDto The JSON string containing properties for creating the claim.
     * @param file                 Optional MultipartFile representing an attached file.
     * @return A ClaimForGetDto object representing the details of the created claim.
     * @throws IOException              if an IO exception occurs during the claim creation.
     * @throws IllegalArgumentException if the provided JSON string is invalid.
     */
    ClaimForGetDto addClaim(String paramClaimForPostDto, MultipartFile file) throws IOException;

    /**
     * Adds a new public claim with an optional file attachment.
     *
     * @param paramClaimForPostDto The JSON string containing properties for creating the public claim.
     * @param file                 Optional MultipartFile representing an attached file.
     * @return A ClaimForGetDto object representing the details of the created public claim.
     * @throws IOException              if an IO exception occurs during the claim creation.
     * @throws IllegalArgumentException if the provided JSON string is invalid.
     */
    ClaimForGetDto addPublicClaim(String paramClaimForPostDto, MultipartFile file) throws IOException;

    /**
     * Treats an existing claim based on the provided information.
     *
     * @param claimForPutDto The data transfer object containing information for treating the claim.
     * @param result         BindingResult that holds validation errors, if any.
     * @return A ClaimForGetDto object representing the details of the treated claim.
     * @throws IOException              if an IO exception occurs during the claim treatment.
     * @throws IllegalArgumentException if the provided parameters are invalid.
     */
    ClaimForGetDto treatClaim(ClaimForPutDto claimForPutDto, BindingResult result) throws IOException;

    /**
     * Retrieves details of a claim identified by the provided ID.
     *
     * @param id The unique identifier of the claim to be retrieved.
     * @return A ClaimForGetDto object representing the details of the requested claim.
     * @throws IllegalArgumentException if the provided claim ID is invalid.
     */
    ClaimForGetDto getClaim(String id);

    /**
     * Changes the status of a claim identified by the provided ID.
     *
     * @param id The unique identifier of the claim for which the status will be changed.
     * @throws IllegalArgumentException if the provided claim ID is invalid.
     */
    void changeStatusClaim(String id);


    /**
     * Retrieves a paginated list of claims based on specified criteria.
     *
     * @param userType     User Type for filtering claims createdBy. (Optional)
     * @param ref          Reference identifier for filtering claims. (Optional)
     * @param status       Claim status for filtering claims. (Optional)
     * @param creationDate Date of claim creation for filtering claims. (Optional)
     * @param page         Page number for pagination. (Optional)
     * @param pageSize     Number of items per page for pagination. (Optional)
     * @param sort         Sorting criteria for the result set. (Optional)
     * @return PaginatedResponse containing ClaimForGetDto objects that match the specified criteria.
     * @throws IllegalArgumentException if the provided parameters are invalid.
     */
    PaginatedResponse<ClaimForGetDto> getAllClaims(UserTypeEnum userType, String ref, ClaimStatusEnum status, String creationDate,
                                                   Integer page, Integer pageSize, Sort sort);
}

