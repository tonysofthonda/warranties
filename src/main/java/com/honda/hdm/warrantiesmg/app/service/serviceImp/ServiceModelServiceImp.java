package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.ServiceModel;
import com.honda.hdm.warrantiesmg.app.domain.repository.ServiceModelRepository;
import com.honda.hdm.warrantiesmg.app.service.ServiceModelService;
import com.honda.hdm.warrantiesmg.app.web.model.ServiceModelDto;

@Service
public class ServiceModelServiceImp implements ServiceModelService{

	@Autowired
	ServiceModelRepository serviceModelRepository;
	
	@Override
	public List<ServiceModelDto> findByModel(String model) {
		// TODO Auto-generated method stub
		List<ServiceModelDto> serviceModelDto = new ArrayList<ServiceModelDto>();
		try {
			List<Optional<ServiceModel>> serviceModel = this.serviceModelRepository.findByModel(model);
			if(!serviceModel.isEmpty()) {
				
				for (Optional<ServiceModel> serviceModelTemp : serviceModel) {
					ServiceModelDto serviceModelDtoTemp = new ServiceModelDto();
					serviceModelDtoTemp.setId(serviceModelTemp.get().getId());
					serviceModelDtoTemp.setKm(serviceModelTemp.get().getKm());
					serviceModelDtoTemp.setModel(serviceModelTemp.get().getModel());
					serviceModelDtoTemp.setModelYear(serviceModelTemp.get().getModelYear());
					serviceModelDtoTemp.setServiceNumber(serviceModelTemp.get().getServiceNumber());
					serviceModelDtoTemp.setStatus(serviceModelTemp.get().getStatus());
					serviceModelDto.add(serviceModelDtoTemp);
				}				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return serviceModelDto;
	}

}
