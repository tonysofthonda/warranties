package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.PartsReplacedDto;

public interface PartsReplacedService {
		
	List<PartsReplacedDto> getAllPartsReplaced(Long warrantyId);
	
	void savePartsReplaced(PartsReplacedDto partsReplacedDto) throws Exception;
	
	void deletePartsReplaced(Long partsReplacedId) throws Exception;
	

}
