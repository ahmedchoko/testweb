package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Currency Model DataTable
 *
 *
 * @author shl
 *
 */
@Entity
@Table(name = "GR027T_DEVISES")
@Getter
@Setter
public class Currency implements Serializable {


    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7815776217790697672L;

    /**
     * Currency's id.
     */
    @Id
    @Column(name = "GR027DEVISE", updatable = false, nullable = false)
    private String id;


    /**
     * Currency's code
     */
    @Column(name = "GR027ABR")
    private String code;

    /**
     * Currency's label
     */
    @Column(name = "GR027LIB")
    private String label;

}