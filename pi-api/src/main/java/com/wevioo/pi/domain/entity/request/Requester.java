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
import javax.persistence.OneToOne;
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
@Table(name = "PI028_REQUESTER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Requester implements Serializable {

	/**
	 * Serial Number
	 */
    private static final long serialVersionUID = -4692078996237409340L;
	/**
     * ID of the activity support.
     */
    @Id
    @Column(name = "PI028_ID", nullable = false, updatable = false)
    private String id;
    /**
     * identification Type
     */
    @Column(name =  "PI028_IDENTIFICATION_TYPE")
    private String identificationType;
    /**
     * identification value
     */
    @Column(name =  "PI028_IDENTIFICATION_VALUE")
    private String identificationValue;
    /**
     * first name
     */
    @Column(name = "PI028_FIRST_NAME")
    private String firstName;
    /**
     * last name
     */
    @Column(name = "PI028_LAST_NAME")
    private String lastName;
    /**
     * social Reason
     */
    @Column(name =  "PI028_SOCIAL_REASON")
    private String socialReason;

    /**
     * EMAIL
     */
    @Column( name = "PI028_EMAIL")
    private String email;

    /**
     * ADDRESS
     */
    @Column( name = "PI028_ADDRESS")
    private String address;

    /**
     * PHONE NUMBER
     */
    @Column( name = "PI028_PHONE_NUMBER")
    private String  phoneNumber;

    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI028_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI028_CREATED_BY" , nullable = false)
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI028_MODIFICATON_DATETIME")
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI028_MODIFIED_BY")
    private User modifiedBy;

    @OneToOne(mappedBy = "requester" , cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
    private DirectInvestRequest directInvestRequest;



    @PrePersist
    private  void   onCreate(){
        this.creationDate = new Date();
    }

    @PreUpdate
    private void onUpdate(){
        this.modificationDate = new Date();
    }






}
