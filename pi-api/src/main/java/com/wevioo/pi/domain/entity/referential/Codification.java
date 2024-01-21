package com.wevioo.pi.domain.entity.referential;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.PrePersist;
import java.util.Date;

@Entity
@Table(name = "REF_CODIFICATION")
@Getter
@Setter
public class Codification {

    /**
     * Codification id.
     */
    @EmbeddedId
    private RefCodificationPK id;

    /**
     * Codification Social Reason
     */
    @Column(name = "RAISONSOCIALE", nullable = false)
    private String socialReason;

    /**
     * Codification Creation Date
     */

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false)
    private Date creationDate;

    /**
     * Codification Social Reason
     */
    @Column(name = "ADRESSE", nullable = false)
    private String address;


    /**
     * /**
     * Pre persist method.
     */
    @PrePersist
    private void prePersist() {
        this.creationDate = new Date();
    }
}
