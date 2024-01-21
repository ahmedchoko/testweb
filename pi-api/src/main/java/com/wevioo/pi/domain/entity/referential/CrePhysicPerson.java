package com.wevioo.pi.domain.entity.referential;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CRE_PERS_PHYSIQUE")
public class CrePhysicPerson {

    @Id
    @Column(name = "KPP", nullable = false)
    private Long id;

    @Column(name = "NDOCID", nullable = false)
    private String ndocid;

    @Column(name = "TYPDOC", nullable = false)
    private String typdoc;

    @Column(name = "LNOM", nullable = false)
    private String lastname;

    @Column(name = "LPRENOM", nullable = false)
    private String firstname;


}
