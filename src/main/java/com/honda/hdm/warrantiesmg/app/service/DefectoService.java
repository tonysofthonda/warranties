package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.DefectosDto;

public interface DefectoService {
	
	List<DefectosDto> getAllDedefectos();
	
	void saveDefect(DefectosDto defectosNew);
	
	void updateDefect(DefectosDto defectosDto);
	
	void updateStatusDefect(DefectosDto defectosDto);
	
}
