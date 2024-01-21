package com.wevioo.pi.domain.entity.request.referential;

import java.io.Serializable;
import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * ConfigurationSearchField Model DataTable
 */
@Entity
@Table(name = "PI033T_CONFIGURATION_SEARCH_FIELD")
@Getter
@Setter
public class ConfigurationSearchField implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -7367965041093572921L;
	/**
	 * ConfigurationSearchField's id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuration_search_field_generator")
	@SequenceGenerator(name = "configuration_search_field_generator", sequenceName = "CONFIGURATION_SEARCH_FIELD_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "PI033_ID", updatable = false, nullable = false)
	private Long id;

	/**
	 * ConfigurationSearchField's investmentType
	 */

	@Enumerated(EnumType.STRING)
	@Column(name = "PI033_INVESTMENT_TYPE")
	private InvestmentType investmentType;

	/**
	 * ConfigurationSearchField's configData
	 */
	@Lob
	@Column(name = "PI033_CONFIG_DATA")
	private String configData;

	/**
	 * ConfigurationSearchField's requestFactoryBeanName
	 */
	@Column(name = "PI033_REQUEST_BEAN_NAME")
	private String requestFactoryBeanName;

	/**
	 * ConfigurationSearchField's type.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "PI033_USER_TYPE", length = 50, nullable = false)
	private UserTypeEnum userType;

}
