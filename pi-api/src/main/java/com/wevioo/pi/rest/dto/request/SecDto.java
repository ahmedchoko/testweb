package com.wevioo.pi.rest.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SecDto {

	private Long id;
	private String sectionTitle;
	private String docLabel;
}