package com.wevioo.pi.rest.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.rest.dto.AttachmentDetailsDto;
import com.wevioo.pi.rest.dto.AttachmentDetailsForGetDto;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;
import com.wevioo.pi.rest.dto.SectionConfigDto;
import com.wevioo.pi.service.AttachmentService;
import com.wevioo.pi.service.SectionService;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@Slf4j
@RestController
@RequestMapping(value = "/attachments", produces = "application/json;charset=UTF-8")
public class AttachmentController {

	/**
	 * Injected bean {@link AttachmentService}
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * Injected bean {@link SectionService}
	 */
	@Autowired
	private SectionService sectionService;

    /**
     * Create new attachment
     * 
     * @param attachmentDetailsDto {@link AttachmentDetailsDto}
     * @param attachments          list of MultipartFile
     * @return {@link AttachmentDetailsForGetDto}
     * @throws IOException custom exception
     */
    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadAttachments(
          @RequestPart("attachmentDetailsDto") String attachmentDetailsDto,
          @RequestPart(value = "attachment", required = false) List<MultipartFile> attachments) {
       log.info("load attachment");
        attachmentService.saveAttachment(attachmentDetailsDto, attachments);
    }

    /**
     * Download attachment
     * 
     * @param attachmentId Attachment's id
     * @return {@link DownloadDocumentDto}
     */
    @GetMapping("/{attachmentId}")
    @ResponseStatus(HttpStatus.OK)
    public DownloadDocumentDto downloadPDF(@PathVariable("attachmentId") String attachmentId) {
       log.info("download attachment");
       return attachmentService.dowloadAttachment(attachmentId);
    }

	/**
	 * Get section/document/attachment related to Request's id
	 * 
	 * @param idForm            Request's id
	 * @param declarationNature DeclarationNature
	 * @param investmentType    InvestmentType
	 * @param operationType     OperationType
	 * @return List<SectionConfigDto>
	 */
	@GetMapping("/sections/form/{idForm}/nature/{declarationNature}")
	@ResponseStatus(HttpStatus.OK)
	public List<SectionConfigDto> getSection(@PathVariable("idForm") String idForm,
			@PathVariable("declarationNature") DeclarationNature declarationNature,
			@RequestParam(required = false) InvestmentType investmentType,
			@RequestParam(required = false) OperationType operationType) {
		log.info("search section/document/attachment related to  request");
		return sectionService.findByDeclarationNatureAndInvestmentTypeAndOperationType(declarationNature,
				investmentType, operationType, idForm);
	}
	
	/**
	 * 
	 * @param declarationNature DeclarationNature
	 * @param investmentType InvestmentType
	 * @param operationType OperationType
	 * @return
	 * @throws FileNotFoundException
	 * @throws JRException
	 */

	@PostMapping("/reporting/documents/{declarationNature}/{investmentType}/{operationType}")
	public DownloadDocumentDto exportInvestmentFile(@PathVariable("declarationNature") DeclarationNature declarationNature,
			@PathVariable("investmentType") InvestmentType investmentType,
			@PathVariable("operationType") OperationType operationType) {
		return sectionService.exportDocumentsByDeclarationNatureAndInvestmentTypeAndOperationType(declarationNature,
				investmentType, operationType);
	}

	@PostMapping("/reporting/documents/{idDirectInvest}/{declarationNature}/{investmentType}/{operationType}")
	public DownloadDocumentDto exportInvestmentFile(@PathVariable("idDirectInvest") String idDirectInvest,@PathVariable("declarationNature") DeclarationNature declarationNature,
			@PathVariable("investmentType") InvestmentType investmentType,
			@PathVariable("operationType") OperationType operationType) throws JRException, IOException {
		return sectionService.exportSheetDocumentsByDeclarationNatureAndInvestmentTypeAndOperationType(declarationNature,
				investmentType, operationType, idDirectInvest);
	}
}
