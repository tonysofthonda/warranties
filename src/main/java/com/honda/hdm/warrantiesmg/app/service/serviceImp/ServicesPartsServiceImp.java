package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.ServicesParts;
import com.honda.hdm.warrantiesmg.app.domain.repository.ServicesPartsRepository;
import com.honda.hdm.warrantiesmg.app.service.ServicesPartsService;

@Service
public class ServicesPartsServiceImp implements ServicesPartsService{
	
	@Autowired
	ServicesPartsRepository partRepository;

	@Override
	public List<ServicesParts> findAll() {
		// TODO Auto-generated method stub
		return this.partRepository.findAll();
	}

}
