package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.referential.Agency;
import com.wevioo.pi.rest.dto.AgencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * AgencyMapper
 */
@Mapper(componentModel = "spring")
public interface AgencyMapper {
    /**
     * Map Agency entity to AgencyDto
     * @param agency Agency entity
     * @return AgencyDto return  AgencyDto
     */
	@Mapping(target ="id", source ="id.agencyId"  )
    AgencyDto toDto(Agency agency);
    /**
     * Map Agency List entity to  List AgencyDto
     * @param agencies  list of  entity Agency
     * @return  AgencyDto return  List of AgencyDto
     */
    List<AgencyDto> toDto(List<Agency> agencies);

}
