package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.domain.entity.Services;

public interface ServicesService {

	List<Services> findAll();
	
	Services newService(String dealerNumber, String dealerName, String createBy);
	
	Services getServicesByOrderNumber(String order);
	
	void deleteService(Services service);
	
	void saveService(Services service);
	
	List<Services> getServicesConsult(String search, String creationDate);
	
}
