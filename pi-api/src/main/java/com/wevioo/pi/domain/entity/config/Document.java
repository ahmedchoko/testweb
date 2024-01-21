package com.wevioo.pi.domain.entity.config;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
/**
 * Document Model DataTable
 */
@Entity
@Table(name = "PI017T_DOCUMENT")
@Getter
@Setter
public class Document implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -7241352147252100811L;

    /**
     * Document's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_generator")
    @SequenceGenerator(name = "document_generator", sequenceName = "DOCUMENT_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "PI017_ID", updatable = false, nullable = false)
    private Long id;

    /**
     *  Document's label
     */
    @Column(name = "PI017_LABEL", nullable = false)
    private String label;
    /**
     *  Document's labelAN
     */
    @Column(name = "PI017_LABEL_AN", nullable = false)
    private String labelAN;

    /**
     *  Document's description
     */
    @Column(name = "PI017_DESCRIPTION", nullable = false)
    private String description;

    /**
     *  Document's descriptionAN
     */
    @Column(name = "PI017_DESCRIPTION_AN", nullable = false)
    private String descriptionAN;  
 
    
    /**
	 *  Document's sectionDocument
	 */
	@OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
	private List<SectionDocument> sectionDocument;
}
