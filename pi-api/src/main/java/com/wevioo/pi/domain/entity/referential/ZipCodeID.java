package com.wevioo.pi.domain.entity.referential;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZipCodeID implements Serializable {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4750301741929883181L;

    /**
     * ZipCodeID's id.
     */

    @Column(name = "GR002GOUV", updatable = false, nullable = false, length = 2)
    private String governorateId;

    /**
     * ZipCodeID's id.
     */

    @Column(name = "GR003DELEG", updatable = false, nullable = false, length = 2)
    private String delegationId;

    /**
     * ZipCodeID's id.
     */
    @Column(name = "GR004LOC", updatable = false, nullable = false, length = 2)
    private String locaId;

    /**
     * ZipCodeID's id.
     */
    @Column(name = "GR005CPOST", updatable = false, nullable = false, length = 4)
    private String zipCodeId;
}
