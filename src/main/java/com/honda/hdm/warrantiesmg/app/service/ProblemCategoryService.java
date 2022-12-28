package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.ProblemCategoryDto;

public interface ProblemCategoryService {
	
	List<ProblemCategoryDto> getProblemCategory(Boolean status);
	
	void saveProblemCategory(ProblemCategoryDto problemCategoryDto) throws Exception;
	
	void changeStatusProblemCategory(ProblemCategoryDto problemCategoryDto) throws Exception;

}
