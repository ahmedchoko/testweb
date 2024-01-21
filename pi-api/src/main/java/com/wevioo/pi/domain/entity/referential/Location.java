package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "GR004T_LOCALITES")
@Getter
@Setter
public class Location implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 8865630621020254259L;

    /**
     * Location's id.
     */

    @EmbeddedId
    private LocationID id;

    /**
     * Location's label
     */
    @Column(name = "GR004LIB", length = 50)
    private String label;

    /**
     * Location's is zone AVT
     */
    @Column(name = "GR004ZONAVT")
    private Boolean zoneAVT;

    /**
     * Location's sup
     */
    @Column(name = "GR004SUP", length = 1)
    private String sup;

    /**
     * Location's compositeId
     */
    @javax.persistence.Transient
    private String compositeId;

    /**
     * Setter compositeId
     *
     * @param id LocationID
     */
    public void setCompositeId(LocationID id) {
        this.compositeId = id.toString();
    }
}