package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.Action;
import com.wevioo.pi.rest.dto.ActionDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * ActionMapper
 */
@Mapper(componentModel = "spring")
public interface ActionMapper {

    /**
     * Map Action entity to AgencyDto
     * @param action Action entity
     * @return ActionDto return  ActionDto
     */
    ActionDto toDto( Action  action);
    /**
     * Map Action List entity to  List ActionDto
     * @param actions  list of  entity Action
     * @return  ActionDto return  List of ActionDto
     */
    List<ActionDto> toDto(List<Action>  actions);
}
