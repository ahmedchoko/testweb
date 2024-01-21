package com.wevioo.pi.rest.resources;


import com.itextpdf.text.DocumentException;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.FileTypeEnum;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.exception.DocumentExportException;
import com.wevioo.pi.exception.EmailSendingException;
import com.wevioo.pi.rest.dto.BankerForGetAllDto;
import com.wevioo.pi.rest.dto.BankerForGetDto;
import com.wevioo.pi.rest.dto.BankerForPostDto;
import com.wevioo.pi.rest.dto.BankerForPutDto;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;
import com.wevioo.pi.rest.dto.response.BankerLightForGetDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.GenericExcelPdfService;
import com.wevioo.pi.service.IBankerService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * Rest controller for Banker
 */
@Slf4j
@RestController
@RequestMapping(value = "/bankers", produces = "application/json;charset=UTF-8")
public class BankerController {

    @Autowired
    IBankerService iBankerService;


    @Autowired
    GenericExcelPdfService genericExcelPdfService;

    /**
     * Handles the HTTP POST request to save banker information.
     *
     * @param bankerDto The DTO containing banker information to be saved.
     * @param result    The binding result for validation.
     * @return BankerForGetDto the saved BankerDto in the body.
     */
    @PostMapping
    public BankerForGetDto saveBanker(@RequestBody BankerForPostDto bankerDto, BindingResult result) {
        log.debug(" Start adding banker ....");
        try {
            return iBankerService.saveBanker(bankerDto, result);
        } catch (MessagingException e) {
            throw new EmailSendingException(ApplicationConstants.ERROR_EMAIL_SEND, "Error sending Email");
        }
    }

    /**
     * Handles the HTTP GET request to find bankers based on search criteria.
     *
     * @param page     The page number for pagination (optional).
     * @param size     The size of each page (optional).
     * @param sort     The sorting order (optional).
     * @param bankerId The ID of the banker (optional).
     * @param bankId   The ID of the bank associated with the banker (optional).
     * @return PaginatedResponse<BankerForGetAllDto> The response entity containing a paginated list of BankerDto objects.
     */

    @GetMapping
    public PaginatedResponse<BankerForGetAllDto> findBySearch(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            Sort sort,
            @RequestParam(name = "bankerId", required = false) String bankerId,
            @RequestParam(name = "bankId", required = false) String bankId) {
        log.info("  start search  banker .......");
        return iBankerService.findAllBySearch(page, size, sort, bankerId, bankId);
    }

    /**
     * Handles the HTTP GET request to retrieve a banker by their ID.
     *
     * @param id The ID of the banker to retrieve.
     * @return BankerDto The response entity containing the BankerDto with the specified ID.
     */
    @GetMapping("/{id}")
    BankerForGetDto findBankerById(@PathVariable String id) {
        return iBankerService.findBankerById(id);
    }

    /**
     * Handles the HTTP PUT request to update a banker by their ID.
     *
     * @param id        The ID of the banker to be updated.
     * @param bankerDto The DTO containing updated banker information.
     * @param result    The binding result for validation.
     * @return BankerDto The response entity containing the updated BankerDto.
     */

    @PostMapping("/{id}")
    BankerForGetDto updateBanker(@PathVariable String id,
                                 @RequestBody BankerForPutDto bankerDto,
                                 BindingResult result) {

        try {
            return iBankerService.updateBanker(id, bankerDto, result);
        } catch (MessagingException e) {
            throw new EmailSendingException(ApplicationConstants.ERROR_EMAIL_SEND, "Error sending Email");
        }
    }

    /**
     * @param operationType
     * @return list of bankers active and not admin of certain bank
     */
    @GetMapping ("/children/{operationType}")
    List<BankerLightForGetDto> getBankers (@PathVariable OperationType operationType) {
        return iBankerService.findByApprovedIntermediaryIdAndIsActiveAndIsAdmin(operationType);
    }

    /**
     *  export filtered banker table list based on its Type PDF or EXCEL
     */
    @GetMapping("/export/{type}")
    public DownloadDocumentDto createExcelSheet(
            @PathVariable(name = "type") FileTypeEnum type,
            @RequestParam(name = "bankerId", required = false) String bankerId,
            @RequestParam(name = "bankId", required = false) String bankId){
        try {
            if (ApplicationConstants.EXCEL.equals(FileTypeEnum.valueOf(type.name()).name())) {
                return genericExcelPdfService.saveExcelSheet(iBankerService.findAllBySearchExport(bankerId, bankId).getContent());
            } else {
                return genericExcelPdfService.savePdfTable(iBankerService.findAllBySearchExport(bankerId, bankId).getContent());
            }
        } catch (IOException | DocumentException e) {
                throw new DocumentExportException(ApplicationConstants.DOCUMENT_ERROR , "Document EExport Error");
            }
        }
    }
