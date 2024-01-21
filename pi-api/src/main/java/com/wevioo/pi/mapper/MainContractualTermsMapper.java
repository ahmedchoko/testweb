package com.wevioo.pi.mapper;


import com.wevioo.pi.domain.entity.request.MainContractualTerms;
import com.wevioo.pi.rest.dto.request.MainContractualTermsForStepFiveDto;
import com.wevioo.pi.rest.dto.request.MainContractualTermsStepTwoForPutDto;
import com.wevioo.pi.rest.dto.response.MainContractualTermsStepTwoForGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {})
public interface MainContractualTermsMapper {


    /**
     * @param dto
     * @return
     */
    MainContractualTerms toEntity (MainContractualTermsStepTwoForPutDto dto);


    /**
     * @param mainContractualTerms
     * @return
     */
    @Mapping (target = "modificationDate", ignore = true)
    @Mapping (target = "createdBy", ignore = true)
    @Mapping (target = "creationDate", ignore = true)
    @Mapping (target = "modifiedBy", ignore = true)
    MainContractualTermsStepTwoForGetDto toDto (MainContractualTerms mainContractualTerms);

    /**
     * Converts an instance of {@link MainContractualTerms} to a summary representation in {@link MainContractualTermsForStepFiveDto}.
     *
     * @param mainContractualTerms The source object to be converted.
     * @return An instance of {@link MainContractualTermsForStepFiveDto} representing the summary data.
     */
    @Mapping(target = "identificationValue", source = "uniqueIdentifier")
    MainContractualTermsForStepFiveDto toDtoSummary(MainContractualTerms mainContractualTerms);

}
