package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class HistoricalAmountsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dealerNumber;
	private String claimNumber;
	private String model;
	private String vin;
	private String dateOrder;
	private String status;
	private String periodDateIni;
	private String periodDateEnd;
	private String partNumber;
	private String description;
	private Double frt;
	private String amount;
	private Double costPieceUnit;
	private String costUnship;
	private String timeLabor;
	private Double total;

}
