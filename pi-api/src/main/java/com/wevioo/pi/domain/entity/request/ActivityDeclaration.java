package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.NatClass;
import com.wevioo.pi.domain.entity.referential.NatGroup;
import com.wevioo.pi.domain.entity.referential.NatSection;
import com.wevioo.pi.domain.entity.referential.NatSubSection;
import com.wevioo.pi.domain.enumeration.ActivityTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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
import java.io.Serializable;
import java.util.Date;

/**
 * Entity representing an Activity Declaration.
 */
@Entity
@Table(name = "PI010_ACTIVITY_DECLARATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDeclaration implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = -1234567890L;

    /**
     * ID of the activity declaration.
     */
    @Id
    @Column(name = "PI010_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Sector of the activity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI010_ACTIVITY_SECTOR", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private NatSection activitySector;

    /**
     * Subsector of the activity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI010_ACTIVITY_SUB_SECTOR", nullable = true)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private NatSubSection activitySubSector;

    /**
     * Group of the activity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI010_ACTIVITY_GROUP", nullable = true)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private NatGroup activityGroup;

    /**
     * Class of the activity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI010_ACTIVITY_CLASS", nullable = true)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private NatClass activityClass;

    /**
     * Type of the activity.
     */
    @Column(name = "PI010_TYPE")
    @Enumerated(EnumType.STRING)
    private ActivityTypeEnum type;

    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI010_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI010_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI010_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI010_MODIFIED_BY", nullable = true)
    private User modifiedBy;


    /**
     * InvestIdentification
     */
    @ManyToOne
    @JoinColumn(name = "PI010_INVEST_IDENTIFICATION_ID")
    private InvestIdentification investIdentification;

    @PrePersist
    private  void   onCreate(){
        this.creationDate = new Date();
    }

    @PreUpdate
    private void onUpdate(){
        this.modificationDate = new Date();
    }

}


