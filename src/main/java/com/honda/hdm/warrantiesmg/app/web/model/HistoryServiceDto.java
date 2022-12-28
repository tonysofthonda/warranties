package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class HistoryServiceDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dealerNo;
	private String serviceNo;
	private String description;
	private String dateService;
}
