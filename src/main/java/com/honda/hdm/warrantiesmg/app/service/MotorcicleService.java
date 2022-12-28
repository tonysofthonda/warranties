package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.WarrantyClaimsDto;

public interface MotorcicleService {
	
	List<WarrantyClaimsDto> getAll();

}
