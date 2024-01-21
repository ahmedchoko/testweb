package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.InvestIdentification;
import com.wevioo.pi.rest.dto.request.InvestIdentificationForPostDto;
import com.wevioo.pi.rest.dto.response.InvestIdentificationDtoToStepFive;
import com.wevioo.pi.rest.dto.response.InvestIdentificationForGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Invest Identification Mapper
 */
@Mapper(componentModel = "spring" , uses =  {ActivityDeclarationMapper.class , ActivitySupportMapper.class})
public interface InvestIdentificationMapper {

    /**
     * Maps an investIdentificationForPostDto to an InvestIdentification entity.
     *
     * @param investIdentificationForPostDto The investIdentificationDto to map.
     * @return The Invest identification entity representing the mapped InvestIdentification.
     */
    @Mapping(target = "legalForm", ignore = true)
    InvestIdentification  toEntity(InvestIdentificationForPostDto investIdentificationForPostDto);

    @Mapping(target = "ficheInvestId" , source = "directInvestRequest.id")
    InvestIdentificationForGetDto toDto(InvestIdentification investIdentification);
    @Mapping(target = "ficheInvestId" , source = "." , qualifiedByName = "findFicheInvestId")
    @Mapping(target = "activityDeclarations", ignore = true)
    @Mapping(target = "activitySupports", ignore = true)
    InvestIdentificationForGetDto toInvestIdentificationDto(InvestIdentification investIdentification);


    InvestIdentificationDtoToStepFive toStepFive(InvestIdentification identification);
    @Named("findFicheInvestId")
    default  String findFicheInvestId(InvestIdentification investIdentification){
        if(investIdentification != null && investIdentification.getDirectInvestRequest() != null){
            return investIdentification.getDirectInvestRequest().getId();
        }
        return  null;
    }


}
