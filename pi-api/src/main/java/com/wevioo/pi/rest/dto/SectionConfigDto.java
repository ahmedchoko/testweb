package com.wevioo.pi.rest.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SectionConfigDto implements Serializable {

	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -6863855140571446988L;
	
	/**
	 * Section's id
	 */
	private Long id;
	/**
	 * Section's label
	 */
	private String label;
	/**
	 * SectionConfigDto's documentConfigList
	 */
	private List<DocumentConfigDto> documentConfigList;

}
