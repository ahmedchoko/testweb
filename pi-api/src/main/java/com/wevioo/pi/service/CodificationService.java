package com.wevioo.pi.service;

import com.wevioo.pi.rest.dto.request.CodificationForPostDto;
import org.springframework.validation.BindingResult;

public interface CodificationService {

    /**
     * @param codificationForPostDto
     * @param result
     * @return society saved in codification table
     */
CodificationForPostDto saveCodification ( CodificationForPostDto codificationForPostDto, BindingResult result);

}
