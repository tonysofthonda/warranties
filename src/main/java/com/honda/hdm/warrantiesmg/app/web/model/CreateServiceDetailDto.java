package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.honda.hdm.warrantiesmg.app.domain.entity.Dealer;
import com.honda.hdm.warrantiesmg.app.domain.entity.Model;
import com.honda.hdm.warrantiesmg.app.domain.entity.ServiceClient;
import com.honda.hdm.warrantiesmg.app.domain.entity.Vin;

import lombok.Data;

@Data
public class CreateServiceDetailDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String serviceNumber;
	private String status;
	private Dealer dealer;
	private Double km;
	private Model model;
	private Vin vin;
	private ServiceClient serviceClient;
	private String saleDate;
	private String soldBy;
	private String owner;
	private String technical;
	private String servicePerformed;
	private String serviceDetail;
	private String createReportBy;
	private String dateService;
	private Timestamp createTimestamp;
	private Timestamp updateTimestamp;

}
