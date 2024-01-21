package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "PI021_AUTHORISATIONS_REQUIRED_TERMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorisationsRequired implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7712441602930366311L;

    /**
     * ID
     */
    @Id
    @Column(name = "PI021_ID" , nullable = false , updatable = false)
    private String id;

    /**
     * governor Authorisation Number
     */
    @Column(name = "PI021_GOVERNOR_AUTHORISATION_NUMBER", nullable = false)
    private Long governorAuthorisationNumber;

    /**
     * governor Authorisation Number
     */
    @Column(name = "PI021_GOVERNOR_AUTHORISATION_DATE", nullable = false)
    private Date governorAuthorisationDate;

    /**
     * authorisation BCT: YES / NO
     */
    @Column(name = "PI021_AUTHORISATION_BCT", nullable = false)
    private Boolean authorisationBCT;

    /**
     * reference Authorisation BCT
     */
    @Column(name = "PI021_REFERENCE_AUTHORISATION_BCT")
    private String referenceAuthorisationBCT;

    /**
     * Authorisation BCT Date
     */
    @Column(name = "PI021_AUTHORISATION_BCT_DATE")
    private Date authorisationBCTDate;

    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI021_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI021_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI021_MODIFICATON_DATETIME")
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI021_MODIFIED_BY")
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
