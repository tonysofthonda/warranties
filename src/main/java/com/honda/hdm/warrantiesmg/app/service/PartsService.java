package com.honda.hdm.warrantiesmg.app.service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Parts;
import com.honda.hdm.warrantiesmg.app.web.model.PartDto;

public interface PartsService {
	
	Parts findPartById(Long partId);
	
	PartDto getPartByPartNumber(String partNumber) throws Exception;
	
	void savePart(PartDto part);

}
