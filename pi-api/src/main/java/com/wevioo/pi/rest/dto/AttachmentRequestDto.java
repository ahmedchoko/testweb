package com.wevioo.pi.rest.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) representing an attachment request.
 * This class is serializable to support data transmission.
 */
@Getter
@Setter
public class AttachmentRequestDto implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = -3488411799290744113L;

    /**
     * Unique identifier for the attachment.
     */
    private String id;

    /**
     * Name of the file attached.
     */
    private String fileName;

    /**
     * Size of the attached file in bytes.
     */
    private Integer fileSize;

    // Getters and setters methods are typically included here but are omitted for brevity.
}

