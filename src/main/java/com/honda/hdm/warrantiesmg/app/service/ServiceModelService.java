package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.ServiceModelDto;

public interface ServiceModelService {

	public List<ServiceModelDto> findByModel(String model);
	
}
