package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.ZoneDto;

public interface ZoneService {

	public List<ZoneDto> getAllZone();
	
	public ZoneDto getById(Long id);
	
	public void saveZone(ZoneDto dto);
}
