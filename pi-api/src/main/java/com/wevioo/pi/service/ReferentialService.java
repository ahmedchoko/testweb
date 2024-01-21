package com.wevioo.pi.service;

import com.wevioo.pi.domain.entity.referential.Country;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.rest.dto.AgencyDto;
import com.wevioo.pi.rest.dto.BankDto;
import com.wevioo.pi.rest.dto.CountryDto;
import com.wevioo.pi.rest.dto.CurrencyDto;
import com.wevioo.pi.rest.dto.DelegationDto;
import com.wevioo.pi.rest.dto.GovernorateDto;
import com.wevioo.pi.rest.dto.LocationDto;
import com.wevioo.pi.rest.dto.ZipCodeDto;
import com.wevioo.pi.rest.dto.NatClassDto;
import com.wevioo.pi.rest.dto.NatGroupDto;
import com.wevioo.pi.rest.dto.NatSectionDto;
import com.wevioo.pi.rest.dto.NatSubSectionDto;
import com.wevioo.pi.rest.dto.request.ExistenceIdentificationTypeDto;
import com.wevioo.pi.rest.dto.response.CrePersonForGetDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ReferentialService {

    /**
     * Load all countries
     *
     * @return list of CountryDto
     */
    List<CountryDto> getAllCountries();
    /**
     * Retrieves a list of countries by their IDs.
     *
     * @param ids The list of country IDs to retrieve
     * @return List of Country objects corresponding to the provided IDs
     */
    List<Country> findByIdIn(List<String> ids);

    /**
     * Retrieves a list of SpecificReferential by their IDs.
     *
     * @param ids The list of specific referential IDs to retrieve
     * @return List of SpecificReferential objects corresponding to the provided IDs
     */
    List<SpecificReferential> findSpecificReferentialByIdIn(List<Long> ids);
    /**
     * Retrieves a list of agencies associated with a specific bank.
     *
     * @param bankId The unique identifier of the bank
     * @return List of AgencyDto objects associated with the specified bankId
     */
    List<AgencyDto> getAgenciesByBankId(String bankId);

    /**
     * Retrieves a list of all banks.
     *
     * @return List of BankDto objects representing all available banks
     */
    List<BankDto> getAllBanks();

    /**
     * Retrieves all the natural sections.
     *
     * @return A list of {@code NatSectionDto} containing all the natural sections.
     */
    List<NatSectionDto> findAll();
    /**
     * Retrieves all the natural sub-sections based on the provided section ID.
     *
     * @param sectionId The ID of the section.
     * @return A list of {@code NatSubSectionDto} containing all the natural sub-sections for the given section ID.
     */
    List<NatSubSectionDto> findAllBySectionId(String sectionId);
    /**
     * Retrieves all the natural groups based on the provided section ID and sub-section ID.
     *
     * @param sectionId    The ID of the section.
     * @param subSectionId The ID of the sub-section.
     * @return A list of {@code NatGroupDto} containing all the natural groups for the given section ID and sub-section ID.
     */
    List<NatGroupDto> findAllBySectionIdAndSubSectionId(String sectionId , String subSectionId);
    /**
     * Retrieves all the natural groups based on the provided section ID, sub-section ID, and group ID.
     *
     * @param sectionId    The ID of the section.
     * @param subSectionId The ID of the sub-section.
     * @param groupId      The ID of the group.
     * @return A list of {@code NatGroupDto} containing all the natural groups for the given section ID, sub-section ID, and group ID.
     */
    List<NatClassDto> findAllBySectionIdAndSubSectionIdAndGroupId(String sectionId , String subSectionId , String groupId);


    /**
     * @param uniqueIdentification
     * @return if unique Identification exists
     */
    boolean existUniqueIdentification(String uniqueIdentification);


    /**
     * @param cin
     * @return FirstName and LastName if Cin exists
     */
    CrePersonForGetDto findPhysicPersonByCin(String cin);
    /**
     * @param taxIdentification
     * @return Social Reason if taxIdentification exists
     */
    CrePersonForGetDto findMoralPersonByTaxIdentification(String taxIdentification);

    /**
     * Retrieves all governorates.
     *
     * @return A list of GovernorateDto containing all governorates.
     */

    List<GovernorateDto> getAllGovernorates();
    /**
     * Retrieves all delegations by Governorate ID.
     *
     * @param governorateId The unique identifier of the Governorate.
     * @return A list of DelegationDto containing all delegations for the given Governorate ID.
     */
    List<DelegationDto> getAllDelegationsByGovernorateId(String governorateId);
    /**
     * Retrieves locations by Governorate ID and Delegation ID.
     *
     * @param governorateId The unique identifier of the Governorate.
     * @param delegationId  The unique identifier of the Delegation.
     * @return A list of LocationDto containing locations for the given Governorate ID and Delegation ID.
     */
    List<LocationDto> getLocationsByGovernorateIdAndDelegationId(String governorateId, String delegationId);
    /**
     * Retrieves zip codes by Governorate ID, Delegation ID, and Location ID.
     *
     * @param governorateId The unique identifier of the Governorate.
     * @param delegationId  The unique identifier of the Delegation.
     * @param locaId        The unique identifier of the Location.
     * @return A list of ZipCodeDto containing zip codes for the given Governorate ID, Delegation ID, and Location ID.
     */
    List<ZipCodeDto> getZipCodesByGovernorateIdAndDelegationIdAndLocationId(String governorateId, String delegationId,
                                                                            String locaId);
    /**
     * Retrieves all currencies.
     *
     * @return A list of CurrencyDto containing all currencies.
     */
    List<CurrencyDto> getAllCurrencies();

    /**
     * @param existenceIdentificationTypeDto
     * @param result
     * @return
     */
    CrePersonForGetDto verifyExistIdentificationType (ExistenceIdentificationTypeDto existenceIdentificationTypeDto , BindingResult result);


}
