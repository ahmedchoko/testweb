package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * InvestIdentification Entity
 */
@Entity
@Table(name = "PI009T_INVEST_IDENTIFICATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvestIdentification implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7732861602930366311L;

    /**
     * ID
     */
    @Id
    @Column(name = "PI009_ID" , nullable = false , updatable = false)
    private String id;

    /**
     * unique Identifier
     */
    @Column(name = "PI009_UNIQUE_IDENTIFIER" , nullable = false)
    private String uniqueIdentifier;

    /**
     * company Name
     */
    @Column(name = "PI009_COMPANY_NAME")
    private String companyName;

    /**
     * legal Form
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PI009_LEGAL_FORM_IDFK", nullable = false)
    @Cascade({ org.hibernate.annotations.CascadeType.MERGE })
    private SpecificReferential legalForm;

    /**
     * quoted
     */
    @Column(name = "PI009_QUOTED")
    private Boolean quoted;
    /**
     * resident
     */
    @Column(name = "PI009_RESIDENT")
    private boolean resident;

    /**
     * secondary Activity Support
     */
    @Column(name = "PI009_SECONDARY_ACTIVITY_SUPPORT")
    private boolean secondaryActivitySupport;

    /**
     * main Activity Support
     */
    @Column(name = "PI009_MAIN_ACTIVITY_SUPPORT")
    private boolean mainActivitySupport;

    /**
     * secondary Activity Declaration
     */
    @Column(name = "PI009_SECONDARY_ACTIVITY_DECLARATION")
    private boolean secondaryActivityDeclaration;

    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI009_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI009_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI009_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI009_MODIFIED_BY", nullable = true)
    private User modifiedBy;
    /**
     * authority Number
     */

    @Column(name = "PI009_AUTHORITY_NUMBER")
    private  String  authorityNumber ;

    /**
     * authority Number
     */

    @Column(name = "PI009_AUTHORITY_DATE")
    private  Date  authorityDate ;
    /**
     * bct Authorization
     */
    @Column(name =  "PI009_BCT_AUTHORIZATION")
    private Boolean bctAuthorization;
    /**
     * list of activityDeclaration
     */
    @OneToMany(mappedBy = "investIdentification" , cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<ActivityDeclaration>  activityDeclarations = new ArrayList<>();

    /**
     * list of activity Supports
     */
    @OneToMany(mappedBy = "investIdentification", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ActivitySupport> activitySupports = new ArrayList<>();

    @OneToOne(mappedBy = "investIdentification" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private  DirectInvestRequest directInvestRequest;
    @PrePersist
    private  void   onCreate(){
        this.creationDate = new Date();
    }

    @PreUpdate
    private void onUpdate(){
        this.modificationDate = new Date();
    }






}


