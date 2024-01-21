package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.domain.entity.request.referential.CategorySpecificReferential;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.CategorySpecificReferentialMapper;
import com.wevioo.pi.mapper.SpecificReferentialMapper;
import com.wevioo.pi.repository.CategorySpecificReferentialRepository;
import com.wevioo.pi.repository.InvestorRepository;
import com.wevioo.pi.repository.SpecificReferentialRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.CategorySpecificReferentialDto;
import com.wevioo.pi.rest.dto.SpecificReferentialDto;
import com.wevioo.pi.service.ParameterService;
import com.wevioo.pi.service.SpecificReferentialService;
import com.wevioo.pi.utility.CommonUtilities;
import com.wevioo.pi.utility.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SpecificReferentialService
 */
@Service
public class SpecificReferentialServiceImp implements SpecificReferentialService {


     /**
     * Injected bean {@link SpecificReferentialRepository}
     */
      @Autowired
      private SpecificReferentialRepository specificReferentialRepository;

    /**
     * Injected bean {@link CategorySpecificReferentialRepository}
     */
    @Autowired
    private CategorySpecificReferentialRepository categorySpecificReferentialRepository;

     /**
     * Injected bean {@link SpecificReferentialMapper}
     */
      @Autowired
      private SpecificReferentialMapper specificReferentialMapper;

     /**
     * Injected bean {@link CategorySpecificReferentialMapper}
     */
      @Autowired
      private CategorySpecificReferentialMapper categorySpecificReferentialMapper;


    /**
     * Inject {@link ParameterService} bean
     */
    @Autowired
    private ParameterService parameterService;

    /**
     * Injected bean {@link SecurityUtils}
     */
    @Autowired
    private SecurityUtils securityUtils;

    /**
     * Injected bean {@link UserRepository}
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Injected bean {@link InvestorRepository}
     */
    @Autowired
    private InvestorRepository investorRepository;


     /**
     * findAll By  id Category
     *
     * @param id category
     * @return list  SpecificReferential dto
     */
    @Override
    public List<SpecificReferentialDto> findAllByCategorySpecificReferentialId(Long id) {
        return  specificReferentialMapper.toDto(
                specificReferentialRepository.findAllByCategorySpecificReferentialId(id)
        );
    }

    @Override
    public CategorySpecificReferentialDto findAllByCategorySpecificReferentialDepends(Long categoryId, String categoryDepends, String investorId) {
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        boolean isBanker = user.getUserType() == UserTypeEnum.BANKER;
        CategorySpecificReferential categorySpecificReferential = categorySpecificReferentialRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.CATEGORY_SPECIFIC_NOT_FOUND,
                        "No specific category found with id: " + categoryId));
        List<SpecificReferential> specificReferentials = categorySpecificReferential.getSpecificReferentialList();

        Parameter parameterVocation = parameterService.getByKey(ApplicationConstants.REFERENTIAL_VOCATION);
        String[] values = new String[0];
        if (parameterVocation != null && categoryId.equals(Long.valueOf(parameterVocation.getParameterValue()))) {
            if (!isBanker) {
                investorId = userId;
            }
            Investor investor = investorRepository.findById(investorId)
                    .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND,
                            ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID + userId));
            PersonTypeEnum investorType = investor.getInvestorType();
            if (CommonUtilities.isInvestorForeign(investorType)) {
                values = parameterVocation.getParameterPattern().split(",");
            }
        } if (categoryDepends != null) {
                Parameter parameter = parameterService.getByKeyAndPrefix(categorySpecificReferential.getLabel(), categoryDepends);
                if (parameter != null) {
                    values = parameter.getParameterValue().split(",");
                }
        }
        String[] finalValues = values;
        specificReferentials = specificReferentials.stream()
                .filter(specificReferential -> Arrays.stream(finalValues)
                        .noneMatch(value -> specificReferential.getId().equals(Long.valueOf(value))))
                .collect(Collectors.toList());
        categorySpecificReferential.setSpecificReferentialList(specificReferentials);
        return categorySpecificReferentialMapper.toDto(categorySpecificReferential);
    }

    /**
     * find AllBy  Category Specific Referential Id In
     *
     * @param ids
     * @return list  SpecificReferential Dto
     */
    @Override
    public List<CategorySpecificReferentialDto> findAllByCategorySpecificReferentialIdIn(List<Long> ids) {
        return  categorySpecificReferentialMapper.toDto(
                specificReferentialRepository.findAllByCategorySpecificReferentialIdIn(ids)
        );
    }

    /**
     * findAllByParentId
     *
     * @param parentId id parent
     * @return list  of  SpecificReferential
     */
    @Override
    public List<SpecificReferentialDto> findAllByParentId(Long parentId) {
        return  specificReferentialMapper.toDto(
                specificReferentialRepository.findAllByParentId(parentId)
        );
    }

    /**
     * findById
     *
     * @param id
     * @return SpecificReferentialDto
     */
    @Override
    public SpecificReferentialDto findById(Long id) {
        SpecificReferential  specificReferential = specificReferentialRepository
                .findById(id).orElseThrow(
                        ()-> new DataNotFoundException(ApplicationConstants.SPECIFIC_REFERENTIAL_NOT_FOUND, "SPECIFIC REFERENTIAL NOT FOUND WITH ID : "+id)
                );
        return  specificReferentialMapper.toDto(specificReferential);
    }
}
