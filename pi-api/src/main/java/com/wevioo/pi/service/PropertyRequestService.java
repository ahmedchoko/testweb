package com.wevioo.pi.service;

import java.io.IOException;

import com.wevioo.pi.rest.dto.request.PropertyDescriptionForGetDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestToSummaryDto;
import com.wevioo.pi.rest.dto.response.RequestStepSixForGet;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.wevioo.pi.rest.dto.request.PropertyRequestStepOneForGetDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepOneForPostDto;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepZeroForGetDto;
import com.wevioo.pi.rest.dto.request.RequestStepSixForPost;

import javax.mail.MessagingException;


public interface PropertyRequestService {
    /**
     * Saves step one of property request.
     *
     * @param propertyRequestStepOneForPostDto The data for step one of property request.
     * @param result                           The binding result for validation.
     * @return The PropertyRequestStepOneForGetDto after saving step one.
     */
    PropertyDescriptionForGetDto savePropertyRequestStepOne(PropertyRequestStepOneForPostDto propertyRequestStepOneForPostDto,
                                                            BindingResult result);
    /**
     * Saves step zero of property request.
     *
     * @param propertyRequestForPostDto The data for step zero of property request.
     * @param file                      The multipart file associated with the property request.
     * @return The PropertyRequestStepZeroForGetDto after saving step zero.
     * @throws IOException If an I/O exception occurs during file handling.
     */
    PropertyRequestStepZeroForGetDto savePropertyRequestStepZero( String propertyRequestForPostDto,
                                                                  MultipartFile file) throws IOException;
    /**
     * Retrieves PropertyRequestStepOneForGetDto based on the provided ID.
     *
     * @param id The unique identifier used to retrieve PropertyRequestStepOneForGetDto.
     * @return The PropertyRequestStepOneForGetDto corresponding to the provided ID.
     */
    PropertyRequestStepOneForGetDto getPropertyRequestStepOneInformation(String id);
    /**
     * Retrieves PropertyRequestStepZeroForGetDto based on the provided ID.
     *
     * @param id The unique identifier used to retrieve PropertyRequestStepZeroForGetDto.
     * @return The PropertyRequestStepZeroForGetDto corresponding to the provided ID.
     */
    PropertyRequestStepZeroForGetDto getPropertyRequestStepZeroInformation(String id);

    /**
     * Converts a PropertyRequest entity to a summary representation in PropertyRequestToStepFiveDto for the fifth step of the property request process.
     *
     * @param id The identifier of the property request to retrieve and convert.
     * @return An instance of PropertyRequestToStepFiveDto representing the summary data for the fifth step.
     */
    PropertyRequestToSummaryDto toStepSix(String id);

    /**
     * Saves Step Six of a property request.
     *
     * @param stepSixForPost The data for Step Six to be saved.
     * @param result         The binding result for validation.
     * @return The saved Step Six data for the property request.
     * @throws MessagingException If there is an error in sending email notifications.
     */
    RequestStepSixForGet savePropertyRequestStepSeven(RequestStepSixForPost stepSixForPost, BindingResult result) throws MessagingException;

    /**
     * Retrieves Step Six data for a property request by its ID.
     *
     * @param id The ID of the property request.
     * @return The Step Six data for the specified property request.
     */
    RequestStepSixForGet findStepSevenById(String id);
}
