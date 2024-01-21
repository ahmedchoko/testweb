package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Participation Identification Validator class
 *
 * @author knh
 *
 */

@Component
public class ParticipationIdentificationValidator {

	/**
	 * Private Constructor
	 */

	private ParticipationIdentificationValidator() {
		super();
	}

	/**
	 * validate Step Two For Post
	 *
	 * @param requestStepTwo {@link ParticipationIdentificationStepTwoForPostDto}
	 * @param errors         {@link Errors}
	 * @param isLegalFormSa isLegalFormUsa
	 * @param isLegalFormSarl isLegalFormUsa
	 *@param  isLegalFormScact isLegalFormScact
	 *
	 */
	public static void validateStepTwoForPost(ParticipationIdentificationStepTwoForPostDto requestStepTwo, OperationType operationType, Boolean isQuoted,
											  Errors errors, Boolean isLegalFormSa , Boolean isLegalFormSarl , Boolean isLegalFormSuarl ,Boolean isLegalFormScact) {
		validateCommonFields(requestStepTwo, errors);

		if (OperationType.COMPANY_CREATION.equals(operationType)) {
			validateCompanyCreationFields(requestStepTwo, errors ,isLegalFormSa);
		}

		if (OperationType.ACQUISITION_SHARES.equals(operationType)) {
			validateAcquisitionSharesFields(requestStepTwo,isQuoted, errors,isLegalFormSa ,isLegalFormSarl,isLegalFormSuarl ,isLegalFormScact);
		}

		if (OperationType.CAPITAL_INCREASE.equals(operationType)) {
			validateCapitalIncreaseFields(requestStepTwo, errors,isLegalFormSa);
		}

		if (errors.hasErrors()) {
			throw new BadRequestException("400", errors);
		}
	}


	private static void validateCommonFields(ParticipationIdentificationStepTwoForPostDto requestStepTwo,Errors errors) {
		// Validation logic for common fields
		if (CommonUtilities.isNullOrEmpty(requestStepTwo. getFicheInvestId())) {
			errors.rejectValue("ficheInvestId", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNull(requestStepTwo.getSocialCapital())) {
			errors.rejectValue("socialCapital", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		if (CommonUtilities.isNull(requestStepTwo.getNominalValue())) {
			errors.rejectValue("nominalValue", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		if (CommonUtilities.isNull(requestStepTwo.getNumberAction())) {
			errors.rejectValue("numberAction", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
	}
	private static void validatePaidCapitalByTranche(ParticipationIdentificationStepTwoForPostDto requestStepTwo, Errors errors) {
		if (CommonUtilities.isNull(requestStepTwo.getPaidCapitalByTranche())) {
			errors.rejectValue("paidCapitalByTranche", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		} else if (Boolean.TRUE.equals(requestStepTwo.getPaidCapitalByTranche())) {
			validateQuestFirstTranche(requestStepTwo, errors);
		}
	}

	private static void validateQuestFirstTranche(ParticipationIdentificationStepTwoForPostDto requestStepTwo, Errors errors) {
		if (CommonUtilities.isNull(requestStepTwo.getQuestFirstTranche())) {
			errors.rejectValue("questFirstTranche", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		} else if (Boolean.FALSE.equals(requestStepTwo.getQuestFirstTranche())) {
			validateReferenceDeclarationAndDate(requestStepTwo, errors);
		}
	}

	private static void validateReferenceDeclarationAndDate(ParticipationIdentificationStepTwoForPostDto requestStepTwo, Errors errors) {
		if (CommonUtilities.isNullOrEmpty(requestStepTwo.getReferenceDeclaration())) {
			errors.rejectValue("referenceDeclaration", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNullOrEmptyDate(requestStepTwo.getDateDeclaration())) {
			errors.rejectValue("dateDeclaration", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
	}

	private static void validateCompanyCreationFields(ParticipationIdentificationStepTwoForPostDto requestStepTwo, Errors errors ,Boolean isLegalFormSa) {
		// Validation logic specific to company creation
			if (CommonUtilities.isNull(requestStepTwo.getFreeCapital())) {
				errors.rejectValue("freeCapital", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}

			if (CommonUtilities.isNull(requestStepTwo.getParticipationRate())) {
				errors.rejectValue("participationRate", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}

			if (CommonUtilities.isNull(requestStepTwo.getContributionAmount())) {
				errors.rejectValue("contributionAmount", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}

			if (CommonUtilities.isNull(requestStepTwo.getNumberPart())) {
				errors.rejectValue("numberPart", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}
		if (Boolean.TRUE.equals(isLegalFormSa)) {
			validatePaidCapitalByTranche(requestStepTwo,errors);
		}

	}

	private static void validateAcquisitionSharesFields(ParticipationIdentificationStepTwoForPostDto requestStepTwo,Boolean isQuoted, Errors errors , Boolean isLegalFormSa ,Boolean isLegalFormSarl , Boolean isLegalFormSuarl ,Boolean isLegalFormScact) {
		// Validation logic specific to acquisition of shares
		if (CommonUtilities.isNull(requestStepTwo.getNumberActionAcquired())) {
			errors.rejectValue("numberActionAcquired", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNull(requestStepTwo.getUnitAcquisitionCost())) {
			errors.rejectValue("unitAcquisitionCost", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNull(requestStepTwo.getTotalAmountAcquisition())) {
			errors.rejectValue("totalAmountAcquisition", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (Boolean.TRUE.equals(isLegalFormSa)) {
			if (Boolean.TRUE.equals(isQuoted)) {
				if (CommonUtilities.isNullOrEmptyDate(requestStepTwo.getTransactionNoticeDate()))
                    errors.rejectValue("transactionNoticeDate", ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }else{
				if (CommonUtilities.isNullOrEmptyDate(requestStepTwo.getRegistrationCertificateDate())) {
				errors.rejectValue("registrationCertificateDate", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}
			}

		}

		if ((Boolean.TRUE.equals(isLegalFormSarl)|| Boolean.TRUE.equals(isLegalFormSarl) || Boolean.TRUE.equals(isLegalFormScact))
				&& CommonUtilities.isNullOrEmptyDate(requestStepTwo.getAcquisitionContractRegistrationDate())) {
			errors.rejectValue("acquisitionContractRegistrationDate", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

	}

	private static void validateCapitalIncreaseFields(ParticipationIdentificationStepTwoForPostDto requestStepTwo, Errors errors , Boolean isLegalFormUsa) {
		// Validation logic specific to capital increase
		if (CommonUtilities.isNull(requestStepTwo.getTotalAmount())) {
			errors.rejectValue("totalAmount", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNull(requestStepTwo.getReleasedAmount())) {
			errors.rejectValue("releasedAmount", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (Boolean.TRUE == isLegalFormUsa) {
			validatePaidCapitalByTranche(requestStepTwo,errors);
		}

		if (Boolean.TRUE.equals(requestStepTwo.getMethodIncrease())) {

			validateMethodIncreaseTrue(requestStepTwo, errors);

		} else {
			validateMethodIncreaseFalse(requestStepTwo, errors) ;
		}
	}
	private static void validateMethodIncreaseTrue(ParticipationIdentificationStepTwoForPostDto requestStepTwo, Errors errors){
		if (CommonUtilities.isNull(requestStepTwo.getNominalValueInC())) {
			errors.rejectValue("nominalValueInC", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNull(requestStepTwo.getContributionAmount())) {
			errors.rejectValue("contributionAmount", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNull(requestStepTwo.getNumberPartInvestor())) {
			errors.rejectValue("numberPartInvestor", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
	}



	private static void validateMethodIncreaseFalse(ParticipationIdentificationStepTwoForPostDto requestStepTwo, Errors errors){

		if (CommonUtilities.isNull(requestStepTwo.getTotalNumberIssued())) {
			errors.rejectValue("totalNumberIssued", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		if (CommonUtilities.isNull(requestStepTwo.getInvestedAmountInC())) {
			errors.rejectValue("investedAmountInC", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNull(requestStepTwo.getNumberPartInvestor())) {
			errors.rejectValue("numberPartInvestor", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNull(requestStepTwo.getPartTotalInvestorNumber())) {
			errors.rejectValue("partTotalInvestorNumber", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNull(requestStepTwo.getTotalAmountInvested())) {
			errors.rejectValue("totalAmountInvested", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
	}
}
