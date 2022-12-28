package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtherExpensesDto {
	
	private Long id;
	
	private String description;
	
	private Integer quantity;
	
	private String details;
	
	private String invoice;	
	
	private Double cost;
	
	private Double total;
	
	private Long warrantyClaims;

}
