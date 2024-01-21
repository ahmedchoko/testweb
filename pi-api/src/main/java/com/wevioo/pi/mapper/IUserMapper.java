package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.rest.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * An interface that defines the mapping between User and UserDto objects using MapStruct.
 *
 * The mapping is based on the specified component model, which in this case is "spring."
 *
 * @see org.mapstruct.Mapper
 * @see UserDto
 * @see User
 * @author  knh
 */
@Mapper(componentModel = "spring")
public interface IUserMapper {
    /**
     * Maps a User entity to a UserDto.
     *
     * @param user The User entity to map.
     * @return The UserDto representing the mapped user.
     */
    @Mapping(target= "email", source = "login")
    UserDto toDto(User user);
    /**
     * Maps a UserDto to a User entity.
     *
     * @param userDto The UserDto to map.
     * @return The User entity representing the mapped user.
     */
    @Mapping(target= "login", source = "email")
     User toEntity(UserDto  userDto);
    /**
     * Maps a list of User entities to a list of UserDto objects.
     *
     * @param users The list of User entities to map.
     * @return A list of UserDto objects representing the mapped users.
     */
    List<UserDto> toDto( List<User> users);
    /**
     * Maps a list of UserDto objects to a list of User entities.
     *
     * @param userDtos The list of UserDto objects to map.
     * @return A list of User entities representing the mapped users.
     */
    List<User> toEntity(List<UserDto>  userDtos);


}
