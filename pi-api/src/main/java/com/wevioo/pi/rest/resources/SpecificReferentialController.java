package com.wevioo.pi.rest.resources;


import com.wevioo.pi.rest.dto.CategorySpecificReferentialDto;
import com.wevioo.pi.rest.dto.SpecificReferentialDto;
import com.wevioo.pi.service.SpecificReferentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest Controller  SpecificReferentialController
 */
@RestController
@RequestMapping("/specificReferentials")
public class SpecificReferentialController {

    @Autowired
    private SpecificReferentialService specificReferentialService;


    @GetMapping("/{id}")
    public SpecificReferentialDto  findById(@PathVariable Long id){
        return specificReferentialService.findById(id);
    }

    @GetMapping("/parent/{id}")
    public List<SpecificReferentialDto> findAllByParentId(@PathVariable Long id){
        return specificReferentialService.findAllByParentId(id);
    }
    @GetMapping("/category/{id}")
    public List<SpecificReferentialDto> findAllByCategoryId(@PathVariable Long id){
        return specificReferentialService. findAllByCategorySpecificReferentialId(id);
    }

    @GetMapping("/categoryIn")
    public List<CategorySpecificReferentialDto> findAllByCategoryIdIn(@RequestParam(required = true)  List<Long> ids){
        return specificReferentialService.  findAllByCategorySpecificReferentialIdIn(ids);
    }

    @GetMapping("/category/depends")
    public CategorySpecificReferentialDto findAllByCategoryDepends(@RequestParam(name = "id", required = true) Long id,
                                                                @RequestParam(name = "categoryDepends", required = false) String categoryDepends,
                                                                @RequestParam(name = "investorId", required = false) String investorId){
        return specificReferentialService.findAllByCategorySpecificReferentialDepends(id, categoryDepends, investorId);
    }
}
