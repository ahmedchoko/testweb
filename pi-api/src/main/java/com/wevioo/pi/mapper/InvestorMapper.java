package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.rest.dto.InvestorForGetDto;
import com.wevioo.pi.rest.dto.InvestorForPostDto;
import com.wevioo.pi.rest.dto.InvestorForSelfPutDto;
import com.wevioo.pi.rest.dto.InvestorGetForPutDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * InvestorMapper
 */
@Mapper(componentModel = "spring")
public interface InvestorMapper {

    /**
     * Maps an InvestorForPostDto to an Investor entity.
     *
     * @param investorForPostDto The InvestorDto to map.
     * @return The Investor entity representing the mapped Investor.
     */
    @Mapping(target = "nationality", ignore = true)
    @Mapping(target = "countryOfResidency", ignore = true)
    @Mapping(target = "representative.nationality", ignore = true)
    @Mapping(target = "representative.countryOfResidency", ignore = true)
    @Mapping(target= "login", source = "email")
    Investor postInvestorToInvestor(InvestorForPostDto investorForPostDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nationality", ignore = true)
    @Mapping(target = "countryOfResidency", ignore = true)
    Investor putInvestorToInvestor(InvestorForSelfPutDto investorForSelfPutDto);

    @Mapping(target = "nationality", source="nationality.nationality")
    @Mapping(target = "countryOfResidency", source="countryOfResidency.label")
    @Mapping(target = "representative.nationality", source="representative.nationality.nationality")
    @Mapping(target = "representative.countryOfResidency", source="representative.countryOfResidency.label")
    @Mapping(target= "email", source = "login")
    @Mapping(target= "active", source = "isActive")
    @Mapping(target= "phoneNumber", source = "cellPhone")
    InvestorForGetDto investorToInvestorForGet(Investor investor);

    @Mapping(target= "email", source = "login")
    @Mapping(target= "active", source = "isActive")
    @Mapping(target = "nationality", source="nationality.id")
    @Mapping(target = "countryOfResidency", source="nationality.id")
    @Mapping(target= "phoneNumber", source = "cellPhone")
    InvestorGetForPutDto investorToInvestorForPut(Investor investor);

}
