package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.PersonType;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.rest.dto.InvestorForPostDto;
import com.wevioo.pi.rest.dto.RepresentativeForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class InvestorValidator implements Validator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		return InvestorForPostDto.class.equals(aClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object o, Errors errors) {
		/**
		 * overrided method
		 */
	}

	/**
	 * Validate Operator for post method
	 *
	 * @param investorForPostDto InvestorForPostDto
	 * @param errors             Errors
	 */
	public static void validatePost(InvestorForPostDto investorForPostDto, Errors errors) {

		if (CommonUtilities.isNull(investorForPostDto.getLanguage())) {
			errors.rejectValue("language", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		// email validation
		CommonsValidation.emailValidation("email", errors, investorForPostDto.getEmail(), 200);
		// phoneNumber validation
		CommonsValidation.validatePhoneNumber("cellPhone", errors, investorForPostDto.getCellPhone(), 15);
		// address validation
		validateAddress(investorForPostDto.getAddress(), errors);

		if (CommonUtilities.isNull(investorForPostDto.getInvestorType())) {
			errors.rejectValue("investorType", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		// ------------------------------------------
		// Specific validation for Physical Investor
		// ------------------------------------------
		if (investorForPostDto.getInvestorType() == PersonTypeEnum.PP_NON_RESIDENT_TUNISIAN
				|| investorForPostDto.getInvestorType() == PersonTypeEnum.PP_NON_RESIDENT_FOREIGN) {
			// firstname validation
			validateFirstAndLastName(investorForPostDto.getFirstName(), investorForPostDto.getLastName(), errors);
			if (investorForPostDto.getInvestorType() == PersonTypeEnum.PP_NON_RESIDENT_FOREIGN
					&& CommonUtilities.isNullOrEmpty(investorForPostDto.getNationality())) {
				errors.rejectValue("nationality", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}
			if (CommonUtilities.isNullOrEmpty(investorForPostDto.getCountryOfResidency())) {
				errors.rejectValue("countryOfResidency", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}
		}

		// ------------------------------------------
		// Specific validation for Moral Investor
		// ------------------------------------------
		if (investorForPostDto.getInvestorType() == PersonTypeEnum.PM_NON_RESIDENT_TUNISIAN
				|| investorForPostDto.getInvestorType() == PersonTypeEnum.PM_NON_RESIDENT_FOREIGN) {

			if (!investorForPostDto.isInvestmentFunds()) {
				// socialReason validation
				if (CommonUtilities.isNullOrEmpty(investorForPostDto.getSocialReason())) {
					errors.rejectValue(ApplicationConstants.SOCIAL_REASON, ApplicationConstants.BAD_REQUEST_CODE,
							ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
				} else if (!CommonUtilities.isValidSocialReason(investorForPostDto.getSocialReason())) {
					errors.rejectValue(ApplicationConstants.SOCIAL_REASON, ApplicationConstants.BAD_REQUEST_CODE,
							ApplicationConstants.ERROR_INVALID_SOCIAL_REASON_FORMAT);
				}

				// unique ID validation
				if (CommonUtilities.isNullOrEmpty(investorForPostDto.getUniqueId())) {
					errors.rejectValue(ApplicationConstants.UNIQUE_ID, ApplicationConstants.BAD_REQUEST_CODE,
							ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
				} 
				
			} else {
				// name for  fund  validation
				if (CommonUtilities.isNullOrEmpty(investorForPostDto.getNameForFund())) {
					errors.rejectValue(ApplicationConstants.NAME_FOR_FUND, ApplicationConstants.BAD_REQUEST_CODE,
							ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
				}
			}
			if (investorForPostDto.getInvestorType()== PersonTypeEnum.PM_NON_RESIDENT_FOREIGN
					&& CommonUtilities.isNullOrEmpty(investorForPostDto.getNationality())) {
				errors.rejectValue("nationality", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}

			if (CommonUtilities.isNullOrEmpty(investorForPostDto.getCountryOfResidency())) {
				errors.rejectValue("countryOfResidency", ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			}
		}
		if (investorForPostDto.getRepresentative() == null && investorForPostDto.isHasRepresentative()) {
			errors.rejectValue("representative", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		if (investorForPostDto.getRepresentative() != null && !investorForPostDto.isHasRepresentative()) {
			errors.rejectValue("representative", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_INCOMPATIBLE_DATA  +
					ApplicationConstants.ERROR_MSG_REPRESENTATIVE_MUST_BE_NULL);
		}
		CommonsValidation.validatePassword(investorForPostDto.getPassword(), "password", errors);

		if (errors.hasErrors()) {
			throw new BadRequestException("400", errors);
		}
	}

	public static void validateRepresentativePost(RepresentativeForPostDto representative, Errors errors) {
		// email validation
		CommonsValidation.emailValidation("representative.email", errors, representative.getEmail(), 200);
		// phoneNumber validation
		CommonsValidation.validatePhoneNumber("representative.cellPhone", errors, representative.getCellPhone(), 15);
		// address validation
		validateAddress(representative.getAddress(), errors);

		if (CommonUtilities.isNullOrEmpty(representative.getNationality())) {
			errors.rejectValue("representative.nationality", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}

		if (CommonUtilities.isNullOrEmpty(representative.getCountryOfResidency())) {
			errors.rejectValue("representative.countryOfResidency", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		if (CommonUtilities.isNull(representative.getRepresentativeType())) {
			errors.rejectValue("representative.representativeType", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		}
		if (CommonUtilities.compareTo(representative.getRepresentativeType().name(), PersonType.PP.name())) {
			validateFirstAndLastName(representative.getFirstName(), representative.getLastName(), errors);
		}
		if (CommonUtilities.compareTo(representative.getRepresentativeType().name(), PersonType.PM.name())) {
			if (CommonUtilities.isNullOrEmpty(representative.getSocialReason())) {
				errors.rejectValue(ApplicationConstants.SOCIAL_REASON, ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
			} else if (!CommonUtilities.isValidSocialReason(representative.getSocialReason())) {
				errors.rejectValue(ApplicationConstants.SOCIAL_REASON, ApplicationConstants.BAD_REQUEST_CODE,
						ApplicationConstants.ERROR_INVALID_SOCIAL_REASON_FORMAT);
			}
		}
		if (errors.hasErrors()) {
			throw new BadRequestException("400", errors);
		}

	}

	private static void validateAddress(String address, Errors errors) {
		if (CommonUtilities.isNullOrEmpty(address)) {
			errors.rejectValue(ApplicationConstants.ADDRESS, ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		} else if (!CommonUtilities.isValidLength(address, 150)) {
			errors.rejectValue(ApplicationConstants.ADDRESS, ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
		}
	}

	public static void validateFirstAndLastName(String firstName, String lasName, Errors errors) {
		if (CommonUtilities.isNullOrEmpty(firstName)) {
			errors.rejectValue("firstName", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		} else if (!CommonUtilities.isValidLength(firstName, 50)) {
			errors.rejectValue("firstName", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
		}
		if (CommonUtilities.isNullOrEmpty(lasName)) {
			errors.rejectValue("lastName", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
		} else if (!CommonUtilities.isValidLength(lasName, 50)) {
			errors.rejectValue("lastName", ApplicationConstants.BAD_REQUEST_CODE,
					ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
		}
	}
}
