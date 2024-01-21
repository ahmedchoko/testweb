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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity representing   Importation Piece.
 */
@Entity
@Table(name = "PI018_IMPORTATION_PIECE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportationPiece  implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * ID of the Financial Mode.
     */
    @Id
    @Column(name = "PI017_ID", nullable = false, updatable = false)
    private String id;
    @ManyToOne
    @JoinColumn(name = "PI017_DIRECT_INVEST_REQUEST")
    private  DirectInvestRequest directInvestRequest;


    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI017_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI017_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI017_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI017_MODIFIED_BY", nullable = true)
    private User modifiedBy;
}
