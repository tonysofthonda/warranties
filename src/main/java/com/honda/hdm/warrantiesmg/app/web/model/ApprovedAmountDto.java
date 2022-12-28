package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ApprovedAmountDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String dealer;
	private String claim;
	private String model;
	private String description;
	private Double repair;
	private Double labor;
	private String status;
	private String part;
	private Double unship;
	private String vin;
	private Double amount;
	private String dateIni;
	private List<TotalsApprovedAmountDto> subTotals;
	private List<Long> claims;
}
