package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.referential.NatClass;
import com.wevioo.pi.rest.dto.NatClassDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NatClassMapper  extends  EntityMapper<NatClassDto, NatClass> {
}
