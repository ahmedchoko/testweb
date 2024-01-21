
package com.wevioo.pi.rest.resources;

import java.io.IOException;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.EmailSendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.rest.dto.DirectInvestRequestStepFourForPostDto;
import com.wevioo.pi.rest.dto.DirectInvestToStepFive;
import com.wevioo.pi.rest.dto.ValidateDirectInvestByBanker;
import com.wevioo.pi.rest.dto.request.CurrencyFinancingForPostDto;
import com.wevioo.pi.rest.dto.request.InvestIdentificationForPostDto;
import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.rest.dto.request.RequestStepSixForPost;
import com.wevioo.pi.rest.dto.response.CurrencyFinancingForGetDto;
import com.wevioo.pi.rest.dto.response.DirectInvestRequestForGet;
import com.wevioo.pi.rest.dto.response.DirectInvestStepOneForGet;
import com.wevioo.pi.rest.dto.response.InvestIdentificationForGetDto;
import com.wevioo.pi.rest.dto.response.ParticipationIdentificationStepTwoForGetDto;
import com.wevioo.pi.rest.dto.response.RequestStepSixForGet;
import com.wevioo.pi.service.CodificationService;
import com.wevioo.pi.service.CurrencyFinancingService;
import com.wevioo.pi.service.DirectInvestRequestService;
import com.wevioo.pi.service.InvestIdentificationService;
import com.wevioo.pi.service.ParticipationIdentificationService;

import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/directInvestRequest")
@Slf4j
public class DirectInvestRequestController {
	/**
	 * Injected bean {@link DirectInvestRequestService}
	 */
	@Autowired
	DirectInvestRequestService directInvestRequestService;
	/**
	 * Injected bean {@link InvestIdentificationService}
	 */
	@Autowired
	InvestIdentificationService investIdentificationService;
	/**
	 * Injected bean {@link ParticipationIdentificationService}
	 */
	@Autowired
	ParticipationIdentificationService participationIdentificationService;
	/**
	 * Injected bean {@link CurrencyFinancingService}
	 */
	@Autowired
	CurrencyFinancingService currencyFinancingService;
	/**
	 * Injected bean {@link CodificationService}
	 */
	@Autowired
	CodificationService codificationService;

	/**
	 * Endpoint to create or update a requester as part of step zero.
	 *
	 * @param directInvestForPostDto The data object containing requester
	 *                               information to be saved or updated.
	 * @return The DTO representation of the saved or updated requester.
	 */
	@PostMapping(value = "/stepZero", consumes = "multipart/form-data")
	public DirectInvestStepOneForGet saveOrUpdateStepZero(@RequestPart(name = "data") String directInvestForPostDto,
			@RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
		return directInvestRequestService.saveDirectInvestStepZero(directInvestForPostDto, file);
	}

	/**
	 * Endpoint to find a requester by its ID as part of step zero.
	 *
	 * @param id The ID of the requester to be found.
	 * @return The DTO representation of the requester found by the provided ID.
	 */
	@GetMapping("/stepZero/{id}")
	public DirectInvestStepOneForGet findStepZeroById(@PathVariable String id) {
		return directInvestRequestService.findById(id);
	}

	/**
	 * Endpoint to save a new direct invest request on step one.
	 *
	 * @param stepOne The DirectInvestRequestStepOneForPostDto object containing
	 *                information for creating a new direct invest request
	 * @param result  The BindingResult object to handle validation errors
	 * @return saved
	 */

	@PostMapping("/stepOne")
	public InvestIdentificationForGetDto saveDirectInvestRequestStepOne(
			@RequestBody InvestIdentificationForPostDto stepOne, BindingResult result) {
		log.debug(" Start adding  direct invest request  step one  ....");
		return investIdentificationService.saveStepOne(stepOne, result);
	}

	/**
	 * Retrieves InvestIdentificationForGetDto based on provided ID for step one.
	 *
	 * @param id The unique identifier used to find the
	 *           InvestIdentificationForGetDto.
	 * @return The InvestIdentificationForGetDto corresponding to the provided ID.
	 */

	@GetMapping("/stepOne/{id}")
	public InvestIdentificationForGetDto findStepOneById(@PathVariable String id) {
		return investIdentificationService.findById(id);
	}

	/**
	 * Saves a direct investment request for step two.
	 *
	 * @param requestStepTwo The data for Step Two of the participation
	 *                       identification process.
	 * @param result         The binding result for validation.
	 * @return The ParticipationIdentificationStepTwoForGetDto after saving the
	 *         request.
	 */
	@PostMapping("/stepTwo")
	public ParticipationIdentificationStepTwoForGetDto saveDirectInvestRequestStepTwo(
			@RequestBody ParticipationIdentificationStepTwoForPostDto requestStepTwo, BindingResult result) {
		log.info(" Start adding  direct invest request  step  two  ....");
		return participationIdentificationService.saveParticipationIdentification(requestStepTwo, result);
	}

	@GetMapping("/stepTwo/{id}")
	public ParticipationIdentificationStepTwoForGetDto findStepTwoById(@PathVariable String id) {
		log.info(" Start  find   step  two  ....");
		return participationIdentificationService.findStepTwoById(id);
	}

	/**
	 * Saves a direct investment request for step three.
	 *
	 * @param requestStepThree The data for Step Three of the direct investment
	 *                         request.
	 * @param result           The binding result for validation.
	 * @return The CurrencyFinancingForGetDto after saving the Step Three request.
	 */
	@PostMapping("/stepThree")
	public CurrencyFinancingForGetDto saveDirectInvestRequestStepThree(
			@RequestBody CurrencyFinancingForPostDto requestStepThree, BindingResult result) {
		log.info(" Start adding  direct invest request  step   three  ....");
		return currencyFinancingService.saveCurrencyFinancingDirectInvest(requestStepThree, result);
	}

	/**
	 * Retrieves CurrencyFinancingForGetDto based on the provided ID for step three.
	 *
	 * @param id The unique identifier used to find the CurrencyFinancingForGetDto
	 *           for step three.
	 * @return The CurrencyFinancingForGetDto corresponding to the provided ID for
	 *         step three.
	 */
	@GetMapping("/stepThree/{id}")
	public CurrencyFinancingForGetDto findStepThreeById(@PathVariable String id) {
		log.info(" Start  find   step  three  ....");
		return currencyFinancingService.getCurrencyFinancingInformationById(id);
	}

	/**
	 * Retrieves the DirectInvestToStepFive object by its ID.
	 *
	 * @param id The ID of the DirectInvestToStepFive object to retrieve.
	 * @return DirectInvestToStepFive object corresponding to the provided ID.
	 */
	@GetMapping("/stepFive/{id}")
	public DirectInvestToStepFive findStepFiveById(@PathVariable String id) {
		log.info(" Start  find   step   Five  ....");
		return directInvestRequestService.toStepFive(id);
	}

	/**
	 * Endpoint to save step six of direct investment.
	 *
	 * @param stepSixForPost The DirectInvestStepSixForPost object containing data
	 *                       for step six
	 * @param result         The BindingResult object for validation result
	 * @return DirectInvestRequestForGet object after saving step six
	 */

	@PostMapping("/stepSix")
	public RequestStepSixForGet saveStepSix(@RequestBody RequestStepSixForPost stepSixForPost, BindingResult result) {
		try {
			return directInvestRequestService.saveDirectInvestStepSix(stepSixForPost, result);
		} catch (MessagingException e) {
			throw new EmailSendingException(ApplicationConstants.ERROR_EMAIL_SEND  , "Error sending Email");
		}
	}

	/**
	 * Endpoint to find step six of direct investment by ID.
	 *
	 * @param id The ID to search for the DirectInvestRequestForGet object
	 * @return DirectInvestRequestForGet object found by ID, or null if not found
	 */

	@GetMapping("/stepSix/{id}")
	public RequestStepSixForGet saveStepSix(@PathVariable String id) {
		return directInvestRequestService.findStepSixById(id);
	}

	/**
	 * Save/ update a direct investment request for step four.
	 *
	 * @param directInvestRequestStepFourForPostDto The data for Step four
	 *                                              DirectInvestRequestStepFourForPostDto
	 * @param result                                The binding result for
	 *                                              validation.
	 * @return The DirectInvestRequestForGet after saving the bank, agency and
	 *         dopsitType.
	 */
	@PostMapping("/stepFour")
	public DirectInvestRequestForGet saveDirectInvestRequestStepTwo(
			@RequestBody DirectInvestRequestStepFourForPostDto directInvestRequestStepFourForPostDto,
			BindingResult result) {
		log.info(" Start adding/ updating  direct invest request  step  four  ....");
		return directInvestRequestService.saveDirectInvestStepFour(directInvestRequestStepFourForPostDto, result);
	}

	/**
	 * Find step four information by given parameter: ID.
	 *
	 * @param id The ID to search for the DirectInvestRequestForGet object
	 * @return DirectInvestRequestForGet object found by ID, or null if not found
	 */

	@GetMapping("/stepFour/{id}")
	public DirectInvestRequestForGet getStepFour(@PathVariable String id) {
		return directInvestRequestService.findStepFourById(id);
	}

	/**
	 * validate Direct Invest By Banker
	 * 
	 * @param request
	 * @param result
	 * @return ValidateDirectInvestByBanker
	 */
	@PostMapping("/banker/validate")
	public ValidateDirectInvestByBanker validateDirectInvestByBanker(@RequestBody ValidateDirectInvestByBanker request,
			BindingResult result) {
		log.info(" validate Direct Invest By Banker....");
		return directInvestRequestService.validateDirectInvestByBanker(request, result);
	}


	/**
	 * Find step four information by given parameter: ID.
	 *
	 * @param id The ID to search for the DirectInvestRequestForGet object
	 * @return DirectInvestRequestForGet object found by ID, or null if not found
	 */

	@DeleteMapping("/{id}")
	public void deleteDirectInvest(@PathVariable String id) {
		directInvestRequestService.deleteDirectInvest(id);
	}

	/**
	 * Update status
	 * 
	 * @param idForm        form's id
	 * @param operationType OperationType
	 * @param requestStatus RequestStatusEnum
	 */
	@PatchMapping("/{idForm}/operationType/{operationType}/status/{requestStatus}")
	public void updateRequestStaus(@PathVariable String idForm, @PathVariable OperationType operationType,
			@PathVariable RequestStatusEnum requestStatus) {
		directInvestRequestService.complete(operationType, idForm,requestStatus );
	}

}
