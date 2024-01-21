package com.wevioo.pi.domain.entity.config;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * SectionDocument model dataTable
 */
@Entity
@Table(name = "PI018_SECTION_DOCUMENT")
@Getter
@Setter
public class SectionDocument implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -7256152147252100442L;

	/***
	 * SectionDocument's id
	 */
	@EmbeddedId
	private SectionDocumentId id;
	/***
	 * SectionDocument's section
	 */
	@ManyToOne
	@MapsId("sectionId")
	@JoinColumn(name = "PI018_SECTION_IDFK")
	private Section section;
	/***
	 * SectionDocument's document
	 */
	@ManyToOne
	@MapsId("documentId")
	@JoinColumn(name = "PI018_DOCUMENT_IDFK")
	private Document document;

	/**
	 * Document in this section is Mandatory? YES: NO
	 */
	@Column(name = "PI018_IS_MANDATORY")
	private Boolean isMandatory;

	/**
	 * Document in this section request number
	 */
	@Column(name = "PI018_REQUESTED_NUMBER")
	private Integer requestedNumber;

}
