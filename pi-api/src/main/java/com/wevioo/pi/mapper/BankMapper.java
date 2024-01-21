package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.rest.dto.BankDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * BankMapper
 */
@Mapper(componentModel = "spring")
public interface BankMapper {
    /**
     * Converts a Bank object to its corresponding BankDto object.
     *
     * @param bank The Bank object to be converted to BankDto
     * @return BankDto representing the converted Bank object
     */
     BankDto toDto(Bank  bank);
    /**
     * Converts a list of Bank objects to a list of corresponding BankDto objects.
     *
     * @param banks The list of Bank objects to be converted to BankDto
     * @return List of BankDto representing the converted list of Bank objects
     */
    List<BankDto> toDto(List<Bank>  banks);
}
