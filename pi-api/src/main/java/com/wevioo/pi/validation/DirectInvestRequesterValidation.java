package com.wevioo.pi.validation;

import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.rest.dto.ValidateDirectInvestByBanker;
import com.wevioo.pi.rest.dto.request.AffectedToBankerDto;
import com.wevioo.pi.rest.dto.request.CurrencyInvestmentFinancingDto;
import com.wevioo.pi.rest.dto.request.FicheInvestDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.DepositType;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.DirectInvestRequestStepFourForPostDto;
import com.wevioo.pi.rest.dto.request.DirectInvestRequestStepZeroForPostDto;
import com.wevioo.pi.rest.dto.request.RequestStepSixForPost;
import com.wevioo.pi.utility.CommonUtilities;

/**
 * Requester validation class
 *
 *
 */
@Component
public class DirectInvestRequesterValidation {

	private DirectInvestRequesterValidation() {
		super();
	}

	public static void validateDirectInvestRequester(DirectInvestRequestStepZeroForPostDto requester, Errors errors) {
		// Validate OperationType to avoid the NullPointerException
		if (requester.getOperationType() == null) {
			errors.rejectValue("operationType", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		if (CommonUtilities.isNull(requester.getHasRequester())) {
			errors.rejectValue("hasRequester", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		} else if (Boolean.TRUE.equals(requester.getHasRequester()) && requester.getRequester() == null) {
			errors.rejectValue("requester", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);

		}
		if (errors.hasErrors()) {
			throw new BadRequestException("400", errors);
		}
	}

	public static void validateDirectInvestByBanker(ValidateDirectInvestByBanker requester, Errors errors) {

		if (CommonUtilities.isNullOrEmpty(requester. getFicheInvestId())) {
			errors.rejectValue("ficheInvestId", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		if(CommonUtilities.isNull(requester.getLanguage())) {
			errors.rejectValue("language", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if(errors.hasErrors()) {
			throw new BadRequestException("400", errors);
		}
	}

	public static void validateAffectedDirectInvestToBanker(AffectedToBankerDto affectedToBankerDto, Errors errors) {

		if (affectedToBankerDto.getFicheInvestDtoList() == null ||
				affectedToBankerDto.getFicheInvestDtoList().isEmpty()) {
			errors.rejectValue("ficheInvestId", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		if(CommonUtilities.isNullOrEmpty(affectedToBankerDto.getBankerId())) {
			errors.rejectValue("bankerId", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		for (int i = 0; i < affectedToBankerDto.getFicheInvestDtoList().size(); i++) {

			FicheInvestDto ficheInvestDto = affectedToBankerDto.getFicheInvestDtoList().get(i);


			if (CommonUtilities.isNullOrEmpty(ficheInvestDto.getReference())) {
				errors.rejectValue("ficheInvestDtoList[" + i + "].ficheInvestId",
						ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}  /* Validation called if current User is a Banker */

			if (CommonUtilities.isNull(ficheInvestDto.getFormInvest())) {
				errors.rejectValue("ficheInvestDtoList[" + i + "].operationType",
						ApplicationConstants.BAD_REQUEST_CODE, ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}
		}

		if(errors.hasErrors()) {
			throw new BadRequestException("400", errors);
		}
	}
	public static void validateDirectInvestStepSix(RequestStepSixForPost requester, Errors errors) {

		if (CommonUtilities.isNullOrEmpty(requester.getBankId())) {
			errors.rejectValue("bankId", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if(CommonUtilities.isNullOrEmpty(requester.getAgencyId())) {
			errors.rejectValue("agencyId", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if(CommonUtilities.isNullOrEmpty(requester.getAgencyId())) {
			errors.rejectValue("language", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if(CommonUtilities.isNullOrEmpty(requester.getFicheInvestId())) {
			errors.rejectValue("ficheInvestId", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}


		if(CommonUtilities.isNull(requester.getExamineAcceptance())) {
			errors.rejectValue("examineAcceptance", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (errors.hasErrors()) {
			throw new BadRequestException("400", errors);
		}
	}
	
	/**
	 * Validate in put for four Step
	 * 
	 * @param directInvestRequestStepFourForPostDto DirectInvestRequestStepFourForPostDto
	 * @param errors                                Errors
	 */
	public static void validateDirectInvestFourStep(
			DirectInvestRequestStepFourForPostDto directInvestRequestStepFourForPostDto, Errors errors) {

		if (CommonUtilities.isNullOrEmpty(directInvestRequestStepFourForPostDto.getFicheInvestId())) {
			errors.rejectValue("ficheInvestId", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (ObjectUtils.isEmpty(directInvestRequestStepFourForPostDto.getDepositType())) {
			errors.rejectValue("depositType", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (!ObjectUtils.isEmpty(directInvestRequestStepFourForPostDto.getDepositType())
				&& directInvestRequestStepFourForPostDto.getDepositType() == DepositType.DIRECT_DEPOSIT) {
			if (CommonUtilities.isNullOrEmpty(directInvestRequestStepFourForPostDto.getBank())) {
				errors.rejectValue("bank", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}

			if (CommonUtilities.isNullOrEmpty(directInvestRequestStepFourForPostDto.getAgency())) {
				errors.rejectValue("agency", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}
		}

		if (errors.hasErrors()) {
			throw new BadRequestException("400", errors);
		}
	}
}
