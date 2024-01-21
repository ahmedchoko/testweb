package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Country Model DataTable
 *
 * @author shl
 *
 */
@Entity
@Table(name = "GR030T_PAYS")
@Getter
@Setter
public class Country implements Serializable {


    /**
     * Serial Number
     */
    private static final long serialVersionUID = -8892641119788252565L;

    /**
     * Country id.
     */
    @Id
    @Column(name = "GR030PAYS", updatable = false, nullable = false)
    private String id;

    /**
     * Country's code
     */
    @Column(name = "GR030ABR1")
    private String code;

    /**
     * Country's code2
     */
    @Column(name = "GR030ABR2")
    private String code2;

    /**
     * Country's label
     */
    @Column(name = "GR030LIB")
    private String label;
    /**
     * Country's labelEn
     */
    @Column(name = "GR030LIB_EN")
    private String labelEn; 

    /**
     * Country's  Nationality
     */
    @Column(name = "GR030NALITE")
    private String nationality;
    
    /**
     * Country's  nationalityEn
     */
    @Column(name = "GR030NALITE_EN")
    private String nationalityEn;
}
