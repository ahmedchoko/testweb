package com.wevioo.pi.rest.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitoringResponse<T> extends CommonResponse {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -2998589189287560149L;
	/**
	 * Represents the content of the paginated response.
	 */
	private List<T> data;
	/**
	 * Represents the content of the paginated response.
	 */
	private List<StructureDto> structure;

}
