package com.wevioo.pi.rest.dto.response;

import com.wevioo.pi.rest.dto.request.CodificationForPostDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodificationForGetDto extends CodificationForPostDto {
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = -2122131491461435641L;
	/**
	 * ID
	 */
	String id;
}
