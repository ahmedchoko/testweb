package com.wevioo.pi.domain.entity.referential;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;

/**
 * IdentificationType Model DataTable
 *
 *
 * @author htt
 *
 */
@Entity
@Table(name = "PI023T_IDENTIFICATION_TYPE")
@Getter
@Setter
public class IdentificationType implements Serializable {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;
    /**
     * IdentificationType's id.
     */
    @Id
    @Column(name = "PI023_ID", updatable = false, nullable = false)
    private Long id;

    /**
     * IdentificationType's type.
     */
    @Column(name = "PI023_TYPE", nullable = false, unique = true)
    private String type;

    /**
     * IdentificationType's pattern.
     */
    @Column(name = "PI023_PATTERN", nullable = false)
    private String pattern;

    /**
     * IdentificationType's isActive.
     */
    @Column(name = "PI023_IS_ACTIVE")
    private Boolean isActive;

    /**
     * IdentificationType's placeholder.
     */
    @Column(name = "PI023_PLACEHOLDER")
    private String placeholder;

    /**
     * IdentificationType's profile.
     */
    @JsonIgnore
    @Column(name = "PI023_PROFILE", nullable = false)
    private String profileName;

    @Transient
    private String[] profile;

    public void setProfile() {
        this.profile = this.profileName.split(";");
    }

    public String[] getProfile() {
        return this.profileName.split(";");
    }

}
