package com.wevioo.pi.domain.entity.referential;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
/**
 * Parameter Model DataTable
 * 
 * @author kad
 *
 */
@Entity
@Table(name = "PI030T_PARAMETER")
@Getter
@Setter
public class Parameter implements Serializable {

	/**
	 * The generated id.
	 */
	private static final long serialVersionUID = 7309233230953005572L;

	/**
	 * The unique id.
	 */
	@Id
	@Column(name = "PI030_ID", unique = true, nullable = false)
	private Long id;
	
	/**
	 * {@link Parameter} parameterKey.
	 */
	@Column(name = "PI030_PARAMETER_KEY")
	private String parameterKey;

	/**
	 * {@link Parameter} parameterValue.
	 */
	@Column(name = "PI030_PARAMETER_VALUE")
	private String parameterValue;

	/**
	 * {@link Parameter} parameterPattern.
	 */
	@Column(name = "PI030_PARAMETER_PATTERN")
	private String parameterPattern;

	/**
	 * {@link Parameter} creationDate.
	 */
	@Column(name = "PI030_CREATION_DATE")
	private Date creationDate = new Date();
}
