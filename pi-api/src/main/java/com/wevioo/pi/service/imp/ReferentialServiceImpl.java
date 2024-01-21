package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.referential.*;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.*;
import com.wevioo.pi.repository.AgencyRepository;
import com.wevioo.pi.repository.BankRepository;
import com.wevioo.pi.repository.CountryRepository;
import com.wevioo.pi.repository.CurrencyRepository;
import com.wevioo.pi.repository.DelegationRepository;
import com.wevioo.pi.repository.GovernorateRepository;
import com.wevioo.pi.repository.LocationRepository;
import com.wevioo.pi.repository.CreMoralPersonRepository;
import com.wevioo.pi.repository.CrePhysicPersonRepository;
import com.wevioo.pi.repository.NatClassRepository;
import com.wevioo.pi.repository.NatGroupRepository;
import com.wevioo.pi.repository.NatSectionRepository;
import com.wevioo.pi.repository.NatSubSectionRepository;
import com.wevioo.pi.repository.SpecificReferentialRepository;
import com.wevioo.pi.repository.ZipCodeRepository;
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
import com.wevioo.pi.rest.dto.response.CrePersonForGetProjection;
import com.wevioo.pi.service.IdentificationTypeService;
import com.wevioo.pi.service.ReferentialService;
import com.wevioo.pi.validation.IdentificationTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ReferentialServiceImpl implements ReferentialService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ReferentialMapper referentialMapper;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private GovernorateRepository governorateRepository;
    @Autowired
    private DelegationRepository delegationRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ZipCodeRepository zipCodeRepository;
    @Autowired
    private SpecificReferentialRepository specificReferentialRepository;
    @Autowired
    BankMapper bankMapper;
    @Autowired
    AgencyMapper agencyMapper;
    @Autowired
    CreMoralPersonRepository creMoralPersonRepository;
    @Autowired
    CrePhysicPersonRepository crePhysicPersonRepository;
    @Autowired
    NatGroupRepository natGroupRepository;
    @Autowired
    NatSectionRepository natSectionRepository;
    @Autowired
    NatSubSectionRepository natSubSectionRepository;
    @Autowired
    NatClassRepository natClassRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    NatSubSectionMapper natSubSectionMapper;
    @Autowired
    NatGroupMapper natGroupMapper;
    @Autowired
    NatSectionMapper natSectionMapper;
    @Autowired
    NatClassMapper natClassMapper;
    @Autowired
    IdentificationTypeValidator identificationTypeValidator;

    @Autowired
    IdentificationTypeService identificationTypeService;

    @Override
    public List<CountryDto> getAllCountries() {
        log.info("get All Countries");
        return referentialMapper.toCountryDtoList(countryRepository.findByOrderByLabelAsc());
    }

    @Override
    public List<Country> findByIdIn(List<String> ids) {
        return countryRepository.findByIdIn(ids);
    }

    @Override
    public List<SpecificReferential> findSpecificReferentialByIdIn(List<Long> ids) {
        return specificReferentialRepository. findAllByIds(ids);
    }

    /**
     * Retrieves a list of AgencyDto objects based on the provided bankId.
     *
     * @param bankId The unique identifier of the bank
     * @return List of AgencyDto objects associated with the specified bankId
     */
    @Override
    public List<AgencyDto> getAgenciesByBankId(String bankId) {
        return agencyMapper.toDto(agencyRepository.findByBankIdOrderByLabelAsc(bankId))  ;
    }
    /**
     * Retrieves a list of all banks and converts them to BankDto objects.
     *
     * @return List of BankDto objects representing all available banks
     */
    @Override
    public List<BankDto> getAllBanks() {
        return  bankMapper.toDto(bankRepository.findAll()) ;
    }

    /**
     * Retrieves all the natural sections.
     *
     * @return A list of {@code NatSectionDto} containing all the natural sections.
     */
    @Override
    public List<NatSectionDto> findAll() {
        return  natSectionMapper.toDto(
                 natSectionRepository.findAll()
        );
    }

    /**
     * Retrieves all the natural sub-sections based on the provided section ID.
     *
     * @param sectionId The ID of the section.
     * @return A list of {@code NatSubSectionDto} containing all the natural sub-sections for the given section ID.
     */
    @Override
    public List<NatSubSectionDto> findAllBySectionId(String sectionId) {
        return  natSubSectionMapper.toDto(
                natSubSectionRepository.findAllByNatSectionId(sectionId)
        );
    }

    /**
     * Retrieves all the natural groups based on the provided section ID and sub-section ID.
     *
     * @param sectionId    The ID of the section.
     * @param subSectionId The ID of the sub-section.
     * @return A list of {@code NatGroupDto} containing all the natural groups for the given section ID and sub-section ID.
     */
    @Override
    public List<NatGroupDto> findAllBySectionIdAndSubSectionId(String sectionId, String subSectionId) {
        return  natGroupMapper.toDto(
                natGroupRepository.findAllByNatSectionIdAndNatSubSectionId(
                        sectionId ,  subSectionId
                )
        );
    }

    /**
     * Retrieves all the natural groups based on the provided section ID, sub-section ID, and group ID.
     *
     * @param sectionId    The ID of the section.
     * @param subSectionId The ID of the sub-section.
     * @param groupId      The ID of the group.
     * @return A list of {@code NatGroupDto} containing all the natural groups for the given section ID, sub-section ID, and group ID.
     */
    @Override
    public List<NatClassDto> findAllBySectionIdAndSubSectionIdAndGroupId(String sectionId, String subSectionId, String groupId) {
        return  natClassMapper.toDto(
                natClassRepository.findAllByNatSectionIdAndNatSubSectionIdAndGroupId(sectionId , subSectionId , groupId)
        );
    }


    @Override
    public boolean existUniqueIdentification(String uniqueIdentification) {
        return creMoralPersonRepository.existsByUniqueIdentification(uniqueIdentification);
    }




    @Override
    public CrePersonForGetDto findPhysicPersonByCin(String cin) {
        CrePersonForGetDto  crePersonForGetDto = new CrePersonForGetDto();
        Optional <CrePersonForGetProjection> crePhysicPersonOptional = crePhysicPersonRepository.findPhysicPersonByCin(cin , ApplicationConstants.TYPDOC_CIN);
        if (crePhysicPersonOptional.isPresent()){
            crePersonForGetDto.setFirstName(crePhysicPersonOptional.get().getLprenom());
            crePersonForGetDto.setLastName(crePhysicPersonOptional.get().getLnom());
            return crePersonForGetDto;
        }
        throw new DataNotFoundException(ApplicationConstants.PHYSIC_PERSON_NOT_FOUND , "No Physic Person found with value : " + cin);
    }

    @Override
    public CrePersonForGetDto findMoralPersonByTaxIdentification(String taxIdentification) {
        CrePersonForGetDto  crePersonForGetDto = new CrePersonForGetDto();
        Optional <CreMoralPerson> creMoralPersonOptional = creMoralPersonRepository.findCreMoralPersonByUniqueIdentification(taxIdentification);
        if (creMoralPersonOptional.isPresent()){
            crePersonForGetDto.setSocialReason(creMoralPersonOptional.get().getSocialReason());
            return crePersonForGetDto;
        }

        throw new DataNotFoundException(ApplicationConstants.MORAL_PERSON_NOT_FOUND , "No Moral Person found with value : " + taxIdentification);
    }


    @Override
    public List<GovernorateDto> getAllGovernorates() {
        return referentialMapper.toGovernorateDtoList(governorateRepository.findAll());
    }

    @Override
    public List<DelegationDto> getAllDelegationsByGovernorateId(String governorateId) {
        List<DelegationDto> delegationDtoList = new ArrayList<>();
        List<Delegation> delegationList = delegationRepository.findByIdGovernorateId(governorateId);
        if (!delegationList.isEmpty()) {
            for (Delegation delegation : delegationList) {
                DelegationDto delegationDto = new DelegationDto();
                delegationDto.setLabel(delegation.getLabel());
                delegationDto.setId(delegation.getId().getDelegationId());
                delegationDtoList.add(delegationDto);
            }
        }
        return delegationDtoList;
    }

    @Override
    public List<LocationDto> getLocationsByGovernorateIdAndDelegationId(String governorateId, String delegationId) {
            List<LocationDto> locationDtoList = new ArrayList<>();
            List<Location> locationList = locationRepository.findByIdGovernorateIdAndIdDelegationId(governorateId, delegationId);
            if (!locationList.isEmpty()) {
                for (Location location : locationList) {
                    LocationDto locationDto = new LocationDto();
                    locationDto.setLabel(location.getLabel());
                    locationDto.setId(location.getId().getLocaId());
                    locationDtoList.add(locationDto);
                }
            }

            return locationDtoList;
    }

    @Override
    public List<ZipCodeDto> getZipCodesByGovernorateIdAndDelegationIdAndLocationId(String governorateId, String delegationId, String locaId) {
        List<ZipCodeDto> zipCodeDtoList = new ArrayList<>();
        List<ZipCode> zipCodeList = zipCodeRepository.findByIdGovernorateIdAndIdDelegationIdAndIdLocaId(governorateId, delegationId,locaId);
        if (!zipCodeList.isEmpty()) {
            for (ZipCode zipCode : zipCodeList) {
                ZipCodeDto zipCodeDto = new ZipCodeDto();
                zipCodeDto.setLabel(zipCode.getLabel());
                zipCodeDto.setId(zipCode.getId().getZipCodeId());
                zipCodeDtoList.add(zipCodeDto);
            }
        }
        return zipCodeDtoList;
    }

    @Override
    public List<CurrencyDto> getAllCurrencies() {
        return referentialMapper.toCurrencyDtoList(currencyRepository.findByOrderByLabelAsc());
    }
    @Override
    public
    CrePersonForGetDto verifyExistIdentificationType(ExistenceIdentificationTypeDto existenceIdentificationTypeDto, BindingResult result) {

        // validate identification Type Existence in Identification  Type reference
        IdentificationType identificationTypeExist = identificationTypeService
                .findByType(existenceIdentificationTypeDto.getIdentificationType());

        // validation of Identification Type
        identificationTypeValidator.validateIdentificationType(identificationTypeExist , existenceIdentificationTypeDto.getIdentificationType(), existenceIdentificationTypeDto.getUniqueIdentifier(), result);
        if (result.hasErrors()) {
            throw new BadRequestException("400", result);
        }
        //  existence CIN in BCT reference and return Nom , Prenom
        if (ApplicationConstants.CIN.equals(existenceIdentificationTypeDto.getIdentificationType())) {
            return findPhysicPersonByCin(existenceIdentificationTypeDto.getUniqueIdentifier());
            //  existence unique identification in BCT reference and return Social Reason
        } else  {
            return findMoralPersonByTaxIdentification(existenceIdentificationTypeDto.getUniqueIdentifier());
        }
    }
}
