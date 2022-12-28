package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Defect;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartMotorcycle;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartsDefectsSymptom;
import com.honda.hdm.warrantiesmg.app.domain.entity.Symptom;
import com.honda.hdm.warrantiesmg.app.domain.repository.PartsDefectsSymptomRepository;
import com.honda.hdm.warrantiesmg.app.service.PartsDefectsSymptomService;
import com.honda.hdm.warrantiesmg.app.web.model.DefectosDto;
import com.honda.hdm.warrantiesmg.app.web.model.PartMotorcycleDto;
import com.honda.hdm.warrantiesmg.app.web.model.PartsDefectsSymptomDto;
import com.honda.hdm.warrantiesmg.app.web.model.SintomaDto;

@Service
public class PartsDefectsSymptomServiceImp implements PartsDefectsSymptomService {

	@Autowired
	private PartsDefectsSymptomRepository partsDefectsSymptomRepository;

	@Override
	public List<DefectosDto> getDefectsByPartId(Long partId) {
		List<Defect> partsDefectsSymptomList = this.partsDefectsSymptomRepository.findAllByPartMotorcycleId(partId);
		List<DefectosDto> defectsDtoList = new ArrayList<>();
		if (partsDefectsSymptomList != null) {
			for (Defect pds : partsDefectsSymptomList) {
				DefectosDto defectosDto = new DefectosDto();
				defectosDto.setId(pds.getId());
				defectosDto.setDescriptionSpa(pds.getDescriptionSpa());
				defectsDtoList.add(defectosDto);
			}
		}
		defectsDtoList = defectsDtoList.stream().distinct().collect(Collectors.toList());
		return defectsDtoList;		
	}
	
	@Override
	public List<SintomaDto> getSymptomDtoByPartIdAndDefectId(Long partId, Long defectId) {
		List<Symptom> symptomList = this.partsDefectsSymptomRepository.findAllByPartIdAndDefectId(partId, defectId);
		List<SintomaDto> sintomaDtoList = new ArrayList<>();
		if (symptomList != null) {			
			for (Symptom pds : symptomList) {
				SintomaDto sintomaDto = new SintomaDto();
				sintomaDto.setId(pds.getId());
				sintomaDto.setCode(pds.getCode());
				sintomaDto.setDescriptionSpa(pds.getDescriptionSpa());
				sintomaDtoList.add(sintomaDto);
			}
		}
		return sintomaDtoList;
	}
	

	@Override
	public List<PartsDefectsSymptomDto> getAllRelated(Boolean status) {
		List<PartsDefectsSymptom> partsDefectsSymptom =this.partsDefectsSymptomRepository.findAllByStatus(status);
		List<PartsDefectsSymptomDto> partsDefectsSymptomDtoList = new ArrayList<>(); 
		if(!partsDefectsSymptom.isEmpty()) {
			for (PartsDefectsSymptom partsDefectsSymptom2 : partsDefectsSymptom) {
				PartsDefectsSymptomDto partsDefectsSymptomDto = new PartsDefectsSymptomDto();
				
				partsDefectsSymptomDto.setId(partsDefectsSymptom2.getId());
				
				PartMotorcycleDto partMotorcycleDto = new PartMotorcycleDto();
				partMotorcycleDto.setId(partsDefectsSymptom2.getPartMotorcycle().getId());
				partMotorcycleDto.setPartNameEnglish(partsDefectsSymptom2.getPartMotorcycle().getDescriptionEnglish());
				partMotorcycleDto.setPartNameSpanish(partsDefectsSymptom2.getPartMotorcycle().getDescriptionSpanish());
				partMotorcycleDto.setStatus(partsDefectsSymptom2.getPartMotorcycle().getStatus());
				partsDefectsSymptomDto.setPartMotorcycle(partMotorcycleDto);
				
				
				DefectosDto defectosDto = new DefectosDto();
				defectosDto.setId(partsDefectsSymptom2.getDefect().getId());				
				defectosDto.setDescriptionEng(partsDefectsSymptom2.getDefect().getDescriptionEng());
				defectosDto.setDescriptionSpa(partsDefectsSymptom2.getDefect().getDescriptionSpa());
				defectosDto.setStatus(partsDefectsSymptom2.getDefect().getStatus());
				partsDefectsSymptomDto.setDefects(defectosDto);
				
				SintomaDto sintomaDto = new SintomaDto();
				sintomaDto.setId(partsDefectsSymptom2.getSymptom().getId());
				sintomaDto.setCode(partsDefectsSymptom2.getSymptom().getCode());
				sintomaDto.setDescriptionEng(partsDefectsSymptom2.getSymptom().getDescriptionEng());
				sintomaDto.setDescriptionSpa(partsDefectsSymptom2.getSymptom().getDescriptionSpa());
				sintomaDto.setStatus(partsDefectsSymptom2.getSymptom().getStatus());
				partsDefectsSymptomDto.setSymptom(sintomaDto);
				
				partsDefectsSymptomDto.setStatus(partsDefectsSymptom2.getStatus());
				
				partsDefectsSymptomDtoList.add(partsDefectsSymptomDto);
			}			
		}		
		return partsDefectsSymptomDtoList;
	}

	@Override
	public void saveRelated(PartsDefectsSymptomDto partsDefectsSymptomDto) throws Exception {
		this.validations(partsDefectsSymptomDto);
		PartsDefectsSymptom partsDefectsSymptom =this.partsDefectsSymptomRepository.findByPartDefectAndSimptom(partsDefectsSymptomDto.getPartMotorcycle().getId(),partsDefectsSymptomDto.getDefects().getId(), partsDefectsSymptomDto.getSymptom().getId());		
		if (partsDefectsSymptom == null) {
			
			PartsDefectsSymptom pds = new PartsDefectsSymptom();
			if (partsDefectsSymptomDto.getId() != null) {
				pds.setId(partsDefectsSymptomDto.getId());
			}
				
			PartMotorcycle partMotorcycle = new PartMotorcycle(); 
			partMotorcycle.setId(partsDefectsSymptomDto.getPartMotorcycle().getId());				
			pds.setPartMotorcycle(partMotorcycle);
				
			Defect defect= new Defect();
			defect.setId(partsDefectsSymptomDto.getDefects().getId());
			pds.setDefect(defect);
				
			Symptom symptom = new Symptom(); 
			symptom.setId(partsDefectsSymptomDto.getSymptom().getId());
			pds.setSymptom(symptom);
			
			pds.setStatus(partsDefectsSymptomDto.getStatus());
			
			this.partsDefectsSymptomRepository.save(pds);
		}else if (partsDefectsSymptom.getId().equals(partsDefectsSymptomDto.getId())) {
			partsDefectsSymptom.setStatus(partsDefectsSymptomDto.getStatus());
			this.partsDefectsSymptomRepository.save(partsDefectsSymptom);
		}else{
			throw new Exception("El relación que intenta guardar ya existe.");
		}
	}
	
	private void validations(PartsDefectsSymptomDto partsDefectsSymptomDto) throws Exception {
		if (partsDefectsSymptomDto.getPartMotorcycle() == null || partsDefectsSymptomDto.getPartMotorcycle().getId() == null) {
			throw new Exception("El parte de no puede ser nulo o vacío");			
		}
		
		if (partsDefectsSymptomDto.getDefects() == null || partsDefectsSymptomDto.getDefects().getId() == null) {
			throw new Exception("El defecto no puede ser nulo o vacío");			
		}
		
		if (partsDefectsSymptomDto.getSymptom() == null || partsDefectsSymptomDto.getSymptom().getId() == null) {
			throw new Exception("El sintoma no puede ser nulo o vacío");
		}
		
	}

	@Override
	public void updateStatusRelated(PartsDefectsSymptomDto partsDefectsSymptomDto) {
		Optional<PartsDefectsSymptom> partsDefectsSymptom = this.partsDefectsSymptomRepository.findById(partsDefectsSymptomDto.getId());
		if (partsDefectsSymptom.isPresent()) {
			partsDefectsSymptom.get().setStatus(partsDefectsSymptomDto.getStatus());
			this.partsDefectsSymptomRepository.save(partsDefectsSymptom.get());
		}
	}
}
