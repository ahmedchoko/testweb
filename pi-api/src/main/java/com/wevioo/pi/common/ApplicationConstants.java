package com.wevioo.pi.common;

import java.util.Arrays;
import java.util.List;

import com.wevioo.pi.domain.enumeration.UserTypeEnum;

public final class ApplicationConstants {

	public static final String IS_ADMIN_NULL = "IS_ADMIN_NULL";
	public static final String APPROVED_INTERMEDIARY_NULL = "APPROVED_INTERMEDIARY_NULL";
	public static final String TYPE_ADMINISTRATOR_IS_NULL = "APPROVED_INTERMEDIARY_NULL";
	public static final String EXCEL = "EXCEL";
	public static final String NO_DATA_FOUND = "NO_DATA_FOUND";
	public static final String FIRST_NAME_IS_REQUIRED = "FIRST_NAME_IS_REQUIRED";

	/**
	 * Email Template Constants
	 */
	public static final String LABEL_TEMPLATE_NOT_FOUND = "LABEL_TEMPLATE_NOT_FOUND";
	public static final String ERROR_EMAIL_SEND = "ERROR_EMAIL_SEND";
	public static final String EMAIL = "EMAIL";
	public static final String PSWD = "PSWD";
	public static final String LOGIN = "LOGIN";
	public static final String INTERMIDIAIRE = "INTERMIDIAIRE";
	public static final String TYPEADMIN = "TYPEADMIN";
	public static final String ID = "ID";
	public static final String NOM = "NOM";
	public static final String FIRSTNAME = "FIRSTNAME";
	public static final String SERVICE = "SERVICE";
	public static final String GENERALDIRECTION = "GENERALDIRECTION";
	public static final String BANKREFERENCE = "BANKREFERENCE";
	public static final String DECLARATIONDATE = "DECLARATIONDATE";
	public static final String ACCOUNTACTIVATIONURL = "ACCOUNTACTIVATIONURL";
	public static final String ACTIVATIONKEY = "ACTIVATIONKEY";
	public static final String PASS = "PASSWORD";
	public static final String URL = "URL";
	public static final String KEY = "KEY";
	public static final String LAST_NAME_IS_REQUIRED = "LAST_NAME_IS_REQUIRED";
	public static final String UNIVERSITY_DEGREE_IS_REQUIRED = "UNIVERSITY_DEGREE_IS_REQUIRED";
	public static final String HOME_PHONE_NUMBER_IS_REQUIRED = "HOME_PHONE_NUMBER_IS_REQUIRED";

	public static final String CELL_PHONE_NUMBER_IS_REQUIRED = "HOME_PHONE_NUMBER_IS_REQUIRED";
	public static final String FAX_NUMBER_IS_REQUIRED = "FAX_NUMBER_IS_REQUIRED";
	public static final String BANK_APPROVED_INTERMEDIARY_IS_REQUIRED = "BANK_APPROVED_INTERMEDIARY_IS_REQUIRED";
	public static final String TYPE_ADMINISTRATOR_IS_REQUIRED = "TYPE_ADMINISTRATOR_IS_REQUIRED";
	public static final String DIRECTION_IS_REQUIRED = "DIRECTION_IS_REQUIRED";
	public static final String GRADE_IS_REQUIRED = "GRADE_IS_REQUIRED";
	public static final String FUNCTION_IS_REQUIRED = "FUNCTION_IS_REQUIRED";

	public static final String NATIONALITY_NOT_TN_CONFLICT = "NATIONALITY_NOT_TN_CONFLICT";

	public static final String NATIONALITY_TN_CONFLICT = "NATIONALITY_TN_CONFLICT";

	public static final String FILE_EXTENSION_INVALID = "FILE_EXTENSION_INVALID";
	public static final String FILE_IS_REQUIRED = "FILE_IS_REQUIRED";
	public static final String COUNTRY_NOT_FOUND = "COUNTRY_NOT_FOUND";
	public static final String ERROR_UNIQUE_IDENTIFICATION_NOT_FOUND_REF = "ERROR_UNIQUE_IDENTIFICATION_NOT_FOUND_REF";
	public static final String ERROR_UNIQUE_IDENTIFICATION_FOUND = "ERROR_UNIQUE_IDENTIFICATION_IS_FOUND";
	public static final String ERROR_IDENTIFICATION_TYPE_NOT_CORRESPOND_PM = "ERROR_IDENTIFICATION_TYPE_NOT_CORRESPOND_PM";
	public static final String ERROR_MAIN_CONTRACTUAL_TERMS_NOT_FOUND = "ERROR_MAIN_CONTRACTUAL_TERMS_NOT_FOUND";

	public static final String TAX_IDENTIFICATION_NOT_FOUND = "TAX_IDENTIFICATION_NOT_FOUND";
	public static final String CIN_NOT_FOUND = "CIN_NOT_FOUND";
	public static final String PROPERTY_REQUEST_NOT_FOUND = "PROPERTY_REQUEST_NOT_FOUND";

	public static final String CURRENCY_FINANCING_NOT_FOUND = "CURRENCY_FINANCING_NOT_FOUND";
	public static final String AUTHORISATION_REQUIRED_NOT_FOUND = "AUTHORISATION_REQUIRED_NOT_FOUND";

	public static final String ACTIVITY_SECTOR_NOT_FOUND = "ACTIVITY_SECTOR_NOT_FOUND";
	public static final String ACTIVITY_SUB_SECTOR_NOT_FOUND = "ACTIVITY_SUB_SECTOR_NOT_FOUND";
	public static final String ACTIVITY_GROUP_NOT_FOUND = "ACTIVITY_GROUP_NOT_FOUND";
	public static final String REQUESTER_NOT_FOUND = "REQUESTER_NOT_FOUND";
	public static final String ACTIVITY_DECLARATION_NOT_FOUND = "ACTIVITY_DECLARATION_NOT_FOUND";

	public static final String ACTIVITY_SUPPORT_NOT_FOUND = "ACTIVITY_SUPPORT_NOT_FOUND";
	public static final String ACTIVITY_CLASS_NOT_FOUND = "ACTIVITY_CLASS_NOT_FOUND";
	public static final String ACTIVITY_SUPPORT_TYPE_NOT_FOUND = "ACTIVITY_SUPPORT_TYPE_NOT_FOUND";
	public static final String AUTHORITY_NOT_FOUND = "AUTHORITY_NOT_FOUND";
	public static final String ACTIVITY_CLASS_LABEL_NOT_FOUND = "ACTIVITY_LABEL_NOT_FOUND For Activity id: ";

	public static final String LEGAL_FORM_NOT_FOUND = "LEGAL_FORM_NOT_FOUND";
	public static final String INVEST_TYPE_NOT_FOUND = "INVEST_TYPE_NOT_FOUND";
	public static final String VOCATION_NOT_FOUND = "VOCATION_NOT_FOUND";

	public static final String CATEGORY_SPECIFIC_NOT_FOUND = "CATEGORY_SPECIFIC_NOT_FOUND";

	public static final String LOCATION_NOT_FOUND = "LOCATION_NOT_FOUND";
	public static final String USAGE_NOT_FOUND = "USAGE_NOT_FOUND";

	public static final String CLAIM_NOT_FOUND = "CLAIM_NOT_FOUND";
	public static final String FILE_SIZE_LIMIT_EXCEEDED = "FILE_SIZE_LIMIT_EXCEEDED";
	public static final String FINANCIAL_MODE_NOT_FOUND = "FINANCIAL_MODE_NOT_FOUND";
	public static final String IMPORTATION_PIECE_FOUND = "IMPORTATION_PIECE_FOUND";

	public static final String OPERATION_TYPE_NOT_FOUND = "OPERATION_TYPE_NOT_FOUND";

	public static final String GOVERNORATE_NOT_FOUND = "GOVERNORATE_NOT_FOUND";
	public static final String DELEGATION_NOT_FOUND = "DELEGATION_NOT_FOUND";
	public static final String LOCALITY_NOT_FOUND = "LOCALITY_NOT_FOUND";
	public static final String ZIP_CODE_NOT_FOUND = "ZIP_CODE_NOT_FOUND";
	public static final String MORAL_PERSON_NOT_FOUND = "MORAL_PERSON_NOT_FOUND";
	public static final String PHYSIC_PERSON_NOT_FOUND = "PHYSIC_PERSON_NOT_FOUND";

	public static final String TYPE_IDENTIFICATION_IN_REF_NOT_FOUND = "TYPE_IDENTIFICATION_IN_REF_NOT_FOUND";

	public static final String ATTACHMENT_NOT_FOUND_WITH_ID = "ATTACHMENT_NOT_FOUND_WITH_ID ";
	public static final String ERROR_NO_FILE_FOUND = "NO_FILE_FOUND";
	public static final String NO_FILE_FOUND_WITH_PATH = "No File  found with PATH:";
	public static final String ERROR_PARAMETER_NOT_FOUND = "ERROR_PARAMETER_NOT_FOUND";

	public static final String SECTION_NOT_FOUND_WITH_ID = "SECTION_NOT_FOUND_WITH_ID ";
	public static final String DOCUMENT_NOT_FOUND_WITH_ID = "DOCUMENT_NOT_FOUND_WITH_ID ";
	public static final String ERROR_FAILED_TO_DELETE_FILE = "FAILED_TO_DELETE_FILE";
	public static final String TEMPLATE_JRXML_NOT_FOUND_WITH_NAME = "TEMPLATE_JRXML_NOT_FOUND";
	public static final String NO_TEMPLATE_JRXML_FOUND_WITH_NAME = "No template JRXML found with name: ";

	public static final String ERROR_EXPORT_PDF_FILE_FROM_JRXML = "ERROR_EXPORT_PDF_FILE_FROM_JRXML";
	public static final String FAILED_TO_GENERATE_PDF_FILE = "Failed to generate PDF file.";

	public static final String FAILED_TO_DELETE_FILE = "Failed to delete the file.";

	public static final String REPOSITORY_URL = "path.attachment.repo";
	public static final String FILE_SIZE_LIMIT_PROPERTY = "file.size.limit";

	public static final String SOCIALREASON = "codification.socialReason";
	public static final String ADDRES = "codification.address";

	public static final String TN_COUNTRY_KEY = "country.code.tn";

	public static final String REFERENTIAL_VOCATION = "referential.vocation";

	/**
	 * legal form
	 */
	public static final String LEGAL_FORM = "legal.form";
	/**
	 * Société Anonyme
	 */
	public static final String USA = "sa";
	/**
	 * Société SARL
	 */
	public static final String SARL = "sarl";
	/**
	 * Société SUARL
	 */
	public static final String SUARL = "suarl";

	/**
	 * Société en Commandite par actions
	 */
	public static final String SCACT = "scact";

	public static final String INVEST_TYPE_ACQUISITION_TERRAIN = "invest.type.acquisition.terrain";

	public static final String NO_COUNTRY_FOUNDED_WITH_ID = "No country founded with id: ";
	public static final String MAIN_CONTRACTUAL_TERMS_NOT_FOUND = "MAIN_CONTRACTUAL_TERMS_NOT_FOUND";

	public static final String EMAIL_ALREADY_EXIST_MSG = "Email already exists";
	public static final String NO_BANK_FOUNDED_WITH_ID = "No bank founded with id: ";
	public static final String NO_CURRENCY_FOUNDED_WITH_ID = "No currency founded with id: ";

	public static final String CLAIM_NOT_FOUND_ERROR = "No claim found with id: ";
	public static final String NATIONALITY_NOT_TN_CONFLICT_MSG = "Nationality should not be Tunisian";

	public static final String NATIONALITY_TN_CONFLICT_MSG = "Nationality should be Tunisian";

	public static final String NO_ACTIVITY_SECTOR_FOUNDED_WITH_ID = "No activity sector founded with id: ";
	public static final String NO_ACTIVITY_SUB_SECTOR_FOUNDED_WITH_ID = "No activity sub sector founded with id: ";
	public static final String NO_ACTIVITY_GROUP_FOUNDED_WITH_ID = "No activity group founded with id: ";
	public static final String NO_ACTIVITY_CLASS_FOUNDED_WITH_ID = "No activity class founded with id: ";

	public static final String NO_ACTIVITY_SUPPORT_TYPE_FOUNDED_WITH_ID = "No activity support type founded with id: ";

	public static final String NO_AUTHORITY_FOUNDED_WITH_ID = "No authority founded with id: ";
	public static final String NO_LEGAL_FORM_FOUNDED_WITH_ID = "No legal form founded with id: ";

	public static final String NO_INVEST_TYPE_FOUNDED_WITH_ID = "No invest type founded with id: ";
	public static final String NO_VOCATION_FOUNDED_WITH_ID = "No vocation founded with id: ";
	public static final String NO_LOCATION_FOUNDED_WITH_ID = "No location founded with id: ";
	public static final String NO_USAGE_FOUNDED_WITH_ID = "No usage founded with id: ";
	public static final String NO_FINANCIAL_MODE_FOUNDED_WITH_ID = "No financial mode founded with id: ";
	public static final String NO_IMPORTATION_PIECE_FOUNDED_WITH_ID = "No imporation piece founded with id: ";

	public static final String NO_GOVERNORATE_FOUNDED_WITH_ID = "No governorate founded with id: ";
	public static final String NO_DELEGATION_FOUNDED_WITH_ID = "No delegation founded with id: ";

	public static final String NO_LOCALITY_FOUNDED_WITH_ID = "No locality founded with id: ";
	public static final String NO_ZIP_CODE_FOUNDED_WITH_ID = "No zip code founded with id: ";

	public static final String NO_PROPERTY_REQUEST_FOUNDED_WITH_ID = "No property request founded with id: ";
	public static final String NO_CURRENCY_FINANCING_FOUNDED_WITH_ID = "No currency financing founded for property request with id: ";
	public static final String NO_AUTHORISATION_REQUIRED_WITH_ID = "No authorisation required founded for property request with id: ";

	public static final String NO_INVESTOR_FOUNDED_WITH_ID = "No Investor founded with id: ";

	/**
	 * Error codes
	 */

	public static final String BAD_REQUEST_CODE = "400";
	public static final String ERROR_INVALID_PASSWORD_FORMAT = "INVALID_PASSWORD_FORMAT";
	public static final String ERROR_LINK_NOT_FOUND = "LINK_NOT_FOUND";
	public static final String ERROR_MISMATCHED_PASSWORDS = "ERROR_MISMATCHED_PASSWORDS";
	public static final String ERROR_INVALID_SOCIAL_REASON_FORMAT = "INVALID_SOCIAL_REASON_FORMAT";
	public static final String ERROR_INVALID_ADDRESS_FORMAT = "INVALID_ADDRESS_FORMAT";
	public static final String ERROR_INVALID_UNIQUE_IDENTIFICATION_FORMAT = "INVALID_UNIQUE_IDENTIFICATION_FORMAT";
	public static final String KEY_GEN_NOT_FOUND_ERROR = "No KEYGEN found with type: ";
	public static final String KEY_GEN_NOT_FOUND_WITH_TYPE = "KEYGEN_NOT_FOUND_WITH_TYPE";

	public static final String FAILED_VALIDATION = "FAILED_VALIDATION";

	public static final String INVESTOR_NOT_FOUND_WITH_ID = "INVESTOR_NOT_FOUND_WITH_ID";

	public static final String INVESTOR_NOT_FOUND = "INVESTOR_NOT_FOUND";
	public static final String INVEST_IDENTIFICATION_NOT_FOUND = "INVEST_IDENTIFICATION_NOT_FOUND";

	public static final String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
	public static final String ERROR_USER_NOT_ALLOWED = "ERROR_USER_NOT_ALLOWED";
	public static final String ERROR_INTERNAL_SERVER_ERROR = "ERROR_INTERNAL_SERVER_ERROR";
	public static final String ERROR_UNAUTHORIZED_REQUEST = "ERROR_UNAUTHORIZED_REQUEST";
	public static final String ERROR_MISSING_REQUIRED_FILES = "ERROR_MISSING_REQUIRED_FILES";
	public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
	public static final String DOCUMENT_ERROR = "DOCUMENT_ERROR";
	public static final String ADMIN_BANKER_NOT_FOUND = "ADMIN_BANKER_NOT_FOUND";

	public static final String BANKER_NOT_FOUND = "BANKER_NOT_FOUND";

	public static final String BANKER_NOT_FOUND_WITH_ID = "BANKER_NOT_FOUND_WITH_ID :";
	public static final String ADMIN_BANKER_NOT_FOUND_FOR_THIS = "NO_ADMIN_BANKER_FOUND_FOR_THIS_BANK";

	/**
	 * Codes length
	 */
	public static final int SECURITY_CODE_LENGTH = 5;
	public static final int SECURITY_CODE_CITIZEN_LENGTH = 8;
	public static final int ACTIVATION_CODE_LENGTH = 20;
	public static final int AUTO_GENERATED_PASSWORD = 15;
	public static final String TYPDOC_CIN = "2";

	public static final String ERROR_EMAIL_ALREADY_EXISTS = "ERROR_EMAIL_ALREADY_EXISTS";

	public static final String UNIQUE_IDENTIFIER_ALREADY_EXISTS = "UNIQUE_IDENTIFIER_ALREADY_EXISTS";

	public static final String REGISTRATION_NUMBER_ALREADY_EXISTS = "REGISTRATION_NUMBER_ALREADY_EXISTS";
	public static final String ERROR_IS_ACTIVE_ALREADY_EXISTS = "ERROR_IS_ACTIVE_ALREADY_EXISTS";

	public static final String PARTICIPATION_ALREADY_EXISTS = "PARTICIPATION_ALREADY_EXISTS";
	public static final String CURRENCY_FINANCING_ALREADY_EXISTS = "CURRENCY_FINANCING_ALREADY_EXISTS";

	public static final String PARTICIPATION_NOT_FOUND = "PARTICIPATION_NOT_FOUND";

	public static final String ERROR_MISSING_REQUIRED_DATA = "MISSING_REQUIRED_DATA";

	public static final String ERROR_INCOMPATIBLE_DATA = "ERROR_INCOMPATIBLE_DATA";

	public static final String ERROR_MSG_REPRESENTATIVE_MUST_BE_NULL = " : Representative must be null";

	public static final String ERROR_MISSING_CONFIRMATION_PASSWORD = "ERROR_MISSING_CONFIRMATION_PASSWORD";

	public static final String ERROR_MAX_FIELD_LENGTH = "ERROR_MIN_MAX_FIELD_LENGTH";

	public static final String INVALID_LOCALE = "INVALID_LOCALE";

	public static final String INVALID_IDENTIFICATION_TYPE = "INVALID_IDENTIFICATION_TYPE";
	public static final String ERROR_FORM_STATUS = "FORM_WITH_WRONG_STATUS";

	public static final String CELL_PHONE = "cellPhone";
	public static final String MATRICULE_FISCAL = "F";
	public static final String SOCIAL_REASON = "socialReason";

	public static final String NAME_FOR_FUND = "nameForFund";

	public static final String DECLARATION_CERTIFICATE_OF_DEPOSIT = "activitySupport.declarationCertificateOfDeposit";

	public static final String UNIQUE_ID = "uniqueId";
	public static final String UNIQUE_DENTIFICATION = "uniqueIdentification";
	public static final String ERROR_INVALID_PHONE_FORMAT = "INVALID_PHONE_FORMAT";
	public static final String ERROR_NOT_UNIQUE = "VALUE_NOT_UNIQUE";
	public static final String ERROR_ACTUAL_DATE = "ERROR_ACTUAL_DATE";
	public static final String ERROR_INVALID_EMAIL_FORMAT = "INVALID_EMAIL_FORMAT";
	public static final String ERROR_UNEXPECTED_NULL_VALUE = "UNEXPECTED_NULL_VALUE";
	public static final String INVESTORPART = "InvestorPart";
	public static final String AMOUNTTNDPROPERTYACQUITED = "amountTNDPropertyAcquitted";
	public static final String AMOUNTCASHCONTRIBUTED = "amountCashContributed";
	public static final String ERROR_IDENTIFICATION_TYPE_NOT_FOUND = "ERROR_IDENTIFICATION_TYPE_NOT_FOUND";
	public static final String ERROR_IDENTIFICATION_VALUE_NOT_FOUND = "ERROR_IDENTIFICATION_VALUE_NOT_FOUND";
	public static final String ERROR_IDENTIFICATION_TYPE_NOT_FOUND_REF = "ERROR_IDENTIFICATION_TYPE_NOT_FOUND_REF";
	public static final String ERROR_INVALID_IDENTIFICATION_VALUE = "INVALID_IDENTIFICATION_VALUE";
	public static final String REQUESTER_INFORMTION_IDENTIFICATION_VALUE = "uniqueIdentifier";
	public static final String CIN = "CIN";
	public static final String IDENTFIANT_UNIQUE = "IDENTIFIANT UNIQUE";
	public static final int NUMBER_USER_OF_TYPE_BANKER = 2;
	public static final String ADDRESS = "address";

	public static final String SPACE = " ";

	public static final String PP_NON_RESIDENT_TUNISIAN_LAB = "Personne physique non-résidente de nationalité tunisienne";
	public static final String PP_NON_RESIDENT_FOREIGN_LAB = "Personne physique non-résidente de nationalité étrangère";
	public static final String PM_NON_RESIDENT_TUNISIAN_LAB = "Personne morale non-résidente de nationalité tunisienne";

	public static final String PM_NON_RESIDENT_FOREIGN_LAB = "Personne morale non-résidente de nationalité étrangère";

	public static final String ERROR_INVALID_PAGE_OR_PAGE_CAPACITY_NUMBER = "INVALID_PAGE_OR_PAGE_CAPACITY_NUMBER";

	public static final String NUMBER_USER_OF_TYPE_ADMIN_BANKER_SUPERIOR = "NUMBER_USER_OF_TYPE_ADMIN_BANKER_SUPERIOR";

	public static final String CREATION_DATE = "creationDate";

	public static final String BANK_NOT_FOUND = "BANK_NOT_FOUND";

	public static final String AGENCY_NOT_FOUND = "AGENCY_NOT_FOUND";

	public static final String ERROR_IN_SIZE_FILE = "File size exceeds the allowed limit, and your file size is : ";
	public static final String DEPOSIT_TYPE_IS_NULL = "DEPOSIT_TYPE_IS_NULL";
	public static final String DEPOSIT_TYPE_CAN_NOT_BE_NULL_AT_THIS_STEP = "DEPOSIT_TYPE_CAN_NOT_BE_NULL_AT_THIS_STEP";

	public static final String CURRENCY_NOT_FOUND = "CURRENCY_NOT_FOUND";
	public static final String SPECIFIC_REFERENTIAL_NOT_FOUND = "SPECIFIC_REFERENTIAL_NOT_FOUND";

	public static final String SPECIFIC_REFERENTIAL_NOT_FOUND_WITH_ID = "SPECIFIC_REFERENTIAL_NOT_FOUND_WITH_ID : ";
	public static final String DIRECT_INVEST_REQUEST_NOT_FOUND = "DIRECT_INVEST_REQUEST_NOT_FOUND";
	public static final String NO_DIRECT_INVEST_REQUEST_FOUND = " fiche invest not found with id : ";

	public static final String ERROR_CONFLICT_RELATE_BANK = "ERROR_CONFLICT_RELATE_BANK";
	public static final String ERROR_CONFLICT_RELATE_AGENCY = "ERROR_CONFLICT_RELATE_AGENCY";

	public static final String ERROR_SAVING_ATTACHMENT = "PROBLEM_OCCURED_SAVING_ATTACHMENT";

	public static final String UNAUTHORIZED_MSG = "You are not authorized to manipulate this request";

	public static final String PDF = "pdf";
	public static final List<UserTypeEnum> USER_TYPE_ADMIN_BCT_BANKER = Arrays.asList(UserTypeEnum.BANKER,
			UserTypeEnum.BCT_ADMIN ,UserTypeEnum.BCT_AGENT);

	public static final List<UserTypeEnum> USER_TYPE_BCTS = Arrays.asList(UserTypeEnum.BCT_AGENT,
			UserTypeEnum.BCT_ADMIN);

	public static final List<UserTypeEnum> USER_TYPE_ADMIN_BCT = List.of(UserTypeEnum.BCT_ADMIN);

	public static final List<UserTypeEnum> USER_TYPE_BCT_ADMIN_AND_ADMIN_BANKER_BCT_AGENT = Arrays.asList(UserTypeEnum.BCT_ADMIN,
			UserTypeEnum.ADMIN_BANKER , UserTypeEnum.BCT_AGENT);
	public static final List<UserTypeEnum> USER_TYPE_BCT_ADMIN_AND_ADMIN_BANKER = Arrays.asList(UserTypeEnum.BCT_ADMIN,
			UserTypeEnum.ADMIN_BANKER);

	public static final List<UserTypeEnum> USER_TYPE_ADMIN_BANKER = List.of(UserTypeEnum.ADMIN_BANKER);

	public static final List<UserTypeEnum> USER_TYPE_BANKER = List.of(UserTypeEnum.BANKER);

	public static final List<UserTypeEnum> USER_TYPE_INVESTOR = List.of(UserTypeEnum.INVESTOR);

	public static final List<UserTypeEnum> USER_TYPE_BANKER_AND_INVESTOR = Arrays.asList(UserTypeEnum.BANKER,
			UserTypeEnum.INVESTOR);

	public static final String SECONDARY_ACTIVITY_SUPPORT_LIST = "secondaryActivitySupportList";
	public static final String MAIN_ACTIVITY_SUPPORT_LIST = "mainActivitySupportList";
	public static final String SA = "SA";

	public static final String COUNTRY_TUNISIA_ID = "788";

	public static final String FUNCTION = "function";

	public static final String DIRECTION = "direction";

	public static final String FIRST_NAME = "firstName";

	public static final String HOME_PHONE_NUMBER = "homePhoneNumber";

	public static final String PASSWORD = "password";

	public static final String LAST_NAME = "lastName";

	public static final String GENERAL_MANAGEMENT_ASSIGNMENT = "generalManagementAssignment";

	public static final String SERVICE_ASSIGNMENT = "serviceAssignment";

	public static final String LOGO = "logo";
	public static final String SECTIONS = "sections";
	public static final String DOCUMENTS = "documents";

	public static final String PASSPORT = "PASSPORT";
	public static final String IDENTIFIANT_UNIQUE = "IDENTIFIANT UNIQUE";

	public static final String TITLE = "title";
	public static final String TEMPLATE_INVESTMENT = "path.reporting";
	public static final String TEMPLATE_INVESTMENT_FILE = "investmentFile.jrxml";
	public static final String IMG_LOGO = "img/logo.png";
	public static final String QRCODE = "qrcode";
	public static final String INTER_INVEST_REQ = "intermediaireDossierInvestReq";
	public static final String BANK_ID = "bankId";
	public static final String BANK_LABEL = "bankLabel";
	public static final String NATURE_INVEST_REQ = "natureInvestReq";
	public static final String NATURE_INVEST_RES = "natureInvestRes";
	public static final String FORM_INVEST_REQ = "formeInvestReq";
	public static final String FORM_INVEST_RES = "formeInvestRes";
	public static final String PART_2_TITLE = "part2Title";
	public static final String UNIQUE_ID_COMPANY_REQ = "uniqueIdCompanyReq";
	public static final String UNIQUE_ID_COMPANY_RES = "uniqueIdCompanyRes";
	public static final String RAISON_SOCIAL_REQ = "raisonSocialReq";
	public static final String RAISON_SOCIAL_RES = "raisonSocialRes";
	public static final String ACTIVITY_SECTOR_REQ = "activitySectorReq";
	public static final String ACTIVITY_SECTOR_RES = "activitySectorRes";
	public static final String SOCIAL_CAP_REQ = "socialCapitalReq";
	public static final String SOCIAL_CAP_RES = "socialCapitalRes";
	public static final String ACT_SUP_REQ = "activitySupportTitle";
	public static final String ACT_SUP_RES = "activitySupportList";
	public static final String PART_3_TITLE = "part3Title";
	public static final String NUMBER_PARTS_INVEST_REQ = "numberPartsInvestReq";
	public static final String NUMBER_PARTS_INVEST_RES = "numberPartsInvestRes";
	public static final String FREE_AMOUNT_INVEST_REQ = "freeAmountInvestReq";
	public static final String FREE_AMOUNT_INVEST_RES = "freeAmountInvestRes";
	public static final String CURRENCY_DATA = "currencyData";
	public static final String PART_4_TITLE = "part4Title";
	public static final String CODE_INVESTOR = "codeInvestor";
	public static final String CODE_INVESTOR_REQ = "codeInvestorRes";
	public static final String NOM_INVESTOR_RES = "nomInvestorRes";
	public static final String NOM_INVESTOR = "nomInvestor";
	public static final String NAT_INVESTOR = "natInvestor";
	public static final String NAT_INVESTOR_RES = "natInvestorRes";
	public static final String ADRESS_INVESTOR = "adressInvestor";
	public static final String ADRESS_INVESTOR_RES = "adressInvestorRes";
	public static final String PART_5_TITLE = "part5Title";
	public static final String FICHE_INVEST_ID = "ficheInvestId";
	public static final String FICHE_INVEST_RES = "ficheInvestIdRes";
	public static final String VALIDE_DATE = "validateDate";
	public static final String VALIDE_DATE_RES = "validateDateRes";
	public static final String SIGNA = "signature";
	public static final String TEMPLATE_INVESTMENT_SHEET = "investmentSheet.jrxml";
	public static final String ACTIVITY_SUPPORT_DATE_SUPPORT_NOT_FOUND = "ACTIVITY SUPPORT DATE SUPPORT NOT FOUND";
	public static final String NO_CURRENCY_CESSION_DATE_FOUNDED_WITH_ID = "NO_CURRENCY_CESSION_DATE_FOUNDED_WITH_ID";
	public static final String CURRENCY_IMPRTATION_PIECE_NOT_FOUND = "CURRENCY_IMPRTATION_PIECE_NOT_FOUND";
	public static final String ACTIVITY_SECTOR_LABEL_NOT_FOUND = "ACTIVITY_SECTOR_LABEL_NOT_FOUND";

	/*
	 * private constructor
	 */
	private ApplicationConstants() {

	}
}
