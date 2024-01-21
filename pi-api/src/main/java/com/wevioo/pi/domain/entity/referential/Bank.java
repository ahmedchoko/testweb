package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Bank Model DataTable
 *
 * @author  knh
 *
 */
@Entity
@Table(name = "GR028T_BANQUES")
@Getter
@Setter
public class Bank  implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -8115650653859707569L;



    /**
     * Bank id.
     */
    @Id
    @Column(name = "GR028BQ", updatable = false, nullable = false)
    private String id;


    /**
     * Bank's code
     */
    @Column(name = "GR028ABR")
    private String code;

    /**
     * Bank's label
     */
    @Column(name = "GR028LIB")
    private String label;

    /**
     * Bank's agencies
     */
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Agency> agency;
}
