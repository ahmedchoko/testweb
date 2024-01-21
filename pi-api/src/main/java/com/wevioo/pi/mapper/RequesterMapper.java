package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.Requester;
import com.wevioo.pi.rest.dto.request.RequesterForPostDto;
import com.wevioo.pi.rest.dto.response.DirectInvestStepOneForGet;
import com.wevioo.pi.rest.dto.response.RequesterForGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Requester Mapper
 */
@Mapper(componentModel = "spring")
public interface RequesterMapper {

    Requester toEntity (RequesterForPostDto dto);
    @Mapping(target ="ficheInvestId" , source = "id")
    @Mapping(target = "hasRequester" , source = "." , qualifiedByName = "isHasRequester")
    @Mapping(target = "investorId" , source = "invistor.id")
    DirectInvestStepOneForGet toDto(DirectInvestRequest   directInvestRequest);



    RequesterForGetDto toDto(  Requester   requester);

    @Named("isHasRequester")
    default  Boolean isHasRequester(DirectInvestRequest  directInvestRequest) {
        if(directInvestRequest != null  && directInvestRequest.getRequester() != null){
            return  Boolean.TRUE;
        }
     return  Boolean.FALSE;
    }
}
