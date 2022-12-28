package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Symptom;
import com.honda.hdm.warrantiesmg.app.domain.repository.SintomaRepository;
import com.honda.hdm.warrantiesmg.app.service.SintomaServie;
import com.honda.hdm.warrantiesmg.app.web.model.SintomaDto;

@Service
public class SintomaServiceImp implements SintomaServie{
	
	@Autowired
	private SintomaRepository sintomaRepository;
	
	@Override
	public List<SintomaDto> getSintomas(){
		List<SintomaDto> dtos = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<Symptom> sintomaList = sintomaRepository.findAllOrderById();
		
		for(Symptom sintoma : sintomaList) {
			dtos.add(modelMapper.map(sintoma, SintomaDto.class));
		}
		return dtos;
	}
	
	@Override
	public SintomaDto getByIdSintoma(Long id){
		SintomaDto dto = new  SintomaDto();
		Optional<Symptom> sintomaFound = sintomaRepository.findById(id);
		if(sintomaFound.isPresent()) {
			BeanUtils.copyProperties(sintomaFound.get(), dto);
			return dto;			
		}
		throw new RuntimeException("El Sintoma no existe");
	}
	
	@Override
	public Symptom getByCodigo(String codigo) {
		Symptom foundCodigo = sintomaRepository.findByCode(codigo);
		if(foundCodigo == null) return foundCodigo;
		
		return foundCodigo;
	}
	
	@Override
	public void saveSintoma(SintomaDto sintomaDto) {
		Symptom sintoma = this.sintomaRepository.findByCode(sintomaDto.getCode());
		if(sintoma == null) {
			Symptom symptomSave = new Symptom();
			symptomSave.setCode(sintomaDto.getCode());		
			symptomSave.setDescriptionSpa(sintomaDto.getDescriptionSpa());
			symptomSave.setDescriptionEng(sintomaDto.getDescriptionEng());
			symptomSave.setStatus(sintomaDto.getStatus());		
			this.sintomaRepository.save(symptomSave);
		}
	}
	
	@Override
	public void updateSintoma(SintomaDto sintomaDto) {
		Optional<Symptom> sintomaUpdate = this.sintomaRepository.findById(sintomaDto.getId());
		if(sintomaUpdate.isPresent()) {
			sintomaUpdate.get().setCode(sintomaDto.getCode());
			sintomaUpdate.get().setDescriptionSpa(sintomaDto.getDescriptionSpa());
			sintomaUpdate.get().setDescriptionEng(sintomaDto.getDescriptionEng());
			sintomaUpdate.get().setStatus(sintomaDto.getStatus());	
			sintomaRepository.save(sintomaUpdate.get());	
		}else {
			throw new RuntimeException("El registro que desea actualizar, no existe en el sistema");
		}
	}

	@Override
	public void updateStatusSintoma(SintomaDto sintomaDto) {
		Optional<Symptom> sintomaFound = this.sintomaRepository.findById(sintomaDto.getId());
		if(sintomaFound.isPresent()) {
			sintomaFound.get().setStatus(sintomaDto.getStatus());
			this.sintomaRepository.save(sintomaFound.get());
		}else {
			throw new RuntimeException("El registro que desea actualizar, no existe en el sistema");
		}
	}

}
