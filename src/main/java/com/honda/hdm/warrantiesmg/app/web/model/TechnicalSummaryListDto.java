package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TechnicalSummaryListDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TechnicalSummaryDto> accepted;
	private List<TechnicalSummaryDto> ge;
	private List<TechnicalSummaryDto> gw;
	private List<TechnicalSummaryDto> pending;
	private List<TechnicalSummaryDto> rejected;
	private List<TechnicalSummaryDto> campaign;
}
