package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.DefectosDto;
import com.honda.hdm.warrantiesmg.app.web.model.PartsDefectsSymptomDto;
import com.honda.hdm.warrantiesmg.app.web.model.SintomaDto;

public interface PartsDefectsSymptomService {
	
	List<DefectosDto> getDefectsByPartId(Long partId);
	
	List<SintomaDto> getSymptomDtoByPartIdAndDefectId(Long partId, Long defectId);
	
	List<PartsDefectsSymptomDto> getAllRelated(Boolean status);

	void saveRelated(PartsDefectsSymptomDto partsDefectsSymptomDto) throws Exception;

	void updateStatusRelated(PartsDefectsSymptomDto partsDefectsSymptomDto);
}
