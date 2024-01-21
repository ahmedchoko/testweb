package com.wevioo.pi.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * AttachmentForPostDto
 *
 * @author shl
 *
 */
@Getter
@Setter
public class AttachmentForPostDto {

    /**
     * fileName
     */
    private String fileName;

    /**
     * creationDate
     */
    private Date creationDate;

    /**
     * createdBy
     */
    private String createdBy;

    /**
     * updateDate
     */
    private Date updateDate;

    private String fileUrl;
}
