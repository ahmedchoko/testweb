package com.wevioo.pi.utility;


import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wevioo.pi.domain.entity.request.MainContractualTerms;
import com.wevioo.pi.mapper.MainContractualTermsMapper;
import com.wevioo.pi.rest.dto.response.MainContractualTermsStepTwoForGetDto;

@Service
@Mapper(componentModel = "spring")
public class SpecificMapper {


    @Autowired
    MainContractualTermsMapper mainContractualTermsMapper;
    /**
     * @param mainContractualTerms
     * @param propertyId
     * @return mainContractualTermsMapped with idPropertyRequest ( unidirectional relation between PropertyRequest and mainContractualTerms )
     */
    public MainContractualTermsStepTwoForGetDto toMainContractualTermsStepTwoForGetDto(MainContractualTerms mainContractualTerms , String propertyId) {
        if (mainContractualTerms == null) {
            return null;
        }
        MainContractualTermsStepTwoForGetDto mainContractualTermsMapped = mainContractualTermsMapper.toDto(mainContractualTerms);
        mainContractualTermsMapped.setIdPropertyRequest(propertyId);
        return mainContractualTermsMapped;
    }
}
