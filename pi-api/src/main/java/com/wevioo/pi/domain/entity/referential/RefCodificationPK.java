package com.wevioo.pi.domain.entity.referential;


import lombok.AllArgsConstructor;
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
public class RefCodificationPK implements Serializable {

	/**
	 * Serial Number
	 */
    private static final long serialVersionUID = -7763845219101470282L;

	@Column(name = "CODEIDENTIFIANT", nullable = false)
    private String uniqueIdentifier;

    @Column(name = "TYPEIDENTIFIANT", nullable = false)
    private String typeIdentifiant;
}
