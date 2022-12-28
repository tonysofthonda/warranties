package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class InfoMobileUnitDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String vin;
	private Long model;
	private String engineSeries;
	private String saleDate;
	private String soldBy;
	private Double km;
	private String owner;
}
