package com.wevioo.pi.mapper;


import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.rest.dto.AttachmentRequestDto;
import com.wevioo.pi.rest.dto.AttachmentLightDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * ActionMapper
 */
@Mapper(componentModel = "spring")
public interface AttachmentsMapper {

    /**
     * map entity  Attachment to  AttachmentLightDto
     * @param attachment  attachment entity
     * @return AttachmentLightDto
     */

    @Mapping(target =  "attachmentId" , source = "id")
    AttachmentLightDto   toDto(Attachment attachment);


    /**
     * map entity  Attachment to  AttachmentRequestDto
     * @param attachment  attachment entity
     * @return AttachmentRequestDto
     */

    AttachmentRequestDto toAttachmentRequestDto(Attachment attachment);
}
