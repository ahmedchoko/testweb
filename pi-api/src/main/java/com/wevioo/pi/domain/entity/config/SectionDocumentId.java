package com.wevioo.pi.domain.entity.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


/**
 * Embeddable id
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionDocumentId implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -7241352147252100811L;
    /***
	 * SectionDocumentId's sectionId
	 */
    @Column(name = "SECTION_ID")
    private Long sectionId;
    /***
	 * SectionDocumentId's documentId
	 */
    @Column(name = "DOCUMENT_ID")
    private Long documentId;

}
