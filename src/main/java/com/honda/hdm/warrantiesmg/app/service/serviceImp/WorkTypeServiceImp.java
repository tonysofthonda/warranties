package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.WorkType;
import com.honda.hdm.warrantiesmg.app.domain.repository.WorkTypeRepository;
import com.honda.hdm.warrantiesmg.app.service.WorkTypeService;

@Service
public class WorkTypeServiceImp implements WorkTypeService{

	@Autowired
	private WorkTypeRepository workTypeRepository;
	
	@Override
	public List<WorkType> findAll() {
		// TODO Auto-generated method stub
		return this.workTypeRepository.findAll();
	}

	@Override
	public List<WorkType> findByIdOrderType(Long id) {
		// TODO Auto-generated method stub
		return this.workTypeRepository.findByOrderTypeIdOrderType(id);
	}

	@Override
	public WorkType findByIdWorkType(Long id) {
		// TODO Auto-generated method stub
		return this.workTypeRepository.findByIdWorkType(id);
	}

}
