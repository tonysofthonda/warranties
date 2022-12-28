package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter
public class SettingDto {
	
	private Long id;
	
	private String name;
	
	private Double valueNumber;
	
	private String valueText;
	
	private String lastUpdate;
	

}
