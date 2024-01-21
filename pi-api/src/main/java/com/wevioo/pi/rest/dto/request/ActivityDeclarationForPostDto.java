package com.wevioo.pi.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDeclarationForPostDto implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -8412627355225637346L;

	/**
	 * id
	 */
	private String id;

	/**
	 * id sector activity declaration.
	 */
	private String activitySectorId;

	/**
	 * id of subSector activity declaration.
	 */
	private String activitySubSectorId;

	/**
	 * id of group activity declaration.
	 */
	private String activityGroupId;

	/**
	 * id of class activity declaration.
	 */
	private String activityClassId;

}
