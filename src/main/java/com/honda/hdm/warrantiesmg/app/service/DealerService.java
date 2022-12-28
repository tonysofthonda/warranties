package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

/**
 * Servicio CRUD DEALERS. 
 * @author ${VJC80578}
 * @since ${12/nov/2021}
 */

import com.honda.hdm.warrantiesmg.app.web.model.DealersDto;

public interface DealerService {
	
	List<DealersDto> getAllDealers();
	
	DealersDto getByDealerNumber(String dealerNumber);
	
	void saveDealer(DealersDto dealerDto);

	void updateStatus(DealersDto dealersDto);

}
