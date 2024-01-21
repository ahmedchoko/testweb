package com.wevioo.pi.rest.resources;

import com.wevioo.pi.domain.enumeration.ClaimStatusEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.rest.dto.ClaimForGetDto;
import com.wevioo.pi.rest.dto.ClaimForPutDto;
import com.wevioo.pi.rest.dto.MessageDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Rest controller for managing claims.
 */
@Slf4j
@RestController
@RequestMapping(value = "/claims", produces = "application/json;charset=UTF-8")
public class ClaimController {

    @Autowired
    private ClaimService claimService;


    /**
     * Endpoint for creating a claim with optional file attachment.
     *
     * @param paramClaimForPostDto The JSON string containing properties for creating the claim.
     * @param file                 Optional MultipartFile representing an attached file.
     * @return A ClaimForGetDto object representing the details of the created claim.
     * @throws IOException if an IO exception occurs during the claim creation.
     * @apiNote This endpoint allows users to create a claim with the provided properties specified
     * in the JSON string. Optionally, a file can be attached to the claim. The result of the
     * creation is returned as a ClaimForGetDto object containing information about the created
     * claim. If the JSON string is invalid or an IO exception occurs during the creation
     * process, an exception is thrown.
     * @see ClaimForGetDto
     * @see MultipartFile
     */
    @PostMapping(consumes = "multipart/form-data")
    public ClaimForGetDto createClaim(@RequestPart("properties") String paramClaimForPostDto,
                                      @RequestPart(value = "file", required = false) MultipartFile file)
            throws IOException {

        log.info("Start creation of claim");
        return claimService.addClaim(paramClaimForPostDto, file);
    }


    /**
     * Endpoint for creating a public claim with optional file attachment.
     *
     * @param paramClaimForPostDto The JSON string containing properties for creating the claim.
     * @param file                 Optional MultipartFile representing an attached file.
     * @return A ClaimForGetDto object representing the details of the created claim.
     * @throws IOException if an IO exception occurs during the claim creation.
     * @apiNote This endpoint allows users to create a public claim with the provided properties
     * specified in the JSON string. Optionally, a file can be attached to the claim. The
     * result of the creation is returned as a ClaimForGetDto object containing information
     * about the created claim. If the JSON string is invalid or an IO exception occurs during
     * the creation process, an exception is thrown.
     * @see ClaimForGetDto
     * @see MultipartFile
     */
    @PostMapping(path = "/public", consumes = "multipart/form-data")
    public ClaimForGetDto createPublicClaim(@RequestPart("properties") String paramClaimForPostDto,
                                            @RequestPart(value = "file", required = false) MultipartFile file)
            throws IOException {

        log.info("Start creation of claim");
        return claimService.addPublicClaim(paramClaimForPostDto, file);
    }


    /**
     * Endpoint for treating a claim based on the provided information.
     *
     * @param claimForPutDto The data transfer object containing information for treating the claim.
     * @param result         BindingResult that holds validation errors, if any.
     * @return A ClaimForGetDto object representing the details of the treated claim.
     * @throws IOException if an IO exception occurs during the claim treatment.
     * @apiNote This endpoint allows users to treat a claim based on the information provided in the
     * ClaimForPutDto. The result of the treatment is returned as a ClaimForGetDto object
     * containing information about the treated claim. Validation errors are included in the
     * BindingResult. If an IO exception occurs during the treatment process, it is propagated.
     * @see ClaimForPutDto
     * @see ClaimForGetDto
     * @see BindingResult
     */
    @PostMapping("/treat")
    public ClaimForGetDto treatClaim(@RequestBody ClaimForPutDto claimForPutDto,
                                     BindingResult result) throws IOException {

        log.info("Start treating claim");
        return claimService.treatClaim(claimForPutDto, result);
    }


    /**
     * Endpoint for retrieving details of a claim identified by the provided ID.
     *
     * @param id The unique identifier of the claim to be retrieved.
     * @return A ClaimForGetDto object representing the details of the requested claim.
     * @apiNote This endpoint allows users to retrieve details of a claim identified by the provided ID.
     * The result is returned as a ClaimForGetDto object containing information about the claim.
     * If the claim ID is invalid, an exception is thrown.
     * @see ClaimForGetDto
     */
    @GetMapping("{id}")
    public ClaimForGetDto getClaim(@PathVariable String id) {
        return claimService.getClaim(id);
    }


    /**
     * Endpoint for changing the status of a claim identified by the provided ID.
     *
     * @param id The unique identifier of the claim for which the status will be changed.
     * @apiNote This endpoint allows users to change the status of a claim identified by the provided ID.
     * The result is returned as a ResponseEntity with a message indicating the success of the
     * status change. If the claim ID is invalid, an exception is thrown.
     * @see ResponseEntity
     * @see MessageDto
     */
    @PostMapping("status/{id}")
    public void changeStatusClaim(@PathVariable String id) {
        claimService.changeStatusClaim(id);
    }




    /**
     * Retrieves a paginated list of claims based on specified criteria.
     *
     * @param ref          Reference identifier for filtering claims. (Optional)
     * @param status       Claim status for filtering claims. (Optional)
     * @param creationDate Date of claim creation for filtering claims. (Optional)
     * @param page         Page number for pagination. (Optional)
     * @param pageSize     Number of items per page for pagination. (Optional)
     * @param sort         Sorting criteria for the result set. (Optional)
     * @return PaginatedResponse containing ClaimForGetDto objects that match the specified criteria.
     * @apiNote This endpoint allows users to retrieve a paginated list of claims based on various criteria.
     * The parameters are optional, and when not provided, the method retrieves all available claims.
     * The result is returned in a PaginatedResponse containing ClaimForGetDto objects.
     * Sorting can be applied by providing a Sort object.
     * @see PaginatedResponse
     * @see ClaimForGetDto
     * @see Sort
     */
    @GetMapping
    public PaginatedResponse<ClaimForGetDto> getClaims(@RequestParam(name = "ref", required = false) String ref,
                                                       @RequestParam(name = "status", required = false) ClaimStatusEnum status,
                                                       @RequestParam(name = "userType", required = false) UserTypeEnum userType,
                                                       @RequestParam(name = "creationDate", required = false) String creationDate,
                                                       @RequestParam(name = "page", required = false) Integer page,
                                                       @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                                       Sort sort) {
        log.info("Start retrieving claims by criteria");
        return claimService.getAllClaims(userType,ref, status, creationDate, page, pageSize, sort);
    }

}
