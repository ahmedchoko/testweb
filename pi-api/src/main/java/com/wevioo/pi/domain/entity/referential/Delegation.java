package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "GR003T_DELEGATIONS")
@Getter
@Setter
public class Delegation implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 8865630621020254259L;

    /**
     * Delegation's id.
     */

    @EmbeddedId
    private DelegationID id;

    /**
     * Delegation's label
     */
    @Column(name = "GR003LIB", length = 50)
    private String label;

    /**
     * Delegation's sup
     */
    @Column(name = "GR003SUP", length = 1)
    private String sup;
}
