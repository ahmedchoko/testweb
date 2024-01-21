package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "GR005T_CPOSTAUX")
@Getter
@Setter
public class ZipCode implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 8865630621020254259L;

    /**
     * ZipCode's id.
     */

    @EmbeddedId
    private ZipCodeID id;

    /**
     * ZipCode's label
     */
    @Column(name = "GR005LIB", length = 50)
    private String label;

    /**
     * ZipCode's sup
     */
    @Column(name = "GR005SUP", length = 1)
    private String sup;

}
