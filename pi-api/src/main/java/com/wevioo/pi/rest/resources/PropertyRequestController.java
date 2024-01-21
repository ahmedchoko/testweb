package com.wevioo.pi.rest.resources;


import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.EmailSendingException;
import com.wevioo.pi.rest.dto.request.AuthorizationsRequiredForPostDto;
import com.wevioo.pi.rest.dto.request.MainContractualTermsStepTwoForPutDto;
import com.wevioo.pi.rest.dto.request.CurrencyFinancingForPostDto;
import com.wevioo.pi.rest.dto.request.PropertyDescriptionForGetDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepOneForGetDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepOneForPostDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepZeroForGetDto;
import com.wevioo.pi.rest.dto.request.RequestStepSixForPost;
import com.wevioo.pi.rest.dto.request.PropertyRequestToSummaryDto;
import com.wevioo.pi.rest.dto.response.AuthorizationRequiredForGetDto;
import com.wevioo.pi.rest.dto.response.CurrencyFinancingForGetDto;
import com.wevioo.pi.rest.dto.response.MainContractualTermsStepTwoForGetDto;
import com.wevioo.pi.rest.dto.response.RequestStepSixForGet;
import com.wevioo.pi.service.AuthorizationRequiredService;
import com.wevioo.pi.service.CurrencyFinancingService;
import com.wevioo.pi.service.MainContractualTermsService;
import com.wevioo.pi.service.PropertyRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Property Request Controller
 */
@RestController
@RequestMapping("/propertyRequest")
@Slf4j
public class PropertyRequestController {

    /**
     * Injected bean {@link MainContractualTermsService}
     */
    @Autowired
    MainContractualTermsService mainContractualTermsService ;

    /**
     * Injected bean {@link AuthorizationRequiredService}
     */
    @Autowired
    AuthorizationRequiredService authorizationRequiredService ;

    /**
     * Injected bean {@link PropertyRequestService}
     */
    @Autowired
    PropertyRequestService propertyRequestService;

    /**
     * Injected bean {@link CurrencyFinancingService}
     */
    @Autowired
    CurrencyFinancingService currencyFinancingService;

    /**
     * Saves property request step zero.
     *
     * @param requestStepZero The data for property request step zero.
     * @param file            The file associated with the request (optional).
     * @return PropertyRequestStepZeroForGetDto containing saved information.
     * @throws IOException If an I/O exception occurs.
     */

    @PostMapping(value = "/stepZero", consumes = "multipart/form-data")
    public PropertyRequestStepZeroForGetDto savePropertyRequestStepZero(@RequestPart(name= "data")  String requestStepZero ,
                                                                        @RequestPart(name = "file" , required = false)   MultipartFile file  ) throws IOException {
        log.info(" Start adding  property request  step zero  ....");
        return propertyRequestService.savePropertyRequestStepZero(requestStepZero, file);
    }

    /**
     * Retrieves property request step zero information by ID.
     *
     * @param id The ID of the property request.
     * @return PropertyRequestStepZeroForGetDto containing step zero information.
     */
    @GetMapping("/stepZero/{id}")
    public PropertyRequestStepZeroForGetDto getPropertyRequestStepZero(@PathVariable("id") String id ){
        log.debug(" Start getting  property request  step zero  ....");
        return propertyRequestService.getPropertyRequestStepZeroInformation(id);
    }
    /**
     * POST method to save Step One of a property request.
     *
     * @param requestStepOne The data for Step One to be saved.
     * @param result         The binding result for validation.
     * @return The saved Step One data for the property request.
     */
    @PostMapping("/stepOne")
    public PropertyDescriptionForGetDto savePropertyRequestStepOne(@RequestBody PropertyRequestStepOneForPostDto requestStepOne, BindingResult result) {
        log.debug(" Start adding  property request  step one  ....");
        return propertyRequestService.savePropertyRequestStepOne(requestStepOne, result);
    }

    /**
     * GET method to retrieve Step One data for a property request by its ID.
     *
     * @param id The ID of the property request.
     * @return The Step One data for the specified property request.
     */
    @GetMapping("/stepOne/{id}")
    public PropertyRequestStepOneForGetDto getPropertyRequestStepOne(@PathVariable("id") String id) {
        log.debug(" Start getting  property request  step one  ....");
        return propertyRequestService.getPropertyRequestStepOneInformation(id);
    }

    /**
     * POST method to save Step Two of a property request.
     *
     * @param requestStepTwo The data for Step Two to be saved or modified.
     * @param result         The binding result for validation.
     * @return The saved or modified Step Two data for the property request.
     */
    @PostMapping("/stepTwo")
    public MainContractualTermsStepTwoForGetDto savePropertyRequestStepTwo(@RequestBody MainContractualTermsStepTwoForPutDto requestStepTwo, BindingResult result) {
        log.debug(" Start adding  property request  step two or modifying it ....");
        return mainContractualTermsService.saveMainContractualTerms(requestStepTwo, result);
    }

    /**
     * GET method to retrieve Step Two data for a property request by its ID.
     *
     * @param id The ID of the property request.
     * @return The Step Two data for the specified property request.
     */
    @GetMapping("/stepTwo/{id}")
    public MainContractualTermsStepTwoForGetDto getPropertyRequestStepTwo(@PathVariable("id") String id) {
        log.debug(" Start getting  property request  step two  ....");
        return mainContractualTermsService.getMainContractualTerms(id);
    }

    /**
     * POST method to save Step Three of a property request.
     *
     * @param requestStepThree The data for Step Three to be saved or modified.
     * @param result           The binding result for validation.
     * @return The saved or modified Step Three data for the property request.
     */
    @PostMapping("/stepThree")
    public AuthorizationRequiredForGetDto savePropertyRequestStepThree(@RequestBody AuthorizationsRequiredForPostDto requestStepThree, BindingResult result) {
        log.debug(" Start adding  property request  step three or modifying it ....");
        return authorizationRequiredService.saveAuthorizationRequired(requestStepThree, result);
    }

    /**
     * GET method to retrieve Step Three data for a property request by its ID.
     *
     * @param id The ID of the property request.
     * @return The Step Three data for the specified property request.
     */
    @GetMapping("/stepThree/{id}")
    public AuthorizationRequiredForGetDto getPropertyRequestStepThree(@PathVariable("id") String id) {
        log.debug(" Start getting  property request  step zero  ....");
        return authorizationRequiredService.getAuthorizationRequired(id);
    }

    /**
     * POST method to save Step Four of a property request.
     *
     * @param requestFour The data for Step Four to be saved.
     * @param result      The binding result for validation.
     * @return The saved Step Four data for the property request.
     */
    @PostMapping("/stepFour")
    public CurrencyFinancingForGetDto savePropertyRequestStepFour(@RequestBody CurrencyFinancingForPostDto requestFour, BindingResult result) {
        log.debug(" Start adding  property request  step four ....");
        return currencyFinancingService.savePropertyRequestCurrencyFinancingInformation(requestFour, result);
    }

    /**
     * GET method to retrieve Step Four data for a property request by its ID.
     *
     * @param id The ID of the property request.
     * @return The Step Four data for the specified property request.
     */
    @GetMapping("/stepFour/{id}")
    public CurrencyFinancingForGetDto getPropertyRequestStepFour(@PathVariable("id") String id) {
        log.debug(" Start getting  property request  step four ....");
        return currencyFinancingService.getPropertyRequestCurrencyFinancingInformation(id);
    }


    /**
     * GET method to retrieve Step Five data for a property request by its ID.
     *
     * @param id The ID of the property request.
     * @return The Step Five data for the specified property request.
     */
    @GetMapping("/stepSix/{id}")
    public PropertyRequestToSummaryDto findStepSixById (
            @PathVariable  String  id ){
        log.info(" Start  find   step   Six  ....");
        return  propertyRequestService.toStepSix(id);
    }

    /**
     * POST method to save Step Six of a property request.
     *
     * @param stepSixForPost The data for Step Six to be saved.
     * @param result         The binding result for validation.
     * @return The saved Step Six data for the property request.
     * @throws EmailSendingException If there is an error sending the email.
     */
    @PostMapping("/stepSeven")
    public RequestStepSixForGet saveStepSeven(@RequestBody RequestStepSixForPost stepSixForPost, BindingResult result) {
        try {
            return propertyRequestService.savePropertyRequestStepSeven(stepSixForPost, result);
        } catch (MessagingException e) {
            throw new EmailSendingException(ApplicationConstants.ERROR_EMAIL_SEND  , "Error sending Email");
        }
    }

    /**
     * GET method to retrieve Step Six data for a property request by its ID.
     *
     * @param id The ID of the property request.
     * @return The Step Six data for the specified property request.
     */
    @GetMapping("/stepSeven/{id}")
    public RequestStepSixForGet findStepSeven(@PathVariable String id) {
        return propertyRequestService.findStepSevenById(id);
    }


}
