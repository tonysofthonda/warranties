package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class InfoReportDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String serviceNumber;
    private String status;
    private String dateCreated;
    private String numberDealer;
    private String nameDealer;
    private String createReportBy;
}
