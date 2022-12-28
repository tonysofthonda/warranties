package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.domain.entity.WorkType;

public interface WorkTypeService {

	List<WorkType> findAll();
	
	WorkType findByIdWorkType(Long id);
	
	List<WorkType> findByIdOrderType(Long id);
	
}
