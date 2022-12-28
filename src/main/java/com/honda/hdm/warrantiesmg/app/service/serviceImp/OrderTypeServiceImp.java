package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.OrderType;
import com.honda.hdm.warrantiesmg.app.domain.repository.OrderTypeRepository;
import com.honda.hdm.warrantiesmg.app.service.OrderTypeService;

@Service
public class OrderTypeServiceImp implements OrderTypeService{

	@Autowired
	private OrderTypeRepository orderTypeRepository;
	
	@Override
	public List<OrderType> findAll() {
		// TODO Auto-generated method stub
		return this.orderTypeRepository.findAll();
	}

	@Override
	public OrderType findByIdOrderType(Long id) {
		// TODO Auto-generated method stub
		return this.orderTypeRepository.findByIdOrderType(id);
	}

}
