package com.wevioo.pi.service;

import java.io.IOException;

import com.wevioo.pi.rest.dto.ValidateDirectInvestByBanker;
import com.wevioo.pi.rest.dto.request.AffectedToBankerDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.rest.dto.DirectInvestRequestStepFourForPostDto;
import com.wevioo.pi.rest.dto.DirectInvestToStepFive;
import com.wevioo.pi.rest.dto.request.RequestStepSixForPost;
import com.wevioo.pi.rest.dto.response.DirectInvestRequestForGet;
import com.wevioo.pi.rest.dto.response.DirectInvestStepOneForGet;
import com.wevioo.pi.rest.dto.response.RequestStepSixForGet;
import com.wevioo.pi.rest.dto.response.RequesterForGetDto;

import javax.mail.MessagingException;

public interface DirectInvestRequestService {

	/**
	 * Saves a direct invest request using the provided
	 * directInvestRequestForPostDto.
	 *
	 * @param directInvestRequestStepZeroForPostDto The directInvestRequestDto
	 *                                              object containing information
	 *                                              for creating a new direct Invest
	 *                                              Request handle validation errors
	 * @return DirectInvestRequestStepOneForPostDto representing the saved direct
	 *         invest request if successful, null otherwise
	 */

	DirectInvestStepOneForGet saveDirectInvestStepZero(String directInvestRequestStepZeroForPostDto, MultipartFile file)
			throws IOException;

	/**
	 * Search by given parameter : ID
	 *
	 * @param id DirectInvestRequest's id
	 * @return {@link RequesterForGetDto}
	 */
	DirectInvestStepOneForGet findById(String id);

	/**
	 * find step 5 by fiche invest id :
	 * 
	 * @param ficheInvestId fiche invest id
	 * @return DirectInvestToStepFive DirectInvest T oStep Five
	 */
	DirectInvestToStepFive toStepFive(String ficheInvestId);

	/**
	 * Saves DirectInvestStepSixForPost object as DirectInvestRequestForGet for the
	 * step six of direct investment.
	 *
	 * @param stepSixForPost The DirectInvestStepSixForPost object containing data
	 *                       for step six
	 * @param result         The BindingResult object for validation result
	 * @return DirectInvestRequestForGet object after saving step six
	 */
	RequestStepSixForGet saveDirectInvestStepSix(RequestStepSixForPost stepSixForPost , BindingResult result) throws MessagingException;

	/**
	 * Finds DirectInvestRequestForGet object by its ID.
	 *
	 * @param id The ID to search for the DirectInvestRequestForGet object
	 * @return DirectInvestRequestForGet object found by ID, or null if not found
	 */
	RequestStepSixForGet findStepSixById(String id);

	/**
	 * Save or update step four
	 * 
	 * @param directInvestRequestStepFourForPostDto DirectInvestRequestStepFourForPostDto
	 * @param result                                BindingResult
	 * @return DirectInvestRequestForGet
	 */
	DirectInvestRequestForGet saveDirectInvestStepFour(
			DirectInvestRequestStepFourForPostDto directInvestRequestStepFourForPostDto, BindingResult result);

	/**
	 * Finds Step for object by its ID.
	 *
	 * @param id The ID to search for the DirectInvestRequest
	 * @return DirectInvestRequestForGet object found by ID, or null if not found
	 */
	DirectInvestRequestForGet findStepFourById(String id);

	/**
	 * validate Direct Invest By Banker
	 * 
	 * @param validateDirectInvestByBanker
	 * @param result
	 * @return ValidateDirectInvestByBanker
	 */
	ValidateDirectInvestByBanker validateDirectInvestByBanker(ValidateDirectInvestByBanker validateDirectInvestByBanker,
			BindingResult result);


	/**
	 * Delete DirectInvest
	 * 
	 * @param id DirectInvest's ID
	 */
	void deleteDirectInvest(String id);

	/**
	 * Complete draft Form
	 * 
	 * @param type   OperationType
	 * @param idFrom idFrom
	 * @param requestStatus RequestStatusEnum
	 */
	void complete(OperationType type, String idFrom, RequestStatusEnum requestStatus);

}
