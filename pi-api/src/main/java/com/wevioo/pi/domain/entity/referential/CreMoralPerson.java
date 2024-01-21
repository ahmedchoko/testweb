package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "CRE_PERS_MORALE")
@Getter
@Setter
public class CreMoralPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "KPM", nullable = false)
    private Long id;

    @Column(name = "CCNSS")
    private String ccnss;

    @Column(name = "CMATRFISC")
    private String uniqueIdentification;

    @Column(name = "LRSOC")
    private String socialReason;

    @Column(name = "CIN")
    private String taxIdentification;

}
