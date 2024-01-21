package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.referential.NatSubSection;
import com.wevioo.pi.rest.dto.NatSubSectionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NatSubSectionMapper   extends  EntityMapper<NatSubSectionDto, NatSubSection> {
}
