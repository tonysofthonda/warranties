package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.PartMotorcycle;
import com.honda.hdm.warrantiesmg.app.domain.repository.PartMotorcycleRepository;
import com.honda.hdm.warrantiesmg.app.service.PartMotorcycleService;
import com.honda.hdm.warrantiesmg.app.web.model.PartMotorcycleDto;

@Service
public class PartMotorcycleServiceImpl implements PartMotorcycleService{
	
	@Autowired
	private PartMotorcycleRepository partMotorcycleRepository;
	
	@Override
	public List<PartMotorcycleDto> getAllPartsMotorcycle(Boolean status) {
		List<PartMotorcycle> partMotorcycleList = new ArrayList<>();
		if (status == null) {
			partMotorcycleList = this.partMotorcycleRepository.findAllOrderById();	
		}else {
			partMotorcycleList = this.partMotorcycleRepository.findAllByStatusOrderByIdAsc(status);
		}
		List<PartMotorcycleDto> partMotorcycleDtoList = new ArrayList<>();
		if (!partMotorcycleList.isEmpty()) {
			for (PartMotorcycle partM : partMotorcycleList) {
				PartMotorcycleDto partDto = new PartMotorcycleDto();
				partDto.setId(partM.getId());
				partDto.setPartNameEnglish(partM.getDescriptionEnglish());
				partDto.setPartNameSpanish(partM.getDescriptionSpanish());
				partDto.setStatus(partM.getStatus());
				partMotorcycleDtoList.add(partDto);
			}
			return partMotorcycleDtoList;
		}else {
			return partMotorcycleDtoList;
		}
	}

	@Override
	public void savePartMotorcycle(PartMotorcycleDto partMotorcycleDto) throws Exception {
		this.validators(partMotorcycleDto);		
		if (partMotorcycleDto.getId() != null) {
			Optional<PartMotorcycle> partMotorcycle = this.partMotorcycleRepository.findById(partMotorcycleDto.getId());
			if(partMotorcycle.isPresent()) {
				partMotorcycle.get().setDescriptionEnglish(partMotorcycleDto.getPartNameEnglish());
				partMotorcycle.get().setDescriptionSpanish(partMotorcycleDto.getPartNameSpanish());
				partMotorcycle.get().setStatus(partMotorcycleDto.getStatus());
				this.partMotorcycleRepository.save(partMotorcycle.get());
			}else {
				throw new Exception("La parte que desea actualizar no existe");
			}			
		}else {
			PartMotorcycle partMotorcycle = new PartMotorcycle();
			partMotorcycle.setDescriptionEnglish(partMotorcycleDto.getPartNameEnglish());
			partMotorcycle.setDescriptionSpanish(partMotorcycleDto.getPartNameSpanish());
			partMotorcycle.setStatus(partMotorcycleDto.getStatus());
			this.partMotorcycleRepository.save(partMotorcycle);
		}
	}
	
	private void validators(PartMotorcycleDto partMotorcycleDto) throws Exception {
		if (partMotorcycleDto.getPartNameEnglish() == null || partMotorcycleDto.getPartNameEnglish().isEmpty()) {
			throw new Exception("El campo nombre de parte (Inglés) no puede ser nulo o vacío");
		}
		
		if (partMotorcycleDto.getPartNameSpanish() == null || partMotorcycleDto.getPartNameSpanish().isEmpty()) {
			throw new Exception("El campo nombre de parte (Español) no puede ser nulo o vacío");
		}
		
		if (partMotorcycleDto.getStatus() == null) {
			throw new Exception("El campo status no puede ser nulo o vacío");
		}
	}

	@Override
	public void updateStatusPartMotorcycle(PartMotorcycleDto partMotorcycleDto) throws Exception {
		Optional<PartMotorcycle> partMotorcycle = this.partMotorcycleRepository.findById(partMotorcycleDto.getId());
		if(partMotorcycle.isPresent()) {
			partMotorcycle.get().setStatus(partMotorcycleDto.getStatus());
			this.partMotorcycleRepository.save(partMotorcycle.get());			
		}else {
			throw new RuntimeException("El registro que desea actualizar, no existe en el sistema");
		}
	}
}
