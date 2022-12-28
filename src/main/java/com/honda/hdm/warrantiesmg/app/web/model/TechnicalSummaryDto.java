package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TechnicalSummaryDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String deal;
	private String reporte;
	private String model;
	private String proveed;
	private String vin;
	private String engine;
	private String sale;
	private String fail;
	private String repair;
	private float km;
	private String partCausal;
	private String descriptionPart;
	private String reportedSymptom;
	private String defectDescription;
	private String code;
	private Double frt;
	private Double amountPesos;
	private Double usdt;
	private String spareParts;
	private String unship;
	private Double labor;
	private String responseDate;
	private String status;
	private String close;
	private String sample;
	private String note;

}
