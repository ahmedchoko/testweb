package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.account.BctAgent;
import com.wevioo.pi.rest.dto.BctAgentDto;
import com.wevioo.pi.rest.dto.BctAgentForGetAllDto;
import com.wevioo.pi.rest.dto.BctAgentForGetDto;
import com.wevioo.pi.rest.dto.BctAgentForPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 *  IBctAgentMapper
 */
@Mapper(componentModel = "spring" , uses = IUserMapper.class)
public interface IBctAgentMapper {

    /**
     * Maps a User entity to a BctAgentDto.
     *
     * @param bctAgent The BctAgent entity to map.
     * @return The BctAgentDto representing the mapped user.
     */
    @Mapping(target= "email", source = "login")
    BctAgentDto toDto( BctAgent bctAgent);

    /**
     * Maps a User entity to a BctAgentForGetDto.
     *
     * @param bctAgent The BctAgent entity to map.
     * @return The BctAgentForGetDto representing the mapped user.
     */
    @Mapping(target= "email", source = "login")
    BctAgentForGetDto toForGetDto(BctAgent bctAgent);

    /**
     * Maps a User entity to a BctAgentForGetAllDto.
     *
     * @param bctAgent The BctAgent entity to map.
     * @return The BctAgentForGetAllDto representing the mapped user.
     */
    BctAgentForGetAllDto toForGetAllDto(BctAgent bctAgent);
    /**
     * Maps a User entity to a list  BctAgentForGetAllDto.
     *
     * @param bctAgent The list BctAgent entity to map.
     * @return The  BctAgentForGetAllDto list representing the mapped list user.
     */
    List<BctAgentForGetAllDto> toForGetAllDto(List<BctAgent> bctAgent);


    /**
     * Maps a BctAgentDto to a BctAgent entity.
     *
     * @return The BctAgent entity representing the mapped BctAgent.
     */
    @Mapping(target= "login", source = "email")
    BctAgent toEntity(  BctAgentForPostDto  bctAgentForPostDto);
}
