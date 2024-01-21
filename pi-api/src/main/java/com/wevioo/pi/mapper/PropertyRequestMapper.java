package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.PropertyRequest;
import com.wevioo.pi.rest.dto.request.PropertyRequestStepZeroForGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PropertyRequestMapper {

    @Mapping(target ="ficheInvestId" , source = "id")
    @Mapping(target = "hasRequester" , source = "." , qualifiedByName = "isHasRequester")
    @Mapping(target = "investorId" , source = "investor.id")
    PropertyRequestStepZeroForGetDto toDto( PropertyRequest  propertyRequest);

    @Named("isHasRequester")
    default  Boolean isHasRequester(PropertyRequest   propertyRequest) {
        if(propertyRequest != null  && propertyRequest.getRequester() != null){
            return  Boolean.TRUE;
        }
        return  Boolean.FALSE;
    }
}
