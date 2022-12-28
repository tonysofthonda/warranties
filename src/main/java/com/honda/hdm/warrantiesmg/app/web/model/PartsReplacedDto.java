package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartsReplacedDto {
	
	private Long id;
	
	private String description;

	private Integer quantity;
	
	private Double packingCost;

	private Double unitCost;

	private Double total;
	
	private PartDto part;
	
	private Long warrantyClaimsId;

}
