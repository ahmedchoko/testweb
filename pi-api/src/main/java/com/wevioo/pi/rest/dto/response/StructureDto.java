package com.wevioo.pi.rest.dto.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author kad
 *
 */

@Getter
@Setter
@Builder
public class StructureDto implements Serializable {
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 8460235210568954423L;
	private String dataIndex;
	private String sortIndex;
	private LabelData label;
}

@Getter
@Setter
class LabelData implements Serializable {
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 9190881099743549283L;
	private String fr;
	private String en;
}