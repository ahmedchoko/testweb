package com.wevioo.pi.mapper;


import com.wevioo.pi.domain.entity.referential.Codification;
import com.wevioo.pi.rest.dto.request.CodificationForPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {})
public interface MoralPersonMapper {


    /**
     * @param dto
     * @return
     */
    @Mapping(source="uniqueIdentifier" , target="id.uniqueIdentifier")
    @Mapping(target="creationDate" , ignore = true)
    Codification toEntity (CodificationForPostDto dto);


    /**
     * @param codification
     * @return
     */
    CodificationForPostDto toDto (Codification codification);
}
