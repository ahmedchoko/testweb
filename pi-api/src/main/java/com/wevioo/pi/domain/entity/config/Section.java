package com.wevioo.pi.domain.entity.config;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.OperationType;

import lombok.Getter;
import lombok.Setter;
/**
 * Section model dataTable
 */
@Entity
@Table(name = "PI016T_SECTION")
@Getter
@Setter
public class Section implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -7256152147252100822L;

	/**
	 * Section's id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "section_generator")
	@SequenceGenerator(name = "section_generator", sequenceName = "SECTION_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "PI016_ID", updatable = false, nullable = false)
	private Long id;

	/**
	 * Section's label
	 */
	@Column(name = "PI016_LABEL", nullable = false)
	private String label;
	
	/**
	 * Section's labelAN
	 */
	@Column(name = "PI016_LABEL_AN", nullable = false)
	private String labelAN;


	
    /**
     * Section's declarationNature
     */
    @Column(name = "PI016_DECLARATION_NATURE", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
	private DeclarationNature declarationNature;
    
	
    /**
     * Section's operationType
     */
    @Column(name = "PI016_OPERATION_TYPE", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
	private OperationType operationType;
    
	
    /**
     * Section's investmentType
     */
    @Column(name = "PI016_INVESTMENT_TYPE", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
	private InvestmentType investmentType;
    
	/**
	 *Section's sectionDocument
	 */
	@OneToMany(mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SectionDocument> sectionDocument;
}
