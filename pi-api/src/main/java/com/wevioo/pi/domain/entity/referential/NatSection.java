package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * NatSection Model DataTable
 *
 * @author  knh
 *
 */
@Entity
@Table(name = "R_NAT_SECTION")
@Getter
@Setter
public class NatSection  implements Serializable {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = -8115650653859707569L;



    /**
     *  Nat Section id.
     */
    @Id
    @Column(name = "KNAT_S", updatable = false, nullable = false)
    private String id;


    /**
     *  NatSection label;
     */
    @Column(name = "LIBELLE" , nullable = false)
    private String  label;
}
