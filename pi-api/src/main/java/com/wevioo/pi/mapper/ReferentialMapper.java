package com.wevioo.pi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.wevioo.pi.domain.entity.referential.Country;
import com.wevioo.pi.domain.entity.referential.Currency;
import com.wevioo.pi.domain.entity.referential.Governorate;
import com.wevioo.pi.rest.dto.CountryDto;
import com.wevioo.pi.rest.dto.CurrencyDto;
import com.wevioo.pi.rest.dto.GovernorateDto;


/**
 * ReferentialMapper
 */
@Mapper(componentModel = "spring")
public interface ReferentialMapper {

    /**
     *
     * @param country
     * @return CountryDto
     */
    CountryDto toCountryDto(Country country);

    /**
     * convert Country to CountryDto.
     *
     * @param countries the Country list.
     * @return list of {@link CountryDto}
     */
    List<CountryDto> toCountryDtoList(List<Country> countries);


    /**
     * Converts a list of Governorate objects to a list of GovernorateDto objects.
     *
     * @param governorateList A list of Governorate objects.
     * @return A list of GovernorateDto objects.
     */
    List<GovernorateDto> toGovernorateDtoList(List<Governorate> governorateList);

    /**
     * Converts a list of Currency objects to a list of CurrencyDto objects.
     *
     * @param currenciesList A list of Currency objects.
     * @return A list of CurrencyDto objects.
     */
    List<CurrencyDto> toCurrencyDtoList(List<Currency> currenciesList);

}
