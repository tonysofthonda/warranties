package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.ClientSaleDto;

public interface ClientSaleService {
	
	List<ClientSaleDto> getAll();
	
	ClientSaleDto getById(Long id);

}
