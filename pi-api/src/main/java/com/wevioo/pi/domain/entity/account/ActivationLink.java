package com.wevioo.pi.domain.entity.account;

import com.wevioo.pi.domain.enumeration.LinkEnum;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;import java.io.Serializable;
import java.util.Date;

/**
 * Activation model dataTable
 */
@Entity
@Table(name = "PI005T_ACTIVATION_LINK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivationLink implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = 3795387557143993810L;

    /**
     * Link id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activation_link_generator")
    @SequenceGenerator(name = "activation_account_generator", sequenceName = "CITIZEN_ACCOUNT_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "PI005_ID", updatable = false, nullable = false)
    private Long id;

    /**
     * Link type
     */
    @Column(name = "PI005_LINK_TYPE", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private LinkEnum type;

    /**
     * Link key
     */
    @Column(name = "PI005_ACTIVATION_KEY", length = 50, nullable = false, unique = true)
    private String activationKey;

    /**
     * Link user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI005_USER_ID", nullable = true)
    private User user;

    /**
     * Link expiration date
     */
    @Column(name = "PI005_EXPIRATION_DATE", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date expirationDate;

    /**
     * ActivationLink's numberAttempts
     */
    @Column(name = "PI005_NUMBER_ATTEMPS", nullable = true)
    private Integer numberAttempts;
}
