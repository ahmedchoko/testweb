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
public class AgencyId  implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4750301741929883181L;

    /**
     * AgencyId's id.
     */

    @Column(name = "GR138AG", updatable = false, nullable = false)
    private String agencyId;

    /**
     * AgencyId's id.
     */

    @Column(name = "GR028BQ", updatable = false, nullable = false)
    private String bankId;
}
