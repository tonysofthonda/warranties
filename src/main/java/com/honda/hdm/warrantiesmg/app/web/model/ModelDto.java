package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter  @Setter
public class ModelDto {

	private Long id;
	
	private String name;
	
	private String model;
	
	private String codModel;
	
	private String year;
	
	private String vds;
	
	private String type;

	private String segment;
	
	private Boolean status;
	
	private PlantDto plant;
	
	private Double cchp;
	
	private String color;
}
