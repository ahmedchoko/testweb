package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.rest.dto.BankerDto;
import com.wevioo.pi.rest.dto.BankerForGetAllDto;
import com.wevioo.pi.rest.dto.BankerForGetDto;
import com.wevioo.pi.rest.dto.BankerForPostDto;
import com.wevioo.pi.rest.dto.response.BankerLightForGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 *
 */
@Mapper(componentModel = "spring" , uses = IUserMapper.class)
public interface IBankerMapper {

    /**
     * Maps a User entity to a BankerDto.
     *
     * @param banker The Banker entity to map.
     * @return The BankerDto representing the mapped user.
     */
    @Mapping(target= "email", source = "login")
     BankerDto toDto(Banker  banker);

    /**
     * Maps a User entity to a BankerDto.
     *
     * @param banker The Banker entity to map.
     * @return The BankerDto representing the mapped user.
     */
    @Mapping(target= "email", source = "login")
    @Mapping(target = "bank" ,  source = "approvedIntermediary")
    BankerForGetDto toBankerForGetDto(Banker banker);
    /**
     * Maps a BankerDto to a Banker entity.
     *
     * @param bankerDto The BankerDto to map.
     * @return The Banker entity representing the mapped Banker.
     */
    @Mapping(target= "login", source = "email")
    Banker toEntity(BankerDto   bankerDto);
    /**
     * Converts a BankerForPostDto object to its corresponding Banker entity object.
     *
     * @param bankerDto The BankerForPostDto object to be converted to a Banker entity
     * @return Banker entity representing the converted BankerForPostDto object
     */
    @Mapping(target= "login", source = "email")
    Banker toEntity(BankerForPostDto   bankerDto);
    /**
     * Maps a list of Banker entities to a list of BankerDto objects.
     *
     * @param bankers The list of Banker entities to map.
     * @return A list of BankerDto objects representing the mapped users.
     */
    List<BankerDto> toDto(List<Banker>  bankers);

    /**
     * Converts a Banker object to its corresponding BankerForGetAllDto object.
     *
     * @param banker The Banker object to be converted to BankerForGetAllDto
     * @return BankerForGetAllDto representing the converted Banker object
     */
    @Mapping(target = "bankName" ,  source = "approvedIntermediary.label")
    BankerForGetAllDto   toBankerForGetAllDto  (Banker banker);

    /**
     * Converts a list of Banker objects to a list of corresponding BankerForGetAllDto objects.
     *
     * @param bankers The list of Banker objects to be converted to BankerForGetAllDto
     * @return List of BankerForGetAllDto representing the converted list of Banker objects
     */
    List<BankerForGetAllDto> toBankerForGetAllDto(List<Banker> bankers);


    /**
     * Converts a list of Banker objects to a list of corresponding BankerLightForGetDto objects.
     * @param bankers
     * @return List of BankerLightForGetDto representing the converted list of Banker objects
     */
    List<BankerLightForGetDto> toDtoBankerLight (List<Banker>  bankers);

}
