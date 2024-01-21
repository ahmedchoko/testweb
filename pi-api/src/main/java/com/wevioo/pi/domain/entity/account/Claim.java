package com.wevioo.pi.domain.entity.account;

import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.domain.enumeration.ClaimStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Claim Model DataTable
 *
 * @author dia
 */
@Entity
@Table(name = "PI040_CLAIM")
@Getter
@Setter
public class Claim implements Serializable {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = -2343605681616647912L;
    /**
     * Claim's id.
     */
    @Id
    @Column(name = "PI040_ID", updatable = false, nullable = false)
    private String id;

    /**
     * Claim's reason
     */
    @Column(name = "PI040_REASON")
    private String reason;

    /**
     * User cell phone.
     */
    @Column(name = "PI040_CELL_PHONE", length = 15)
    private String cellPhone;
    /**
     * Claim's email.
     */
    @Column(name = "PI040_EMAIL")
    private String email;

    /**
     * Claim's assignedTo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI040_MODIFIED_BY", nullable = true)
    private User modifiedBy;

    /**
     * Claim's description.
     */
    @Lob
    @Column(name = "PI040_DESCRIPTION")
    private String description;


    /**
     * Claim's Depositor.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI040_CREATED_BY")
    private User depository;


    /**
     * Claim's response.
     */
    @Column(name = "PI040_RESPONSE")
    private String response;

    /**
     * Claim's status.
     */
    @Column(name = "PI040_STATUS")
    private ClaimStatusEnum status;

    /**
     * Claim's attachment.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PI040_ATTACHMENT_IDFK", nullable = true)
    private Attachment attachment;

    /**
     * Claim's creation date
     */
    @Column(name = "PI040_CREATION_DATE")
    private Date creationDate = new Date();

    /**
     * Claim's modification date
     */
    @Column(name = "PI040_MODIFICATION_DATE")
    private Date modificationDate;


    /**
     * The first name of the person submitting the contact form.
     */
    @Column(name = "PI040_FIRST_NAME", updatable = false)
    private String firstName;

    /**
     * The last name of the person submitting the contact form.
     */
    @Column(name = "PI040_LAST_NAME", updatable = false)
    private String lastName;
    /**
     * Claim's user nature EN
     */
    @Column(name = "PI040_USER_NATURE_EN", nullable = true)
    private String userNatureEn;

    /**
     * Claim's user nature FR
     */
    @Column(name = "PI040_USER_NATURE_FR", nullable = true)
    private String userNatureFr;
}
