package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.referential.NatSection;
import com.wevioo.pi.rest.dto.NatSectionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NatSectionMapper extends  EntityMapper<NatSectionDto, NatSection> {
}
