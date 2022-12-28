package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class WarrantyDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String claimNo;
	private String vin;
	private String serviceNo;
	private String repairDate;
	private String status;
	private String clientName;
	
}
