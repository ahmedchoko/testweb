package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.CurrencyFinancing;
import com.wevioo.pi.domain.entity.request.CurrencyFinancingData;
import com.wevioo.pi.rest.dto.request.CurrencyInvestmentFinancingDto;
import com.wevioo.pi.rest.dto.response.CurrencyFinancingDataForGet;
import com.wevioo.pi.rest.dto.response.CurrencyFinancingDataForStepFive;
import com.wevioo.pi.rest.dto.response.CurrencyFinancingForGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyInvestmentFinancingMapper {

    /**
     * Converts a CurrencyInvestmentFinancingDto object to a corresponding CurrencyFinancingData entity.
     *
     * @param currencyInvestmentFinancingDto The DTO containing information for creating a CurrencyInvestmentFinancingDto entity.
     * @return CurrencyFinancingData representing the converted entity.
     */
    @Mapping(target = "financialMode", ignore = true)
    @Mapping(target = "importationPiece", ignore = true)
    @Mapping(target = "financialCurrency", ignore = true)
    @Mapping(target = "bank", ignore = true)
    CurrencyFinancingData toEntity(CurrencyInvestmentFinancingDto currencyInvestmentFinancingDto);
    /**
     * Converts a CurrencyFinancingData object to its corresponding CurrencyFinancingDataForGet DTO.
     *
     * @param currencyFinancingData The CurrencyFinancingData object to convert.
     * @return Corresponding CurrencyFinancingDataForGet DTO.
     */
    CurrencyFinancingDataForGet toDto(CurrencyFinancingData currencyFinancingData);
    /**
     * Converts a list of CurrencyFinancingData objects to a list of corresponding CurrencyFinancingDataForGet DTOs.
     *
     * @param currencyFinancingData List of CurrencyFinancingData objects to convert.
     * @return List of corresponding CurrencyFinancingDataForGet DTOs.
     */
    List<CurrencyFinancingDataForGet> toDto(List<CurrencyFinancingData> currencyFinancingData);


    /**
     * Converts a CurrencyFinancing object to its corresponding CurrencyFinancingForGetDto.
     *
     * @param currencyFinancing The CurrencyFinancing object to convert.
     * @return Corresponding CurrencyFinancingForGetDto.
     */
    CurrencyFinancingForGetDto  toDto(CurrencyFinancing currencyFinancing);

    /**
     * Converts a CurrencyFinancingData object to its corresponding CurrencyFinancingDataForStepFive DTO
     * specifically for Step Five in DirectInvest.
     *
     * @param currencyFinancingData The CurrencyFinancingData object to convert.
     * @return Corresponding CurrencyFinancingDataForStepFive DTO for Step Five in DirectInvest.
     */
    CurrencyFinancingDataForStepFive toDtoForStepFiveDirectInvest(CurrencyFinancingData  currencyFinancingData);

    /**
     * Converts a list of CurrencyFinancingData objects to a list of corresponding CurrencyFinancingDataForStepFive DTOs
     * specifically for Step Five in DirectInvest.
     *
     * @param currencyFinancingData List of CurrencyFinancingData objects to convert.
     * @return List of corresponding CurrencyFinancingDataForStepFive DTOs for Step Five in DirectInvest.
     */
    List<CurrencyFinancingDataForStepFive> toDtoForStepFiveDirectInvest(List<CurrencyFinancingData>  currencyFinancingData);

}
