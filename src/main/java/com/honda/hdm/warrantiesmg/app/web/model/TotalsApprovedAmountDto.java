package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TotalsApprovedAmountDto implements Serializable {

	private static final long serialVersionUID = -2454646231464838191L;
	private String title;
	private String total;

}
