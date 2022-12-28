package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.PeriodDto;

public interface PeriodService {
	
	List<PeriodDto> getAllPeriods();
	
	void savePeriod(PeriodDto dto);
	
	void updatePeriodByCron();
	
	void updateStatusPeriod(PeriodDto periodDto) throws Exception;

}
