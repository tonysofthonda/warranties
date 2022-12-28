package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.CampaignDto;
import com.honda.hdm.warrantiesmg.app.web.model.HistoricalAmountsDto;
import com.honda.hdm.warrantiesmg.app.web.model.TechnicalSummaryListDto;

public interface GeneralReportsService {

	TechnicalSummaryListDto technicalSummaryReport(final String dateIni, final String dateEnd);
	
	void saveUnitsCampignReport(final byte[] fileBytes);
	
	List<CampaignDto> unitsCampignReport();
	
	List<HistoricalAmountsDto> historicalAmountsReport(final String dateIni, final String dateEnd);
	
	void deleteCampaignRecord(final Long id);
	
	void updateCampaignRecord(final CampaignDto campaignDto);
}
