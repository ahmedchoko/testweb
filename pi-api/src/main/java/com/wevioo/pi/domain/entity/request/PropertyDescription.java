package com.wevioo.pi.domain.entity.request;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Governorate;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.domain.enumeration.DepositType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  Direct Invest Request Entity
 */
@Entity
@Table(name = "PI029_PROPERTY_DESCRIPTION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDescription implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7712561602930366311L;

    /**
     * ID
     */
    @Id
    @Column(name = "PI029_ID" , nullable = false , updatable = false)
    private String id;


    /**
     * Property Request's isRegistered
     */
    @Column(name = "PI029_IS_REGISTERED", nullable = false)
    private Boolean isRegistered;


    /**
     * deposit Type
     */
    @Column(name = "PI029_DEPOSIT_TYPE")
    @Enumerated(EnumType.STRING)
    private DepositType depositType;



    /**
     * Property Request's invest type
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI029_INVEST_TYPE_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private SpecificReferential investType;

    /**
     * Property Request's usage
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI029_USAGE_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private SpecificReferential usage;

    /**
     * Property Request's vocation
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI029_VOCATION_IDFK")
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private SpecificReferential vocation;

    /**
     * Property Request's location
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI029_LOCATION_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private SpecificReferential location;

    /**
     * Property Request's address
     */
    @Column(name = "PI029_ADDRESS", nullable = false)
    private String address;
    /**
     * Property Request's governorate
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI029_GOVERNORATE_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private Governorate governorate;

    /**
     * Property Request's delegation
     */
    @Column(name = "PI029_DELEGATION")
    private String delegation;

    /**
     * Property Request's location
     */
    @Column(name = "PI029_LOCALITY")
    private String locality;

    /**
     * Property Request's zip code
     */
    @Column(name = "PI029_ZIP_CODE")
    private String zipCode;

    /**
     * Property Request's name of land title
     */
    @Column(name = "PI029_NAME_OF_LAND_TITLE")
    private String nameOfLandTitle;

    /**
     * Property Request's land title number
     */
    @Column(name = "PI029_LAND_TITLE_NUMBER")
    private Long landTitleNumber;

    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI029_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI029_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI029_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI029_MODIFIED_BY", nullable = true)
    private User modifiedBy;




    /**
     * /**
     * Pre persist method.
     */
    @PrePersist
    private void prePersist() {
        this.creationDate = new Date();
    }

    /**
     * Pre update method.
     */
    @PreUpdate
    private void preUpdate() {
        this.modificationDate = new Date();
    }


}
