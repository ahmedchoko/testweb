package com.wevioo.pi.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActivitySupportReportingDto {
	private String activitySupportTypeReq;
	 private String activitySupportAuthReq;
	 private String activitySupportDateReq;
	 private String activitySupportNumReq;
	 private String activitySupportTypeRes;
	 private String activitySupportAuthRes;
	 private String activitySupportDateRes;
	 private String activitySupportNumRes;

}
