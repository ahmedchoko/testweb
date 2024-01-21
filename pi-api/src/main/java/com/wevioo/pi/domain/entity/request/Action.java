package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.domain.enumeration.StepEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Action Model DataTable
 *
 * @author knh
 *
 */
@Entity
@Table(name = "PI032T_ACTION")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Action implements Serializable {


    /**
     * The generated id.
     */
    private static final long serialVersionUID = 2094344180905329310L;
    /**
     * {@link  Action} ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType. AUTO)
    @Column(name = "PI032_ID")
    private  Long id;
    /**
     * {@link  Action}  USER ID.
     */
    @Column(name = "PI032_USER_ID")
    private  String userId;
    /**
     * {@link  Action} step.
     */
    @Column(name = "PI032_STEP")
    private StepEnum step;
    /**
     * {@link  Action} ficheId.
     */
    @Column(name = "PI032_FICHE_ID")
    private  String ficheId;
    /**
     * {@link  Action} OperationType.
     */
    @Column(name = "PI032_OPERATION_TYPE")
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    /**
     * {@link  Action} creationDate.
     */
    @Column(name = "PI032_CREATION_DATE")
    private Date creationDate = new Date();

    /**
     * {@link  Action}  modificationDate.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI032_MODIFICATON_DATETIME")
    private Date modificationDate;

    /**
     *  * {@link  Action}   status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PI032_STATUS")
    private RequestStatusEnum status;
}
