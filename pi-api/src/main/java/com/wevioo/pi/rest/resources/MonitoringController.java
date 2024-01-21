package com.wevioo.pi.rest.resources;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.wevioo.pi.common.ApplicationConstants;

import com.wevioo.pi.exception.DocumentExportException;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;
import com.wevioo.pi.service.GenericExcelPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.rest.dto.response.MonitoringResponse;
import com.wevioo.pi.domain.enumeration.FileTypeEnum;
import com.wevioo.pi.service.MonitoringService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/monitoring")
@Slf4j
public class MonitoringController {

	/**
	 * Injected bean {@link MonitoringService}
	 */
	@Autowired
	private MonitoringService monitoringService;


	@Autowired
	private GenericExcelPdfService genericExcelPdfService;
	/**
	 * Handles the HTTP GET request to find bankers based on search criteria.
	 *
	 * @param declarationNature DeclarationNature.
	 * @param investmentType    InvestmentType.
	 * @param page              The page number for pagination (optional).
	 * @param size              The size of each page (optional).
	 * @param sort              The sorting order (optional).
	 * @param reference         The ID of the DirectInvest.
	 * @param investor          The firstName/lastName/reasonSocial of the Investor
	 *                          (optional).
	 * @param types             The List of operation type (optional). * @param
	 *                          companyName The name of company (optional). *
	 *                          * @param companyName The name of company (optional).
	 * @return PaginatedResponse<DirectInvestDto> The response entity containing a
	 *         paginated list of DirectInvestDto objects.
	 */
	@GetMapping("/nature/{declarationNature}/investmentType/{investmentType}")
	public MonitoringResponse<Object> findDirectInvestRequestByCriteria(
			@PathVariable("declarationNature") DeclarationNature declarationNature,
			@PathVariable("investmentType") InvestmentType investmentType,
			@RequestParam(name = "reference", required = false) String reference,
			@RequestParam(name = "investor", required = false) String investor,
			@RequestParam(name = "types", required = false) List<OperationType> types,
			@RequestParam(name = "companyName", required = false) String companyName,
			@RequestParam(name = "companyIdentifier", required = false) String companyIdentifier,
			@RequestParam(name = "banks", required = false) List<String> bank,
			@RequestParam(name = "status", required = false) List<RequestStatusEnum> status,
			@RequestParam(name = "investorIdentifier", required = false) String investorIdentifier,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size, Sort sort) {
		return monitoringService.findDirectInvestRequestByCriteria(declarationNature, investmentType, reference,
				investor, types, companyName, companyIdentifier, bank, status, investorIdentifier, page, size, sort);
	}

	@GetMapping("/nature/export/{type}/{declarationNature}/investmentType/{investmentType}")
	public DownloadDocumentDto createExcelSheet(
			@PathVariable(name = "type") FileTypeEnum type,
			@PathVariable("declarationNature") DeclarationNature declarationNature,
			@PathVariable("investmentType") InvestmentType investmentType,
			@RequestParam(name = "reference", required = false) String reference,
			@RequestParam(name = "investor", required = false) String investor,
			@RequestParam(name = "types", required = false) List<OperationType> types,
			@RequestParam(name = "companyName", required = false) String companyName,
			@RequestParam(name = "companyIdentifier", required = false) String companyIdentifier,
			@RequestParam(name = "banks", required = false) List<String> bank,
			@RequestParam(name = "status", required = false) List<RequestStatusEnum> status,
			@RequestParam(name = "investorIdentifier", required = false) String investorIdentifier) {
		try {
			if(ApplicationConstants.EXCEL.equals(FileTypeEnum.valueOf(type.name()).name())) {
				return genericExcelPdfService.saveExcelSheet(monitoringService.findDirectInvestRequestByCriteriaExport(declarationNature, investmentType, reference,
						investor, types, companyName, companyIdentifier, bank, status, investorIdentifier ).getData());
			} else {
				return genericExcelPdfService.savePdfTable(monitoringService.findDirectInvestRequestByCriteriaExport(declarationNature, investmentType, reference,
						investor, types, companyName, companyIdentifier, bank, status, investorIdentifier ).getData());
			}
		} catch (IOException | DocumentException e) {
			throw new DocumentExportException(ApplicationConstants.DOCUMENT_ERROR , "Document EExport Error");
		}
	}

}
