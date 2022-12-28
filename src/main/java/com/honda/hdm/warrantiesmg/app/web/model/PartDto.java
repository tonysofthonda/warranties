package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartDto {
	
	private Long id;
	
	private String model;
	
	private Double frt;
		
	private Integer yearModelFrom;
	
	private Integer yearModelTo;
	
	private String partNumber;
	
	private String descriptionPart;
	
	private Double price;
	
	private String lonCode;
	
	private Integer reference;
	
	private String block;
	
	private String publishCatalogNumber;

}
