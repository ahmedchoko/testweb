package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.referential.NatGroup;
import com.wevioo.pi.rest.dto.NatGroupDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NatGroupMapper extends  EntityMapper<NatGroupDto, NatGroup> {
}
