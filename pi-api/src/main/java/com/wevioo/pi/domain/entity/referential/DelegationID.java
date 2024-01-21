package com.wevioo.pi.domain.entity.referential;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DelegationID implements Serializable {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4750301741929883181L;

    /**
     * LocationID's id.
     */

    @Column(name = "GR002GOUV", updatable = false, nullable = false, length = 2)
    private String governorateId;

    /**
     * LocationID's id.
     */

    @Column(name = "GR003DELEG", updatable = false, nullable = false, length = 2)
    private String delegationId;
}
