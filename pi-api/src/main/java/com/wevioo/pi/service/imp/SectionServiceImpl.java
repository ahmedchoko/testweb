package com.wevioo.pi.service.imp;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import com.google.zxing.WriterException;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.entity.config.Section;
import com.wevioo.pi.domain.entity.referential.Country;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.domain.entity.request.ActivityDeclaration;
import com.wevioo.pi.domain.entity.request.ActivitySupport;
import com.wevioo.pi.domain.entity.request.CurrencyFinancingData;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.InvestIdentification;
import com.wevioo.pi.domain.entity.request.ParticipationIdentification;
import com.wevioo.pi.domain.enumeration.ActivityTypeEnum;
import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.exception.ConflictException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.DataValidationException;
import com.wevioo.pi.repository.AttachmentsRepository;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.SectionRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.ActivitySupportReportingDto;
import com.wevioo.pi.rest.dto.AttachmentLightDto;
import com.wevioo.pi.rest.dto.CurrencyFinancingDataDto;
import com.wevioo.pi.rest.dto.DocumentConfigDto;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;
import com.wevioo.pi.rest.dto.SectionConfigDto;
import com.wevioo.pi.rest.dto.request.SecDto;
import com.wevioo.pi.rest.dto.request.SectionFileDto;
import com.wevioo.pi.service.ParameterService;
import com.wevioo.pi.service.SectionService;
import com.wevioo.pi.utility.QrCodeUtil;
import com.wevioo.pi.utility.SecurityUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
@Slf4j
public class SectionServiceImpl implements SectionService {

	/**
	 * Injected bean {@link SectionRepository}
	 */
	@Autowired
	private SectionRepository sectionRepository;
	/**
	 * Injected bean {@link SecurityUtils}
	 */
	@Autowired
	private SecurityUtils securityUtils;

	/**
	 * Injected bean {@link UserRepository}
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * Injected bean {@link AttachmentsRepository }
	 */
	@Autowired
	AttachmentsRepository attachmentsRepository;

	@Autowired
	private ParameterService parameterService;

	/**
	 * Injected bean {@link DirectInvestRequestRepository}
	 */

	@Autowired
	private DirectInvestRequestRepository directInvestRequestRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SectionConfigDto> findByDeclarationNatureAndInvestmentTypeAndOperationType(
			DeclarationNature declarationNature, InvestmentType investmentType, OperationType operationType,
			String idForm) {

//		String userId = securityUtils.getCurrentUserId();
//		userRepository.findById(userId)
//				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
//						ApplicationConstants.USER_NOT_FOUND + userId));

		List<Attachment> attachments = null;
		if (!ObjectUtils.isEmpty(idForm)) {
			attachments = attachmentsRepository.findByIdDeclarationNature(idForm);
		}
		Map<String, List<AttachmentLightDto>> mapResult = getMapAttchment(attachments);
		List<Section> sectionList = sectionRepository.findByDeclarationNatureAndInvestmentTypeAndOperationType(
				declarationNature, investmentType, operationType);

		List<SectionConfigDto> sectionConfigDtoList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(sectionList))
			sectionList.forEach(section -> {
				List<DocumentConfigDto> documentConfigList = new ArrayList<>();
				if (!ObjectUtils.isEmpty(section.getSectionDocument())) {
					section.getSectionDocument().forEach(doc -> {
						DocumentConfigDto docDto = DocumentConfigDto.builder().id(doc.getDocument().getId())
								.label(doc.getDocument().getLabel()).description(doc.getDocument().getDescription())
								.isMandatory(doc.getIsMandatory()).requestedNumber(doc.getRequestedNumber()).build();
						if (mapResult.containsKey(doc.getSection().getId() + "_" + doc.getDocument().getId())) {
							docDto.setAttachments(
									mapResult.get(doc.getSection().getId() + "_" + doc.getDocument().getId()));
						}
						documentConfigList.add(docDto);
					});
				}

				SectionConfigDto sectionDto = SectionConfigDto.builder().id(section.getId()).label(section.getLabel())
						.documentConfigList(documentConfigList).build();
				sectionConfigDtoList.add(sectionDto);
			});

		return sectionConfigDtoList;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<SectionConfigDto> findByDeclarationNatureAndInvestmentTypeAndOperationTypeForVisualisation(
			DeclarationNature declarationNature, InvestmentType investmentType, OperationType operationType) {

		List<Section> sectionList = sectionRepository.findByDeclarationNatureAndInvestmentTypeAndOperationType(
				declarationNature, investmentType, operationType);

		List<SectionConfigDto> sectionConfigDtoList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(sectionList))
			sectionList.forEach(section -> {
				List<DocumentConfigDto> documentConfigList = new ArrayList<>();
				if (!ObjectUtils.isEmpty(section.getSectionDocument())) {
					section.getSectionDocument().forEach(doc -> {
						DocumentConfigDto docDto = DocumentConfigDto.builder().id(doc.getDocument().getId())
								.label(doc.getDocument().getLabel()).description(doc.getDocument().getDescription())
								.isMandatory(doc.getIsMandatory()).requestedNumber(doc.getRequestedNumber()).build();

						documentConfigList.add(docDto);
					});
				}

				SectionConfigDto sectionDto = SectionConfigDto.builder().id(section.getId()).label(section.getLabel())
						.documentConfigList(documentConfigList).build();
				sectionConfigDtoList.add(sectionDto);
			});

		return sectionConfigDtoList;
	}

	/**
	 * Group attachment by section and document
	 * 
	 * @param attachments List<Attachment>
	 * @return Map<String, List<AttachmentLightDto>>
	 */
	private Map<String, List<AttachmentLightDto>> getMapAttchment(List<Attachment> attachments) {

		Map<String, List<AttachmentLightDto>> mapResult = new HashMap<>();
		if (!ObjectUtils.isEmpty(attachments)) {
			attachments.forEach(attachment -> {

				List<AttachmentLightDto> attachmentLightDtoList = new ArrayList<>();
				if (mapResult.containsKey(attachment.getSection().getId() + "_" + attachment.getDocument().getId())) {
					attachmentLightDtoList = mapResult
							.get(attachment.getSection().getId() + "_" + attachment.getDocument().getId());
					AttachmentLightDto attachmentDto = AttachmentLightDto.builder().attachmentId(attachment.getId())
							.fileName(attachment.getFileName()).build();
					attachmentLightDtoList.add(attachmentDto);
				} else {
					AttachmentLightDto attachmentDto = AttachmentLightDto.builder().attachmentId(attachment.getId())
							.fileName(attachment.getFileName()).build();
					attachmentLightDtoList.add(attachmentDto);
				}

				mapResult.put(attachment.getSection().getId() + "_" + attachment.getDocument().getId(),
						attachmentLightDtoList);

			});

		}
		return mapResult;
	}

	/**
	 * export Documents By DeclarationNature InvestmentType and OperationType
	 * 
	 */
	@Override
	public DownloadDocumentDto exportDocumentsByDeclarationNatureAndInvestmentTypeAndOperationType(
			DeclarationNature declarationNature, InvestmentType investmentType, OperationType operationType) {

		log.info("section List");
		List<SecDto> sectionFileDto = findByDeclarationNatureAndInvestmentTypeAndOperationType(declarationNature,
				investmentType, operationType);

		JasperPrint jasperPrint = null;

		try {
			jasperPrint = dataToPdf(sectionFileDto);
		} catch (FileNotFoundException | JRException e1) {
			e1.printStackTrace();
		}
		log.info("export Investment File ");
		try {
			return DownloadDocumentDto.builder()
					.content(Base64.getEncoder().encodeToString(JasperExportManager.exportReportToPdf(jasperPrint)))
					.contentType("application/pdf").fileName("Dossier Investissement").build();
		} catch (JRException e) {
			log.error("An exception has been thrown ", e);
			throw new ConflictException(ApplicationConstants.ERROR_EXPORT_PDF_FILE_FROM_JRXML,
					ApplicationConstants.FAILED_TO_GENERATE_PDF_FILE);
		}

	}

	/**
	 * find section By DeclarationNature,InvestmentType And OperationTypee
	 * 
	 * @param declarationNature
	 * @param investmentType
	 * @param operationType
	 * @return List<SecDto>
	 */
	public List<SecDto> findByDeclarationNatureAndInvestmentTypeAndOperationType(DeclarationNature declarationNature,
			InvestmentType investmentType, OperationType operationType) {

		List<Section> sectionList = sectionRepository.findByDeclarationNatureAndInvestmentTypeAndOperationType(
				declarationNature, investmentType, operationType);
		List<SecDto> investmentFileDtoList = new ArrayList<>();

		if (!ObjectUtils.isEmpty(sectionList)) {
			sectionList.forEach(section -> {
				if (!ObjectUtils.isEmpty(section.getSectionDocument())) {
					section.getSectionDocument().forEach(doc -> {
						SecDto investmentFileDto = SecDto.builder().id(section.getId()).sectionTitle(section.getLabel())
								.docLabel(doc.getDocument().getLabel()).build();
						investmentFileDtoList.add(investmentFileDto);
					});
				}
			});
		}
		return investmentFileDtoList;
	}

	/**
	 * dataToPdf
	 * 
	 * @param sectionFileDto
	 * @return
	 * @throws FileNotFoundException
	 * @throws JRException
	 */

	private JasperPrint dataToPdf(List<SecDto> sectionFileDto) throws FileNotFoundException, JRException {

		// read path file from referentiel
		Parameter param = parameterService.getByKey(ApplicationConstants.TEMPLATE_INVESTMENT);
		String basePath = param.getParameterValue();

		String logoUrl = basePath + File.separator + ApplicationConstants.IMG_LOGO;

		Map<String, Object> parameters = new HashMap<>();

		// add parameters to map
		parameters.put(ApplicationConstants.LOGO, logoUrl);
		parameters.put(ApplicationConstants.TITLE, "DOSSIER D'INVESTISSEMENT");
		parameters.put(ApplicationConstants.DOCUMENTS, "Documents");
		parameters.put(ApplicationConstants.SECTIONS, new JRBeanCollectionDataSource(sectionFileDto));

		// read jrxml file
		String templateInvestmentFile = basePath + File.separator + ApplicationConstants.TEMPLATE_INVESTMENT_FILE;
		try {
			JasperPrint jasperPrint = generateJasperPrint(templateInvestmentFile, parameters);
			return jasperPrint;
		} catch (Exception e) {
			log.error("An exception has been thrown ", e);
			throw new DataNotFoundException(ApplicationConstants.TEMPLATE_JRXML_NOT_FOUND_WITH_NAME,
					ApplicationConstants.NO_TEMPLATE_JRXML_FOUND_WITH_NAME + templateInvestmentFile);
		}
	}

	/**
	 * export SheetDocuments ByDeclarationNatureAndInvestmentTypeAndOperationType
	 */
	@Override
	public DownloadDocumentDto exportSheetDocumentsByDeclarationNatureAndInvestmentTypeAndOperationType(
			DeclarationNature declarationNature, InvestmentType investmentType, OperationType operationType,
			String idDirectInvest) throws JRException, IOException {

		log.info("direct Invest sheet");
		DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(idDirectInvest)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND,
						"DIRECT INVEST REQUEST NOT FOUND WITH ID :" + idDirectInvest));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		List<SectionFileDto> activitySectorList = extractActivitySectors(directInvestRequest);
		List<ActivitySupportReportingDto> activitySupportList = extractActivitySupports(directInvestRequest, sdf);
		List<CurrencyFinancingDataDto> currencyFinancingDataList = extractCurrencyFinancingData(directInvestRequest,
				sdf);

		JasperPrint jasperPrintInvestSheet = createJasperPrintInvestSheet(directInvestRequest, activitySectorList,
				activitySupportList, currencyFinancingDataList);

		log.info("export Investment Sheet ");
		try {
			return DownloadDocumentDto.builder()
					.content(Base64.getEncoder()
							.encodeToString(JasperExportManager.exportReportToPdf(jasperPrintInvestSheet)))
					.contentType("application/pdf").fileName("Investment Sheet.pdf").build();
		} catch (JRException e) {
			log.error("An exception has been thrown ", e);
			throw new ConflictException(ApplicationConstants.ERROR_EXPORT_PDF_FILE_FROM_JRXML,
					ApplicationConstants.FAILED_TO_GENERATE_PDF_FILE);
		}
	}

	/**
	 * validation
	 * 
	 * @param directInvestRequest
	 */
	private void validateDirectInvest(DirectInvestRequest directInvestRequest) {

		if (directInvestRequest == null) {
			throw new DataValidationException(ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID);
		}

		if (directInvestRequest.getInvestIdentification() == null) {
			throw new DataValidationException(
					ApplicationConstants.INVEST_IDENTIFICATION_NOT_FOUND + directInvestRequest.getId());
		}

		if (directInvestRequest.getInvestIdentification().getActivityDeclarations() == null) {
			throw new DataValidationException(
					ApplicationConstants.ACTIVITY_DECLARATION_NOT_FOUND + directInvestRequest.getId());
		}
	}

	/** validation **/
	private void validateDirectInvestAct(ActivityDeclaration act) {

		if (act == null) {
			throw new DataValidationException(ApplicationConstants.ACTIVITY_DECLARATION_NOT_FOUND);
		}
		if (act.getActivitySector() == null) {
			throw new DataValidationException(ApplicationConstants.ACTIVITY_SECTOR_NOT_FOUND);
		}
		if (act.getActivitySector().getLabel() == null) {
			throw new DataValidationException(ApplicationConstants.ACTIVITY_SECTOR_LABEL_NOT_FOUND + act.getId());
		}
	}

	/**
	 * extractActivitySectors
	 * 
	 * @param directInvestRequest
	 * @return
	 */
	private List<SectionFileDto> extractActivitySectors(DirectInvestRequest directInvestRequest) {
		// validate direct invest
		validateDirectInvest(directInvestRequest);

		List<SectionFileDto> activitySectorList = new ArrayList<>();
		directInvestRequest.getInvestIdentification().getActivityDeclarations().forEach(act -> {
			// validate Activity Declaration

			if (act.getActivityClass() != null) {
				validateDirectInvestAct(act);
				SectionFileDto investmentSheetDto = SectionFileDto.builder()
						.sectionTitle(act.getActivitySector().getLabel()).build();
				activitySectorList.add(investmentSheetDto);
			}
		});
		return activitySectorList;
	}

	/** validation **/
	private void validateDirectInvestForActSupport(DirectInvestRequest directInvestRequest) {

		if (directInvestRequest == null) {
			throw new DataValidationException(ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID);
		}

		if (directInvestRequest.getInvestIdentification() == null) {
			throw new DataValidationException(
					ApplicationConstants.INVEST_IDENTIFICATION_NOT_FOUND + directInvestRequest.getId());
		}

		if (directInvestRequest.getInvestIdentification().getActivitySupports() == null) {
			throw new DataValidationException(
					ApplicationConstants.ACTIVITY_SUPPORT_NOT_FOUND + directInvestRequest.getId());
		}
	}

	/** validation **/
	private void validateActivitySupport(ActivitySupport actS) {

		if (actS == null) {
			throw new DataValidationException(ApplicationConstants.ACTIVITY_SUPPORT_NOT_FOUND);
		}
		if (actS.getActivityType() == null) {
			throw new DataValidationException(ApplicationConstants.ACTIVITY_SUPPORT_TYPE_NOT_FOUND);
		}
		if (actS.getIssuingAuthority() == null) {
			throw new DataValidationException(ApplicationConstants.AUTHORITY_NOT_FOUND + actS.getId());
		}
		if (actS.getDateSupport() == null) {
			throw new DataValidationException(
					ApplicationConstants.ACTIVITY_SUPPORT_DATE_SUPPORT_NOT_FOUND + actS.getId());
		}
	}

	/**
	 * 
	 * @param directInvestRequest
	 * @param sdf
	 * @return
	 */
	private List<ActivitySupportReportingDto> extractActivitySupports(DirectInvestRequest directInvestRequest,
			SimpleDateFormat sdf) {
		// validate direct invest For Activity Support
		validateDirectInvestForActSupport(directInvestRequest);
		List<ActivitySupportReportingDto> activitySupportList = new ArrayList<>();

		directInvestRequest.getInvestIdentification().getActivitySupports().forEach(actS -> {
			// validate ActivitySupport
			validateActivitySupport(actS);

			ActivitySupportReportingDto activitySupportListDto = createActivitySupportListDto(actS, sdf);
			activitySupportList.add(activitySupportListDto);

		});

		return activitySupportList;
	}

	/**
	 * 
	 * @param actS ActivitySupport
	 * @param sdf  SimpleDateFormat
	 * @return
	 */
	private ActivitySupportReportingDto createActivitySupportListDto(ActivitySupport actS, SimpleDateFormat sdf) {
		return ActivitySupportReportingDto.builder().activitySupportTypeReq("type")
				.activitySupportAuthReq("Autorité ayant délivré le support").activitySupportDateReq("Date")
				.activitySupportNumReq("Numéro")
				.activitySupportTypeRes(actS.getActivityType() == ActivityTypeEnum.PRIMARY ? "activité principale"
						: "activité secondaire")
				.activitySupportAuthRes(actS.getIssuingAuthority().getLabel())
				.activitySupportDateRes(sdf.format(actS.getDateSupport()))
				.activitySupportNumRes(actS.getSupportNumber()).build();
	}

	/** validation **/
	private void validateDirectInvestForCurrencyFinancingData(DirectInvestRequest directInvestRequest) {
		if (directInvestRequest == null) {
			throw new DataValidationException(ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID);
		}

		if (directInvestRequest.getCurrencyFinancing() == null) {
			throw new DataValidationException(ApplicationConstants.CURRENCY_NOT_FOUND + directInvestRequest.getId());
		}

		if (directInvestRequest.getCurrencyFinancing().getCurrencyFinancingData() == null) {
			throw new DataValidationException(
					ApplicationConstants.NO_CURRENCY_FOUNDED_WITH_ID + directInvestRequest.getId());
		}

	}

	/** validation **/
	private void validateCurrencyFinancing(CurrencyFinancingData currency) {
		if (currency == null) {
			throw new DataValidationException(ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID);
		}

		if (currency.getImportationPiece() == null) {
			throw new DataValidationException(
					ApplicationConstants.CURRENCY_IMPRTATION_PIECE_NOT_FOUND + currency.getId());
		}

		if (currency.getFinancialCurrency() == null) {
			throw new DataValidationException(ApplicationConstants.NO_CURRENCY_FOUNDED_WITH_ID + currency.getId());
		}
		if (currency.getCessionDate() == null) {
			throw new DataValidationException(
					ApplicationConstants.NO_CURRENCY_CESSION_DATE_FOUNDED_WITH_ID + currency.getId());
		}

	}

	/**
	 * 
	 * @param directInvestRequest
	 * @param sdf
	 * @return
	 */
	private List<CurrencyFinancingDataDto> extractCurrencyFinancingData(DirectInvestRequest directInvestRequest,
			SimpleDateFormat sdf) {
		List<CurrencyFinancingDataDto> currencyFinancingDataList = new ArrayList<>();

		// validate direct invest For CurrencyFinancingData
		validateDirectInvestForCurrencyFinancingData(directInvestRequest);

		directInvestRequest.getCurrencyFinancing().getCurrencyFinancingData().forEach(currency -> {
			if (currency != null && currency.getImportationPiece() != null && currency.getFinancialCurrency() != null
					&& currency.getCessionDate() != null && sdf != null) {

				// validate currency For CurrencyFinancingData
				validateCurrencyFinancing(currency);

				CurrencyFinancingDataDto currencyFinancingDataDto = createCurrencyFinancingDataDto(currency, sdf);
				currencyFinancingDataList.add(currencyFinancingDataDto);
			}

			else {
				throw new DataValidationException("ERROR " , ApplicationConstants.NO_CURRENCY_FOUNDED_WITH_ID + currency.getId());

			}
		});

		return currencyFinancingDataList;
	}

	/**
	 * createCurrencyFinancingData
	 * 
	 * @param currency
	 * @param sdf      date format
	 * @return
	 */
	private CurrencyFinancingDataDto createCurrencyFinancingDataDto(CurrencyFinancingData currency,
			SimpleDateFormat sdf) {
		return CurrencyFinancingDataDto.builder().supportingDocuments("Justificatifs du financement en devise")
				.importedAmount("Montant importé en devises").currency("Devise").cessionDate("Date de Cession")
				.counterValueOfImportedAmountTND("Contre-valeur en TND du montant importé")
				.descriptionSpecRefList(currency.getImportationPiece().getLabel())
				.importedAmountList(currency.getImportedAmount())
				.financialCurrencyList(currency.getFinancialCurrency().getLabel())
				.cessionDateList(sdf.format(currency.getCessionDate()))
				.counterValueOfImportedAmountTNDList(currency.getCounterValueOfImportedAmountTND())
				.totalImportedAmount("Total de la contre-valeur en TND du montant importé").build();
	}

	/**
	 * 
	 * @param directInvestRequest
	 * @param activitySectorList
	 * @param activitySupportList
	 * @param currencyFinancingDataList
	 * @return
	 * @throws JRException
	 * @throws IOException
	 */
	private JasperPrint createJasperPrintInvestSheet(DirectInvestRequest directInvestRequest,
			List<SectionFileDto> activitySectorList, List<ActivitySupportReportingDto> activitySupportList,
			List<CurrencyFinancingDataDto> currencyFinancingDataList) throws JRException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		return directInvestSheetToPdf(directInvestRequest, activitySectorList, activitySupportList,
				currencyFinancingDataList, sdf, true, "https://www.google.com/");
	}

	/**
	 * convert directInvest creation company ToPdf
	 * 
	 * @param directInvestRequest
	 * @param activitySectorList
	 * @param activitySupportList
	 * @param currencyFinancingDataList
	 * @param sdf
	 * @param printQRCode
	 * @param url
	 * @return
	 * @throws JRException
	 * @throws IOException
	 */
	private JasperPrint directInvestSheetToPdf(DirectInvestRequest directInvestRequest,
			List<SectionFileDto> activitySectorList, List<ActivitySupportReportingDto> activitySupportList,
			List<CurrencyFinancingDataDto> currencyFinancingDataList, SimpleDateFormat sdf, Boolean printQRCode,
			String... url) throws JRException, IOException {

		Parameter param = parameterService.getByKey(ApplicationConstants.TEMPLATE_INVESTMENT);
		String basePath = param.getParameterValue();
		String logoUrl = basePath + File.separator + ApplicationConstants.IMG_LOGO;

		String templateInvestmentSheet = basePath + File.separator + ApplicationConstants.TEMPLATE_INVESTMENT_SHEET;

		Map<String, Object> parameters = prepareParameters(logoUrl, directInvestRequest, activitySectorList,
				activitySupportList, currencyFinancingDataList, sdf, printQRCode, url);

		try {
			JasperPrint jasperPrint = generateJasperPrint(templateInvestmentSheet, parameters);
			return jasperPrint;
		} catch (FileNotFoundException | JRException e) {
			log.error("An exception has been thrown ", e);
			throw new DataNotFoundException(ApplicationConstants.TEMPLATE_JRXML_NOT_FOUND_WITH_NAME,
					ApplicationConstants.NO_TEMPLATE_JRXML_FOUND_WITH_NAME + templateInvestmentSheet);
		}

	}

	/**
	 * prepareParameters to investment File
	 * 
	 * @param logoUrl
	 * @param directInvestRequest
	 * @param activitySectorList
	 * @param activitySupportList
	 * @param currencyFinancingDataList
	 * @param sdf
	 * @param printQRCode
	 * @param url
	 * @return
	 */
	private Map<String, Object> prepareParameters(String logoUrl, DirectInvestRequest directInvestRequest,
			List<SectionFileDto> activitySectorList, List<ActivitySupportReportingDto> activitySupportList,
			List<CurrencyFinancingDataDto> currencyFinancingDataList, SimpleDateFormat sdf, Boolean printQRCode,
			String... url) {
		Map<String, Object> parameters = new HashMap<>();

		// add parameters to map
		// check language
		// if (Language.FR.equals(directInvestRequest.getLanguage())) {

		parameters.put(ApplicationConstants.LOGO, logoUrl);
		parameters.put(ApplicationConstants.TITLE, "FICHE D'INVESTISSEMENT");

		// Part 1 :Intermédiaire agréé domiciliataire de dossier d’investissement
		parameters.put(ApplicationConstants.INTER_INVEST_REQ,
				"Intermédiaire agréé domiciliataire de dossier d’investissement");
		parameters.put(ApplicationConstants.BANK_ID,
				directInvestRequest.getBank() != null ? directInvestRequest.getBank().getId() : null);
		parameters.put(ApplicationConstants.BANK_LABEL,
				directInvestRequest.getBank() != null ? directInvestRequest.getBank().getLabel() : null);

		parameters.put(ApplicationConstants.NATURE_INVEST_REQ, "Nature de l’investissement");

		switch (directInvestRequest.getCreatedBy().getUserType()) {
		case INVESTOR:
			parameters.put(ApplicationConstants.NATURE_INVEST_RES, "Etablissement d'une fiche d'investissement");
			break;
		case BANKER:
			parameters.put(ApplicationConstants.NATURE_INVEST_RES,
					"Etablissement mandaté d'une fiche d'investissement");
			break;
		default:
			parameters.put(ApplicationConstants.NATURE_INVEST_RES, "Etablissement d'une fiche d'investissement");
			break;
		}

		parameters.put(ApplicationConstants.FORM_INVEST_REQ, "Forme de l’investissement");
		parameters.put(ApplicationConstants.FORM_INVEST_RES, "Création de société");

		// Part 2 :Identification de la société objet l’Investissement
		parameters.put(ApplicationConstants.PART_2_TITLE, "Identification de la société objet l’Investissement");

		parameters.put(ApplicationConstants.UNIQUE_ID_COMPANY_REQ, "- Identifiant unique");
		parameters.put(ApplicationConstants.UNIQUE_ID_COMPANY_RES,
				directInvestRequest.getInvestIdentification() != null
						? directInvestRequest.getInvestIdentification().getUniqueIdentifier()
						: null);

		parameters.put(ApplicationConstants.RAISON_SOCIAL_REQ, "- Raison sociale");
		parameters.put(ApplicationConstants.RAISON_SOCIAL_RES,
				directInvestRequest.getInvestIdentification() != null
						? directInvestRequest.getInvestIdentification().getCompanyName()
						: null);

		parameters.put(ApplicationConstants.ACTIVITY_SECTOR_REQ, "- Secteur d'activité");
		parameters.put(ApplicationConstants.ACTIVITY_SECTOR_RES,
				!activitySectorList.isEmpty() ? new JRBeanCollectionDataSource(activitySectorList) : null);

		parameters.put(ApplicationConstants.SOCIAL_CAP_REQ, "- Capital social");
		parameters.put(ApplicationConstants.SOCIAL_CAP_RES,
				directInvestRequest.getParticipationIdentification().getSocialCapital().toString());

		parameters.put(ApplicationConstants.ACT_SUP_REQ, "- Support d'activité");

		parameters.put(ApplicationConstants.ACT_SUP_RES,
				!activitySupportList.isEmpty() ? new JRBeanCollectionDataSource(activitySupportList) : null);

		// Part 3 :"Identification de l’Investissement"
		parameters.put(ApplicationConstants.PART_3_TITLE, "Identification de l’Investissement");

		parameters.put(ApplicationConstants.NUMBER_PARTS_INVEST_REQ,
				"-  Nombre de parts / actions détenu au moment de la création");

		String numberPartString = Optional.ofNullable(directInvestRequest.getParticipationIdentification())
				.map(ParticipationIdentification::getNumberPart).map(Object::toString).orElse(null);

		parameters.put(ApplicationConstants.NUMBER_PARTS_INVEST_RES, numberPartString);

		parameters.put(ApplicationConstants.FREE_AMOUNT_INVEST_REQ, "-  Montant libéré lors de la création");

		String freeCapitalString = Optional.ofNullable(directInvestRequest.getParticipationIdentification())
				.map(ParticipationIdentification::getFreeCapital).map(Object::toString).orElse(null);

		parameters.put(ApplicationConstants.FREE_AMOUNT_INVEST_RES, freeCapitalString);

		JRBeanCollectionDataSource currencyDataSource = Optional.ofNullable(currencyFinancingDataList)
				.map(JRBeanCollectionDataSource::new).orElse(null);

		parameters.put(ApplicationConstants.CURRENCY_DATA, currencyDataSource);

		// Part 4 :Identification de l'investisseur
		String fullName = Stream
				.of(directInvestRequest.getInvistor().getFirstName(), directInvestRequest.getInvistor().getLastName())
				.filter(Objects::nonNull).collect(Collectors.joining(" "));

		parameters.put(ApplicationConstants.PART_4_TITLE, "Identification de l'investisseur");

		parameters.put(ApplicationConstants.CODE_INVESTOR, "-  Code étranger / N° du registre de commerce étranger");

		parameters.put(ApplicationConstants.CODE_INVESTOR_REQ,
				directInvestRequest.getInvistor().getSocialReason() != null
						? directInvestRequest.getInvistor().getSocialReason()
						: (directInvestRequest.getInvistor().getNameForFund() != null
								? directInvestRequest.getInvistor().getNameForFund()
								: directInvestRequest.getInvistor().getFirstName()
										.concat(" " + directInvestRequest.getInvistor().getLastName())));

		parameters.put(ApplicationConstants.NOM_INVESTOR, "-  Nom et Prénom");
		parameters.put(ApplicationConstants.NOM_INVESTOR_RES, fullName);
		parameters.put(ApplicationConstants.NAT_INVESTOR, "-  Nationalité");

		String nationalityLabel = Optional.ofNullable(directInvestRequest.getInvistor()).map(Investor::getNationality)
				.map(Country::getLabel).orElse(null);

		parameters.put(ApplicationConstants.NAT_INVESTOR_RES, nationalityLabel);
		parameters.put(ApplicationConstants.ADRESS_INVESTOR, "-  Adresse");

		String investorAddress = Optional.ofNullable(directInvestRequest.getInvistor()).map(Investor::getAddress)
				.orElse(null);
		parameters.put(ApplicationConstants.ADRESS_INVESTOR_RES, investorAddress);

		// Part 5 :Identification de l'investisseur
		parameters.put(ApplicationConstants.PART_5_TITLE, "Identification de la Fiche d’Investissement");

		parameters.put(ApplicationConstants.FICHE_INVEST_ID, "-  Référence de la fiche d’investissement");

		parameters.put(ApplicationConstants.FICHE_INVEST_RES,
				directInvestRequest.getInvestIdentification() != null
						? directInvestRequest.getInvestIdentification().getId()
						: null);
		parameters.put(ApplicationConstants.VALIDE_DATE, "-  Date de Validation");

		String formattedModificationDate = directInvestRequest.getModificationDate() != null
				? sdf.format(directInvestRequest.getModificationDate())
				: null;

		parameters.put(ApplicationConstants.VALIDE_DATE_RES, formattedModificationDate);

		///////////////////////////////////////
		parameters.put(ApplicationConstants.SIGNA, "Cachet et signature de l’intermédiaire agréé valideur de la FI");

		////////// generation qrcode ///////////////

		if (printQRCode && url.length > 0) {
			try {
				log.info("url certif with qrcode" + url[0]);
				Image image = QrCodeUtil.createQRImage(url[0]);
				parameters.put(ApplicationConstants.QRCODE, image);
			} catch (WriterException | IOException e) {
				log.error("Unable to generate QR Code : " + e.getMessage());
			}
		}

		return parameters;
	}

	/**
	 * generateJasperPrint
	 * 
	 * @param templateInvestmentSheet
	 * @param parameters
	 * @return
	 * @throws JRException
	 * @throws IOException
	 */
	private JasperPrint generateJasperPrint(String templateInvestmentSheet, Map<String, Object> parameters)
			throws JRException, IOException {
		FileInputStream input = new FileInputStream(ResourceUtils.getFile(templateInvestmentSheet));
		JasperDesign jasperDesign = JRXmlLoader.load(input);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
	}
}
