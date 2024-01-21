package com.wevioo.pi.rest.resources;

import com.wevioo.pi.domain.entity.referential.IdentificationType;
import com.wevioo.pi.rest.dto.AgencyDto;
import com.wevioo.pi.rest.dto.BankDto;
import com.wevioo.pi.rest.dto.CountryDto;
import com.wevioo.pi.rest.dto.CurrencyDto;
import com.wevioo.pi.rest.dto.NatClassDto;
import com.wevioo.pi.rest.dto.NatGroupDto;
import com.wevioo.pi.rest.dto.NatSectionDto;
import com.wevioo.pi.rest.dto.NatSubSectionDto;
import com.wevioo.pi.rest.dto.DelegationDto;
import com.wevioo.pi.rest.dto.GovernorateDto;
import com.wevioo.pi.rest.dto.LocationDto;
import com.wevioo.pi.rest.dto.ZipCodeDto;
import com.wevioo.pi.rest.dto.request.CodificationForPostDto;
import com.wevioo.pi.rest.dto.request.ExistenceIdentificationTypeDto;
import com.wevioo.pi.rest.dto.response.CrePersonForGetDto;
import com.wevioo.pi.service.CodificationService;
import com.wevioo.pi.service.IdentificationTypeService;
import com.wevioo.pi.service.ReferentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/referential", produces = "application/json;charset=UTF-8")
@Validated
public class ReferentialController {

    @Autowired
    IdentificationTypeService identificationTypeService;


    /**
     * Injected bean {@link ReferentialService}
     */
    @Autowired
    private ReferentialService referentialService;
    /**
     * Injected bean {@link CodificationService}
     */
    @Autowired
    private CodificationService codificationService;

    /**
     * Retrieves a list of all banks via HTTP GET request.
     *
     * @return List of BankDto objects representing all available banks
     */
    @GetMapping("/banks")
    public List<BankDto> getAllBanks() {
        return referentialService.getAllBanks();
    }

    /**
     * Retrieves a list of agencies associated with a specific bank via HTTP GET request.
     *
     * @param bankId The unique identifier of the bank
     * @return List of AgencyDto objects associated with the specified bankId
     */
    @GetMapping("/bank/{bankId}/agencies")
    public List<AgencyDto> getAllAgencies(@PathVariable("bankId") String bankId) {
        return referentialService.getAgenciesByBankId(bankId);
    }
    /**
     * Retrieves a list of countries.
     *
     * @return List of CountryDto
     */
    @GetMapping("/countries")
    public List<CountryDto> getAllCountries() {
        return referentialService.getAllCountries();
    }
    /**
     * Retrieves all NatSections.
     *
     * @return List of NatSectionDto
     */
    @GetMapping("/natSections")
    public List<NatSectionDto>  findAllNatSection(){return referentialService.findAll();}

    /**
     * Retrieves all NatSubSections by given sectionId.
     *
     * @param sectionId The section ID to filter NatSubSections
     * @return List of NatSubSectionDto
     */
    @GetMapping("/natSubSections")
    public  List<NatSubSectionDto> findAllNatSubSectionBySectionId(@RequestParam(name = "sectionId") String sectionId) {
        return  referentialService.findAllBySectionId(sectionId);
    }

    /**
     * Retrieves all NatGroups by given sectionId and subSectionId.
     *
     * @param sectionId     The section ID to filter NatGroups
     * @param subSectionId  The sub-section ID to filter NatGroups
     * @return List of NatGroupDto
     */
    @GetMapping("/natGroups")
    public  List<NatGroupDto> findAllNatSubSectionBySectionIdAndSubSectionId(
            @RequestParam(name = "sectionId")String sectionId,
            @RequestParam(name = "subSectionId")String subSectionId){
        return referentialService.findAllBySectionIdAndSubSectionId(sectionId ,subSectionId);
    }

    /**
     * Retrieves all NatClasses by given sectionId, subSectionId, and groupId.
     *
     * @param sectionId     The section ID to filter NatClasses
     * @param subSectionId  The sub-section ID to filter NatClasses
     * @param groupId       The group ID to filter NatClasses
     * @return List of NatClassDto
     */
    @GetMapping("/natClass")
    public  List<NatClassDto> findAllNatSubSectionBySectionIdAndSubSectionIdAndGroupId(
            @RequestParam(name = "sectionId") String sectionId,
            @RequestParam(name = "subSectionId") String subSectionId,
            @RequestParam(name = "groupId") String groupId){
        return referentialService. findAllBySectionIdAndSubSectionIdAndGroupId(sectionId ,subSectionId , groupId);
    }




    /**
     * Get List of IdentificationType saved in data base
     *
     * @return List of IdentificationType
     */

    @GetMapping("/identificationTypes")
    public List<IdentificationType> getAll() {
        return identificationTypeService.findAllIdentifier();
    }

    /**
     * @param requestMoralPerson
     * @param result
     * @return add a moral personS
     */
    @PostMapping("/codifications")
    public
    CodificationForPostDto saveMoralPerson(@RequestBody CodificationForPostDto requestMoralPerson,
                                           BindingResult result) {
        return codificationService.saveCodification(requestMoralPerson, result);
    }

    @GetMapping("/governorates")
    public List<GovernorateDto> getAllGovernorates() {
        return referentialService.getAllGovernorates();
    }

    @GetMapping("/governorate/{governorateId}/delegations")
    public List<DelegationDto> getAllDelegationsByGovernorate(@PathVariable("governorateId") String governorateId) {
        return referentialService.getAllDelegationsByGovernorateId(governorateId);
    }

    @GetMapping("/governorate/delegation/locations")
    public List<LocationDto> getAllLocationsByGovernorateAndDelegation(@RequestParam("governorateId") String governorateId,
                                                          @RequestParam("delegationId") String delegationId) {
        return referentialService.getLocationsByGovernorateIdAndDelegationId(governorateId,delegationId);
    }

    @GetMapping("/governorate/delegation/location/zipCodes")
    public List<ZipCodeDto> getAllLocationsByGovernorateAndDelegation(@RequestParam("governorateId") String governorateId,
                                                                      @RequestParam("delegationId") String delegationId,
                                                                      @RequestParam("locationId") String locationId) {
        return referentialService.getZipCodesByGovernorateIdAndDelegationIdAndLocationId(governorateId,delegationId,locationId);
    }

    @GetMapping("/currencies")
    public List<CurrencyDto> getAllCurrencies() {
        return referentialService.getAllCurrencies();
    }

    /**
     * @param existenceIdentificationTypeDto
     * @param result
     * @return verify existence person based on identification Type ( Physic Person , Moral Person ) ==> ( lastName , first Name , SocialReason )
     */
    @PostMapping("/organismCheck")
    public ResponseEntity<CrePersonForGetDto> verifyExistIdentificationType(@RequestBody ExistenceIdentificationTypeDto existenceIdentificationTypeDto , BindingResult result){
        return ResponseEntity.ok(referentialService.verifyExistIdentificationType(existenceIdentificationTypeDto , result));
    }

}
