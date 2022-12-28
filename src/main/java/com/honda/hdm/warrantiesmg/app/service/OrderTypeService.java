package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.domain.entity.OrderType;

public interface OrderTypeService {

	List<OrderType> findAll();
	
	OrderType findByIdOrderType(Long id);
	
}
