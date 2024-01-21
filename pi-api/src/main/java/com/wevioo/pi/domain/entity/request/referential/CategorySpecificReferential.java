package com.wevioo.pi.domain.entity.request.referential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Entity Category Generic Referential
 */
@Entity
@Table(name = "PI027_CATEGORY_SPECIFIC_REFERENTIAL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySpecificReferential implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 12479749814L;

    /**
     * ID of the activity class.
     */
    @Id
    @Column(name = "PI027_ID", nullable = false, updatable = false)
    private  Long id;

    /**
     * Code of the Activity class.
     */
    @Column(name = "PI027_CODE")
    private String code;

    /**
     * label of the Activity class.
     */
    @Column(name = "PI027_LABEL")
    private String label;

    @OneToMany(mappedBy = "categorySpecificReferential")
    private List<SpecificReferential> specificReferentialList;
}
