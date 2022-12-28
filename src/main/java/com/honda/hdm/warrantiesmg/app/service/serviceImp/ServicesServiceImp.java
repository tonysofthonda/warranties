package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Services;
import com.honda.hdm.warrantiesmg.app.domain.repository.ServicesRepository;
import com.honda.hdm.warrantiesmg.app.service.ServicesService;
import com.honda.hdm.warrantiesmg.app.web.enums.StatusEnum;
import com.honda.hdm.warrantiesmg.util.DateUtils;

@Service
public class ServicesServiceImp implements ServicesService{

	@Autowired
	private ServicesRepository servicesRepository;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Override
	public List<Services> findAll() {
		// TODO Auto-generated method stub
		return this.servicesRepository.findAll();
	}

	@Override
	public Services newService(String dealerNumber, String dealerName, String createBy) {
		// TODO Auto-generated method stub
		Services service = new Services();
		service.setStatus(StatusEnum.DRAFT.getStatus());
		service.setOrderNumber(lastService());
		service.setDateRegister(new Timestamp(new Date().getTime()));
		service.setDateCreation(new Timestamp(new Date().getTime()));
		service.setBstate(1);
		service.setDealerName(dealerName);
		service.setDealerNum(dealerNumber);
		service.setReportedBy(createBy);
		this.servicesRepository.save(service);
		return service;
	}
	
	public String lastService() {
		String lastService = "";
		Services service = this.servicesRepository.findTopByOrderByIdServiceDesc();
		if(service != null) {
			int newServiceNumber = Integer.parseInt(service.getOrderNumber());
			newServiceNumber = newServiceNumber + 1; 
			lastService = Integer.toString(newServiceNumber);
		} else {
			lastService = "1";
		}
		return lastService;
	}

	@Override
	public void deleteService(Services service) {
		// TODO Auto-generated method stub
		Optional<Services> tempService = this.servicesRepository.findById(service.getIdService());
		if(tempService.isPresent()) {
			tempService.get().setBstate(0);
			tempService.get().setStatus(StatusEnum.ELIMINADO.getStatus());
			this.servicesRepository.save(tempService.get());
		}
	}

	@Override
	public Services getServicesByOrderNumber(String order) {
		// TODO Auto-generated method stub
		return this.servicesRepository.findByOrderNumber(order);
	}

	@Override
	public void saveService(Services service) {
		// TODO Auto-generated method stub
		service.setStatus(StatusEnum.OPEN.getStatus());
		this.servicesRepository.save(service);
	}

	@Override
	public List<Services> getServicesConsult(String search, String creationDate) {
		// TODO Auto-generated method stub
		List<Services> services = this.servicesRepository.findAllByFilters(search,
				this.dateUtils.dateToTimeStamp("yyyy/MM/dd", creationDate));
		return services;
	}

}
