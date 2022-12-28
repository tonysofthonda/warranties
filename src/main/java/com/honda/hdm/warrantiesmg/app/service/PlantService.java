package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.PlantDto;

public interface PlantService {

	List<PlantDto> getAllPlants();
	
	void savePlant(PlantDto plant);
}
