package com.wevioo.pi.domain.entity.referential;

import com.wevioo.pi.domain.enumeration.KeyGenType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Key gen.
 * @author kad
 *
 */
@Getter
@Setter
@Entity
@Table(name = "PI008T_KEY_GEN")
public class KeyGen implements Serializable {

    /**
     * Serial unique id.
     */
    private static final long serialVersionUID = -593723854561762794L;
    /**
     * L'identifiant du key generator.
     */

    @Id
    @Column(name = "PI008_ID", updatable = false, nullable = false)
    private Long id;

    /**
     * Digits value.
     */
    @Column(name = "PI008_DIGITS", nullable = false)
    private int digits;



    /**
     * Prefix value.
     */
    @Column(name = "PI008_PREFIX", nullable = true)
    private String prefix;

    /**
     * Key type.
     */
    @Column(name = "PI008_TYPE",  nullable = false)
    @Enumerated(EnumType.STRING)
    private KeyGenType type;

    /**
     * Key value.
     */
    @Column(name = "PI008_VALUE", nullable = false)
    private String value;

    /**
     * Key criteria.
     */
    @Column(name = "PI008_CRETERIA", nullable = true)
    private String criteria;

}
