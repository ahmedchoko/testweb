package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "GR138T_AGENCES")
@Getter
@Setter
public class Agency  implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -7217696757657161161L;

    /**
     * AGENCY id.
     */
    @Id
    private AgencyId id;

    /**
     * AGENCY's label
     */
    @Column(name = "GR138LIB")
    private String label;
    
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GR028BQ", nullable = false, insertable = false, updatable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private Bank bank;
    /**
     * AGENCY's compositeId
     */
    @javax.persistence.Transient
    private String compositeId;

    /**
     * Setter compositeId
     *
     * @param id AgencyId
     */
    public void setCompositeId(AgencyId id) {
        this.compositeId = id.getAgencyId().concat("_" + id.getBankId());
    }

    /**
     * Getter compositeId
     * @return compositeId
     */
    public String getCompositeId() {
        return this.compositeId;

    }

}
