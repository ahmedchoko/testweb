package com.wevioo.pi.domain.entity.request.referential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity Specific Referential
 */
@Entity
@Table(name = "PI026_SPECIFIC_REFERENTIAL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecificReferential implements Serializable {
    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 12479749814L;

    /**
     * ID of the activity class.
     */
    @Id
    @Column(name = "PI026_ID", nullable = false, updatable = false)
    private Long id;

    /**
     * Code of the Activity class.
     */
    @Column(name = "PI026_CODE")
    private String code;

    /**
     * label of the Activity class.
     */
    @Column(name = "PI026_LABEL")
    private String label;
    /**
     *  description
     */
    @Column(name = "PI026_DESCRIPTION")
    private String description;
    /**
     *  parent
     */
    @ManyToOne
    @JoinColumn(name = "PI026_PARENT_ID")
    private SpecificReferential parent;
    
    /**
     * Category Generic Referential
     */
    @ManyToOne
    @JoinColumn(name = "PI026_CATEGORY_SPECIFIC_REFERENTIAL_ID")
    private CategorySpecificReferential  categorySpecificReferential;
}
