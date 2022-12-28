package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.domain.entity.Symptom;
import com.honda.hdm.warrantiesmg.app.web.model.SintomaDto;

public interface SintomaServie {
	
	List<SintomaDto> getSintomas();
	
	SintomaDto getByIdSintoma(Long id);
	
	Symptom getByCodigo(String codigo);
	
	void saveSintoma(SintomaDto sintomaDto);
	
	void updateSintoma(SintomaDto sintomaDto);
	
	void updateStatusSintoma(SintomaDto sintomaDto);
}
