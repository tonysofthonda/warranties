package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class WarrantyConsultDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String claimNo;
	private String serviceNo;
	private String comments;
	private String dateCreate;
	private String dealerNo;
	private String clientName;
	private String dealerId;
	private String report;
	private String model;
	private String dealerName;
	private String vin;
	private String motor;
	private String sale;
	private String fail;
	private String repair;
	private String km;
	private String causalPart;
	private String descriptionPart;
	private String reportSymptom;
	private String descriptionDefect;
	private String code;
	private String frt;
	private String amountPesos;
	private String usd;
	private String spareparts;
	private String unship;
	private String labor;
	private String responseDate;
	private String status;
	private String close;
	private String samples;
	private String note;

}
