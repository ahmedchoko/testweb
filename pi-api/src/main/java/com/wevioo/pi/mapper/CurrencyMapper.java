package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.referential.Currency;
import com.wevioo.pi.rest.dto.CurrencyDto;
import org.mapstruct.Mapper;

/**
 * This interface defines a mapper for converting Currency entities to CurrencyDto objects.
 * The mapper is typically used in Spring components for mapping between different representations of currencies.
 */
@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    /**
     * Converts a Currency entity to a CurrencyDto.
     *
     * @param currency The Currency entity to convert.
     * @return The corresponding CurrencyDto.
     */
    CurrencyDto toCurrencyDto(Currency currency);
}