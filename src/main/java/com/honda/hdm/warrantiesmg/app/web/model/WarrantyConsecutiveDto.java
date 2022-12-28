package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WarrantyConsecutiveDto {
	
	private Long id;
	
	private Integer dealerNumber;
	
	private Integer currentPeriod;
	
	private Integer consecutive;
	
	private String warrantyConsecutive;
	
	private String dealer;
	
}
