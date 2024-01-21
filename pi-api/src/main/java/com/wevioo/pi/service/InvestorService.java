package com.wevioo.pi.service;

import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.rest.dto.InvestorForGetDto;
import com.wevioo.pi.rest.dto.InvestorForGetListDto;
import com.wevioo.pi.rest.dto.InvestorForPostDto;
import com.wevioo.pi.rest.dto.InvestorForSelfPutDto;
import com.wevioo.pi.rest.dto.InvestorGetForPutDto;
import com.wevioo.pi.rest.dto.response.InvestorCheckForGetProjection;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import com.wevioo.pi.domain.enumeration.IdentificationTypeEnum;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface InvestorService {

    /**
     * Checks the existence of an investor with the specified email.
     *
     * @param email The email address to check.
     * @return {@code true} if an investor with the specified email exists, {@code false} otherwise.
     */
    boolean investorExistByEmail(String email);


    /**
     * Register an investor with the provided data, including an optional representative and power of attorney.
     *
     * @param paramInvestorForPostDto The JSON string representing the data for creating the investor.
     * @param powerOfAttorney         The power of attorney file attached to the investor (optional).
     * @return An {@link InvestorForGetDto} representing the saved investor.
     * @throws IOException            If there is an issue reading the JSON data.
     * @throws DataNotFoundException  If required reference data is not found.
     */
    InvestorForGetDto saveInvestor(String paramInvestorForPostDto, MultipartFile powerOfAttorney)
            throws IOException, MessagingException;

    /**
     * Banker Creates a new investor based on the provided data.
     *
     * @param investorForPostDto The DTO containing the data for creating the investor.
     * @param result             The binding result for validation.
     * @return An {@link InvestorForGetDto} representing the created investor.
     * @throws DataNotFoundException If the authenticated user or required reference data is not found.
     */
    InvestorForGetDto createInvestor(InvestorForPostDto investorForPostDto, BindingResult result) throws MessagingException;

    /**
     * Retrieves the details of the authenticated investor.
     *
     * @return An {@link InvestorForGetDto} containing the details of the authenticated investor.
     * @throws DataNotFoundException If the authenticated investor is not found.
     */
    InvestorForGetDto getInvestor() throws DataNotFoundException;

    /**
     * Retrieves a paginated list of investors based on specified criteria.
     *
     * @param page         The page number (1-indexed) of the result set to retrieve.
     * @param size         The number of items per page in the result set.
     * @param sort         The sorting criteria for the result set.
     * @param investorType The type of investor to filter by (e.g., INDIVIDUAL, LEGAL_ENTITY).
     * @param systemId     The system identifier to filter by.
     * @param email        The email address to filter by.
     * @return A {@link PaginatedResponse} containing a list of {@link InvestorForGetListDto} objects.
     * @throws DataNotFoundException   If the authenticated user is not found.
     * @throws UnauthorizedException   If the authenticated user does not have the required authorization.
     */
    PaginatedResponse<InvestorForGetListDto> getInvestorsList(Integer page, Integer size, Sort sort,
                                                              List<PersonTypeEnum> investorType , String systemId, String email , String investor, String identificaionValue , List<IdentificationTypeEnum> identificationType);
    /**
     * Retrieves a list of investors based on specified criteria.
     * @param investorType The type of investor to filter by (e.g., INDIVIDUAL, LEGAL_ENTITY).
     * @param systemId     The system identifier to filter by.
     * @param email        The email address to filter by.
     * @return A {@link PaginatedResponse} containing a list of {@link InvestorForGetListDto} objects.
     * @throws DataNotFoundException   If the authenticated user is not found.
     * @throws UnauthorizedException   If the authenticated user does not have the required authorization.
     */
    PaginatedResponse<InvestorForGetListDto> getInvestorsListExport(List<PersonTypeEnum> investorType , String systemId, String email , String investor, String identificaionValue , List<IdentificationTypeEnum> identificationType);
    /**
     * Retrieves an investor's details by their unique identifier.
     *
     * @param id The unique identifier of the investor to retrieve.
     * @return An instance of {@link InvestorGetForPutDto} representing the investor details.
     * @throws DataNotFoundException If no investor is found with the specified ID.
     */
    InvestorGetForPutDto getInvestorById(String id) throws DataNotFoundException;

    /**
     * Disables an investor based on the provided ID.
     *
     * @param id     The ID of the investor to be disabled.
     * @return An instance of {@link InvestorGetForPutDto} representing the disabled investor.
     * @throws DataNotFoundException    If no investor is found with the specified ID.
     * @throws UnauthorizedException If the user is not authorized to perform the operation.
     */
    InvestorGetForPutDto disableInvestor(String id) throws DataNotFoundException, UnauthorizedException;

    /**
     * Updates the details of the currently authenticated investor.
     *
     * @param investorForSelfPutDto The data to update for the investor, provided as a DTO.
     * @param result                The binding result to capture validation errors, if any.
     * @return An instance of {@link InvestorForGetDto} representing the updated investor details.
     * @throws DataNotFoundException    If the authenticated investor is not found.
     */
    InvestorForGetDto updateInvestor(InvestorForSelfPutDto investorForSelfPutDto, BindingResult result) throws DataNotFoundException;

    /**
     * Verifies the nationality compatibility for a specific investor type.
     *
     * This method checks if a person with the specified investor type and nationality is compatible.
     * If the nationality is found to be incompatible with the investor type.
     *
     *
     * @param investorType The type of the person, specified by {@link PersonTypeEnum}.
     * @param nationality The nationality of the person.
     * @return {@code true} if the specified nationality is compatible with the investor type.
     */
    boolean verifyNationality(PersonTypeEnum investorType, String nationality);


    /**
     * @param mail verify the existence of an email
     */
    void verifyMail (String mail , BindingResult result ) ;

    /**
     * Verify the investor to return (LastName , FirstName , socialReason ... )
     * @param id
     * @return
     */
    InvestorCheckForGetProjection verifyInvestor (String id);

}
