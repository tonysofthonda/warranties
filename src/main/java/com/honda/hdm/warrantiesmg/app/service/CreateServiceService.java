package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.CreateServiceDetailDto;
import com.honda.hdm.warrantiesmg.app.web.model.CreateServiceDto;
import com.honda.hdm.warrantiesmg.app.web.model.HistoryServiceDto;
import com.honda.hdm.warrantiesmg.app.web.model.InfoCreateServiceDto;

public interface CreateServiceService {

	List<CreateServiceDto> getAllServiceCreated();
	
	void saveCreateService(InfoCreateServiceDto infoCreateService);
	
	String getServiceNumber();
	
	void deleteService(Long id);
	
	void updateService(InfoCreateServiceDto infoCreateService, long id);
	
	List<HistoryServiceDto> getHistoryService(final String vin);

	CreateServiceDetailDto getServiceCreatedDetail(long id);
	
}
