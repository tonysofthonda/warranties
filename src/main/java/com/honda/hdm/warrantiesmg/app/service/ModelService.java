package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.ModelDto;

public interface ModelService {
	
	List<ModelDto> getAllModels();
	
	ModelDto getById(Long id);
	
	void saveModel(ModelDto modelDto);

	void updateStatus(ModelDto modelDto);
	
	ModelDto getOneModel(final String model);

}
