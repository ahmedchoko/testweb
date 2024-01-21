package com.wevioo.pi.rest.resources;

import com.wevioo.pi.rest.dto.request.AffectedToBankerDto;
import com.wevioo.pi.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/common")
@Slf4j
public class CommonController {

    @Autowired
    CommonService commonService;

    /**
     * affect fiche invest By Banker
     *
     * @param request
     * @param result
     * @return affectedToBankerDto
     */
    @PostMapping("/banker/affected")
    public AffectedToBankerDto affectedFicheInvestToBanker(@RequestBody AffectedToBankerDto request,
                                                                  BindingResult result) {
        log.info(" affected Direct Invest To Banker....");
        return commonService.affectedFicheInvestToBanker(request, result);
    }
}
