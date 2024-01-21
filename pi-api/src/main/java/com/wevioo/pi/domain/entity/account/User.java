package com.wevioo.pi.domain.entity.account;

import com.wevioo.pi.domain.enumeration.Language;
import com.wevioo.pi.domain.enumeration.ProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.Date;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;

/**
 * User Model DataTable
 *
 */

@Entity
@Table(name = "PI001T_ACCOUNT")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 7732861602930366311L;

	/**
	 * User id.
	 */
	@Id
	@Column(name = "PI001_ID", updatable = false, nullable = false)
	private String id;

	/**
	 * User password.
	 */
	@Column(name = "PI001_PASSWORD")
	private String password;

	/**
	 * User email.
	 */
	@Column(name = "PI001_LOGIN", length = 150)
	private String login;

	/**
	 * User first name.
	 */
	@Column(name = "PI001_FIRST_NAME", length = 150)
	private String firstName;

	/**
	 * User last name.
	 */
	@Column(name = "PI001_LAST_NAME", length = 150)
	private String lastName;

	/**
	 * User cell phone.
	 */
	@Column(name = "PI001_CELL_PHONE", length = 15)
	private String cellPhone;

	/**
	 * User status.
	 */
	@Column(name = "PI001_IS_ACTIVE")
	private Boolean isActive;

	/**
	 * expiration Date for otp Code
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PI001_EXPIRATION_DATE")
	private Date expirationDate;

	/**
	 * User's type.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "PI001_USER_TYPE", length = 50, nullable = false)
	private UserTypeEnum userType;

	/**
	 * Creation date.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PI001_CREATION_DATETIME", nullable = false)
	private Date creationDate;

	/**
	 * Created by.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PI001_CREATED_BY")
	private User createdBy;

	/**
	 * Modification date.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PI001_MODIFICATON_DATETIME", nullable = true)
	private Date modificationDate;

	/**
	 * Is first connection
	 */
	@Column(name = "PI001_IS_FIRST_CONNECTION")
	private Boolean isFirstConnection;

	/**
	 * Modified by.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PI001_MODIFIED_BY", nullable = true)
	private User modifiedBy;

	/**
	 * password last modificationDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PI001_PASSWORD_MODIFICATON_DATE", nullable = true)
	private Date pwdModificationDate;

	/**
	 * User's Profile
	 */
	@Transient
	private ProfileEnum profile;

	/**
	 * User's language.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "PI001_USER_LANGUAGE", length = 50, nullable = false)
	private Language language;

	/**
	 * /** Pre persist method.
	 */
	@PrePersist
	private void prePersiste() {
		this.creationDate = new Date();
		this.isFirstConnection = Boolean.TRUE;

	}

	/**
	 * Pre update method.
	 */
	@PreUpdate
	private void preUpdate() {
		this.modificationDate = new Date();
	}

}
