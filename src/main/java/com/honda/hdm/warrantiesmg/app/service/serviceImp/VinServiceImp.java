package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Vin;
import com.honda.hdm.warrantiesmg.app.domain.repository.VinRepository;
import com.honda.hdm.warrantiesmg.app.service.VinService;
import com.honda.hdm.warrantiesmg.app.web.model.ModelDto;
import com.honda.hdm.warrantiesmg.app.web.model.PlantDto;
import com.honda.hdm.warrantiesmg.app.web.model.VinDto;
import com.honda.hdm.warrantiesmg.configuration.WmqBusinessLogicException;

@Service
public class VinServiceImp implements VinService {

	@Autowired
	VinRepository vinRepository;
	
	@Override
	public VinDto findByVin(String vin) {
		// TODO Auto-generated method stub
		Optional<Vin> vinEntity = vinRepository.findByVin(vin);
		VinDto vinDto = new VinDto();
		ModelDto modelDto = new ModelDto();
		PlantDto plantDto = new PlantDto();
		if(!vinEntity.isPresent()) { 
			throw new WmqBusinessLogicException("El vin no existe"); 
		} else {
			
			plantDto.setId(vinEntity.get().getModel().getPlant().getId());
			plantDto.setName(vinEntity.get().getModel().getPlant().getName());
			plantDto.setPlantCountry(vinEntity.get().getModel().getPlant().getPlantCountry());
			plantDto.setStatus(vinEntity.get().getModel().getPlant().getStatus());
			plantDto.setTypeClassification(vinEntity.get().getModel().getPlant().getTypeClassification());
			
			modelDto.setId(vinEntity.get().getModel().getId());
			modelDto.setCodModel(vinEntity.get().getModel().getCodModel());
			modelDto.setModel(vinEntity.get().getModel().getModel());
			modelDto.setName(vinEntity.get().getModel().getName());
			modelDto.setPlant(plantDto);
			modelDto.setSegment(vinEntity.get().getModel().getSegment());
			modelDto.setStatus(vinEntity.get().getModel().getStatus());
			modelDto.setType(vinEntity.get().getModel().getType());
			modelDto.setVds(vinEntity.get().getModel().getVds());
			modelDto.setYear(vinEntity.get().getModel().getYear());
			
			vinDto.setId(vinEntity.get().getId());
			vinDto.setColor(vinEntity.get().getColor());
			vinDto.setEngineNumber(vinEntity.get().getEngineNumber());
			vinDto.setInvoiceFolio(vinEntity.get().getInvoiceFolio());
			vinDto.setModel(modelDto);
			vinDto.setModelYear(vinEntity.get().getModelYear());
			vinDto.setSalesDate(vinEntity.get().getSalesDate());
			vinDto.setVin(vinEntity.get().getVin());
			vinDto.setWarrantyDateExpdate(vinEntity.get().getWarrantyDateExpdate());
			vinDto.setWarrantyNumberFolio(vinEntity.get().getWarrantyNumberFolio());
		}
		
		return vinDto;
	}

}
