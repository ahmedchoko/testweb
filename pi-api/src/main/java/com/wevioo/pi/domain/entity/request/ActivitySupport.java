package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
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
 * Entity representing Activity Support.
 */
@Entity
@Table(name = "PI011_ACTIVITY_SUPPORT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySupport implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * ID of the activity support.
     */
    @Id
    @Column(name = "PI011_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Type of activity support.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI011_TYPE_ACTIVITY_SUPPORT_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private SpecificReferential typeActivitySupport;

    /**
     * Issuing authority of the support.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI011_ISSUING_AHTORITY_IDFK", nullable = true)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private SpecificReferential issuingAuthority;

    /**
     * Support number.
     */
    @Column(name = "PI011_SUPPORT_NUMBER")
    private String supportNumber;

    /**
     * Date of support issuance.
     */
    @Column(name = "PI011_DATE_SUPPORT")
    @Temporal(TemporalType.DATE)
    private Date dateSupport;

    /**
     * Type of support (Enum).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PI011_ACTIVITY_TYPE")
    private ActivityTypeEnum activityType;


    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI011_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI011_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI011_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI011_MODIFIED_BY", nullable = true)
    private User modifiedBy;

    /**
     * InvestIdentification
     */
    @ManyToOne
    @JoinColumn(name = "PI011_INVEST_IDENTIFICATION_ID")
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
