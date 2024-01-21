package com.wevioo.pi.rest.resources;

import com.itextpdf.text.DocumentException;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.FileTypeEnum;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.exception.EmailSendingException;

import com.wevioo.pi.rest.dto.InvestorForGetDto;
import com.wevioo.pi.rest.dto.InvestorForGetListDto;
import com.wevioo.pi.rest.dto.InvestorForPostDto;
import com.wevioo.pi.rest.dto.InvestorForSelfPutDto;
import com.wevioo.pi.rest.dto.InvestorGetForPutDto;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;

import com.wevioo.pi.rest.dto.response.EmailDto;
import com.wevioo.pi.rest.dto.response.InvestorCheckForGetProjection;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.GenericExcelPdfService;
import com.wevioo.pi.service.InvestorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.wevioo.pi.domain.enumeration.IdentificationTypeEnum;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/investors", produces = "application/json;charset=UTF-8")
public class InvestorController {

    /**
     * Injected bean {@link com.wevioo.pi.service.InvestorService}
     */
    @Autowired
    InvestorService investorService;


    @Autowired
    GenericExcelPdfService genericExcelPdfService;
    /**
     * GET mapping to retrieve the current investor's information.
     *
     * @return InvestorForGetDto representing the current investor's information.
     */
    @GetMapping("/current")
    public InvestorForGetDto getInvestor() {
        return investorService.getInvestor();
    }

    /**
     * GET mapping to retrieve a paginated list of investors based on optional parameters.
     *
     * @param page         The page number for pagination.
     * @param size         The size of each page for pagination.
     * @param sort         Sorting criteria for the list.
     * @param investorType The type of investor (e.g., individual, organization).
     * @param systemId     The system identifier associated with the investor.
     * @param email        The email address associated with the investor.
     * @return PaginatedResponse<InvestorForGetListDto> representing the paginated list of investors.
     */
    @GetMapping()
    public PaginatedResponse<InvestorForGetListDto> getInvestors(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            Sort sort,
            @RequestParam(name = "investorType", required = false) List<PersonTypeEnum> investorType,
            @RequestParam(name = "systemId", required = false) String systemId,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "investor", required = false) String investor ,
            @RequestParam(name = "identificationType", required = false) List<IdentificationTypeEnum> identificationType ,
            @RequestParam(name = "identificationValue", required = false) String identificationValue ) {
        log.info("start get investors");
        return investorService.getInvestorsList(page, size, sort, investorType, systemId, email ,investor , identificationValue ,identificationType);
    }

    /**
     * PUT mapping to update the information of the current investor.
     *
     * @param investorForSelfPutDto The DTO containing updated information for the investor.
     * @param result                BindingResult for validation and error handling.
     * @return InvestorForGetDto representing the updated information of the investor.
     */
    @PostMapping("/update")
    public InvestorForGetDto updateInvestor(@RequestBody @Valid InvestorForSelfPutDto investorForSelfPutDto,
                                     BindingResult result) {
        return investorService.updateInvestor(investorForSelfPutDto, result);
    }

    /**
     * GET mapping to retrieve an investor's information for BCT by their unique identifier.
     *
     * @param id The unique identifier of the investor.
     * @return InvestorGetForPutDto representing the investor's information for BCT.
     */
    @GetMapping("/{id}/bct")
    public InvestorGetForPutDto getInvestorForBct(@PathVariable String id) {
        log.info("start get investor by id");
        return investorService.getInvestorById(id);
    }


    /**
     * PATCH mapping to disable an investor identified by the provided ID.
     *
     * @param id The unique identifier of the investor to be disabled.
     * @return InvestorGetForPutDto representing the updated state of the disabled investor.
     */
    @PostMapping("/disable/{id}")
    public InvestorGetForPutDto disableInvestor(@PathVariable String id) {
        return investorService.disableInvestor(id);
    }

    /**
     * @param investorForPostDto
     * @param powerOfAttorney    MultipartFile
     * @return InvestorForGetDto
     * @throws IOException custom exception
     */
    @PostMapping(consumes = "multipart/form-data")
    public InvestorForGetDto registerInvestor(@RequestPart("investorForPostDto") String investorForPostDto,
                                       @RequestPart(value = "powerOfAttorney", required = false)
                                       MultipartFile powerOfAttorney) throws IOException {
        try {
            return investorService.saveInvestor(investorForPostDto, powerOfAttorney);
        } catch (MessagingException e) {
            throw new EmailSendingException(ApplicationConstants.ERROR_EMAIL_SEND  , "Error sending Email");
        }
    }


    /**
     *
     * @param investorForPostDto
     * @return InvestorForGetDto
     */
    @PostMapping("/create")
    public InvestorForGetDto createInvestor(@RequestBody InvestorForPostDto investorForPostDto,
                                                              BindingResult result) {
        try {
            return investorService.createInvestor(investorForPostDto,result);
        } catch (MessagingException e) {
            throw new EmailSendingException(ApplicationConstants.ERROR_EMAIL_SEND  , "Error sending Email");
        }
    }

    /**
     * Verify if email exists or not
     *
     * @param email  email to verify
     * @param result list of errors
     * @return 200/ 400
     */
    @PostMapping("/email/exists")
    public void verifyEmail(@RequestBody EmailDto email, BindingResult result) {
        investorService.verifyMail(email.getEmail(), result);
    }


    /**
     * Check the existence of Investor based on its Id
     * @param id
     * @return InvestorCheckForGetProjection ( firstName , LastName , Fund , SocialReason )
     */
    @GetMapping("/check")
    public InvestorCheckForGetProjection verifyInvestor(@RequestParam(required = true) String id){
        return investorService.verifyInvestor(id);
    }

    /**
     *  export filtered Investor table list based on its Type PDF or EXCEL
     */
    @GetMapping ("/export/{type}")
    public DownloadDocumentDto createExcelSheet(
                                         @PathVariable(name = "type") FileTypeEnum type,
                                         @RequestParam(name = "investorType", required = false) List<PersonTypeEnum> investorType,
                                         @RequestParam(name = "systemId", required = false) String systemId,
                                         @RequestParam(name = "email", required = false) String email,
                                         @RequestParam(name = "investor", required = false) String investor ,
                                         @RequestParam(name = "identificationType", required = false) List<IdentificationTypeEnum> identificationType ,
                                         @RequestParam(name = "identificationValue", required = false) String identificationValue ) throws IOException, DocumentException {
        if(ApplicationConstants.EXCEL.equals(FileTypeEnum.valueOf(type.name()).name())){
            return  genericExcelPdfService.saveExcelSheet(investorService.getInvestorsListExport(investorType, systemId, email ,investor , identificationValue ,identificationType).getContent());
        }
        else{
            return genericExcelPdfService.savePdfTable(investorService.getInvestorsListExport(investorType, systemId, email ,investor , identificationValue ,identificationType).getContent());
        }
    }

}
