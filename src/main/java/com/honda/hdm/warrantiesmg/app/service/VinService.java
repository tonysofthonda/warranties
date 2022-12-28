package com.honda.hdm.warrantiesmg.app.service;

import com.honda.hdm.warrantiesmg.app.web.model.VinDto;

public interface VinService {

	VinDto findByVin(String vin);
	
}
