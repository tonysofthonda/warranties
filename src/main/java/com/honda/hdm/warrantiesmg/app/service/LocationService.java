package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.LocationDto;

public interface LocationService {
	
	List<LocationDto> getAllLocations();

	List<LocationDto> getlocationByIdState(Long id);
	
	LocationDto getByIdSelect(Long id);
}
