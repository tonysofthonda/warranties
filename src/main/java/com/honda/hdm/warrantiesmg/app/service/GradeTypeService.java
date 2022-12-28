package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.GradeTypeDto;

public interface GradeTypeService {
	
	List<GradeTypeDto> getAllGradeType(Boolean status);
	
	void saveGradeType(GradeTypeDto gradeTypeDto) throws Exception;
	
	void updateStatusGradeType(GradeTypeDto gradeTypeDto) throws Exception; 

}
