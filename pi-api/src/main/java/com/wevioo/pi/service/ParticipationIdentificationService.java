package com.wevioo.pi.service;

import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.rest.dto.response.ParticipationIdentificationStepTwoForGetDto;
import org.springframework.validation.BindingResult;


public interface ParticipationIdentificationService {
  /**
   * Saves participation identification for step two.
   *
   * @param requestStepTwo The data for Step Two of participation identification.
   * @param result         The binding result for validation.
   * @return The ParticipationIdentificationStepTwoForGetDto after saving the participation identification for step two.
   */
  ParticipationIdentificationStepTwoForGetDto saveParticipationIdentification(
          ParticipationIdentificationStepTwoForPostDto requestStepTwo,
          BindingResult result
  );
  /**
   * Retrieves ParticipationIdentificationStepTwoForGetDto based on the provided ID for step two.
   *
   * @param id The unique identifier used to find the ParticipationIdentificationStepTwoForGetDto for step two.
   * @return The ParticipationIdentificationStepTwoForGetDto corresponding to the provided ID for step two.
   */
  ParticipationIdentificationStepTwoForGetDto findStepTwoById(String id);

}
