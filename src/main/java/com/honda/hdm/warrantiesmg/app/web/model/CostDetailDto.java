package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CostDetailDto {
	
	private Double laborTime;
	private Double laborCost;
	private Double totalLaborTime;
	private Double sparePartCost;
	private Double landingCost;
	private Double totalSparePart;
	private Double totalSparePartAndLabor;

}
