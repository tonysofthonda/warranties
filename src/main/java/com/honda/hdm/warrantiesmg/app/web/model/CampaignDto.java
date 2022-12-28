package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CampaignDto implements Serializable {

	private static final long serialVersionUID = 5015889480637031485L;

	private Long id;
	private String code;
	private String bulletinNumber;
	private String bulletinTitle;
	private String model;
	private Long year;
	private String vin;
	private String client;
	private String saleDate;
	private String repairDate;
	private String status;
	private String note;
}
