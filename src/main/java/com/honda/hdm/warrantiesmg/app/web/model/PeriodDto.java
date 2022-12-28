package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PeriodDto {
	
	private Long id;
	
	private String periodName;
	
	private String description;
	
	private String dateInit;
	
	private String dateEnd;
	
	private Boolean status;
}
