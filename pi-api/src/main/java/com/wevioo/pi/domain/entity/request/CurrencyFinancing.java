package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Entity representing Currency Financing  .
 */
@Entity
@Table(name = "PI025_CURRENCY_FINANCING")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyFinancing implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * ID of the currency financing data.
     */
    @Id
    @Column(name = "PI025_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI025_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI025_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI025_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI025_MODIFIED_BY")
    private User modifiedBy;

    @OneToMany(mappedBy = "currencyFinancing" , fetch = FetchType.EAGER  , cascade = CascadeType.ALL)
    private List<CurrencyFinancingData> currencyFinancingData;

    @PrePersist
    void onCreate(){
        this.creationDate = new Date();
    }
    @PreUpdate
    void onUpdate(){
        this. modificationDate = new Date();
    }
}
