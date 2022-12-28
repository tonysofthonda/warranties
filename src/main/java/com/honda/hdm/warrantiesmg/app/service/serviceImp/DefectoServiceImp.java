package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Defect;
import com.honda.hdm.warrantiesmg.app.domain.repository.DefectoRepository;
import com.honda.hdm.warrantiesmg.app.service.DefectoService;
import com.honda.hdm.warrantiesmg.app.web.model.DefectosDto;

@Service
public class DefectoServiceImp implements DefectoService{

	@Autowired
	private DefectoRepository defectoRepository;
	
	@Override
	public List<DefectosDto> getAllDedefectos(){
		List<DefectosDto> returnValues = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<Defect> defectos = this.defectoRepository.findAllOrderById();
		for(Defect defectosENtity: defectos) {
			returnValues.add(modelMapper.map(defectosENtity, DefectosDto.class));
		}
		return returnValues;
	}
	
	@Override
	public void saveDefect(DefectosDto defectosNew) {
		Defect defectosEntity = new Defect();				
		BeanUtils.copyProperties(defectosNew, defectosEntity);
		this.defectoRepository.save(defectosEntity);
	}
	
	@Override
	public void updateDefect(DefectosDto defectosDto) {		
		Defect defectosUpdate = this.defectoRepository.findById(defectosDto.getId()).orElse(null);
		if(defectosUpdate == null) {
			throw new RuntimeException("El registro que desea actualizar, no existe en el sistema");
		}
		defectosUpdate.setDescriptionSpa(defectosDto.getDescriptionSpa());	
		defectosUpdate.setDescriptionEng(defectosDto.getDescriptionEng());
		defectosUpdate.setStatus(defectosDto.getStatus());
		defectoRepository.save(defectosUpdate);
	}

	@Override
	public void updateStatusDefect(DefectosDto defectosDto) {
		Defect defectosUpdate = this.defectoRepository.findById(defectosDto.getId()).orElse(null);
		if(defectosUpdate == null) {
			throw new RuntimeException("El registro que desea actualizar, no existe en el sistema");
		}
		defectosUpdate.setStatus(defectosDto.getStatus());
		defectoRepository.save(defectosUpdate);		
	}
	
}
