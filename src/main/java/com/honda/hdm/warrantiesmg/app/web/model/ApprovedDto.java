package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ApprovedDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ApprovedAmountDto> approved;
	private List<TotalsApprovedAmountDto> totals;

}
