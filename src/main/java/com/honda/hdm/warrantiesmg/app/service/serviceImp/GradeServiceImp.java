package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.GradeType;
import com.honda.hdm.warrantiesmg.app.domain.repository.GradeTypeRepository;
import com.honda.hdm.warrantiesmg.app.service.GradeTypeService;
import com.honda.hdm.warrantiesmg.app.web.model.GradeTypeDto;

@Service
public class GradeServiceImp  implements GradeTypeService{
	
	@Autowired
	private GradeTypeRepository gradeTypeRepository;

	@Override
	public List<GradeTypeDto> getAllGradeType(Boolean status) {
		List<GradeType> gradeList = this.gradeTypeRepository.findByStatus(status);
		List<GradeTypeDto> gradeDto = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();		
		for(GradeType grade: gradeList) {
			gradeDto.add(mapper.map(grade, GradeTypeDto.class));
		}
		return gradeDto;
	}

	@Override
	public void saveGradeType(GradeTypeDto gradeTypeDto) throws Exception {
		this.validators(gradeTypeDto);
		if (gradeTypeDto.getId() != null) {
			Optional<GradeType> gradeType = this.gradeTypeRepository.findById(gradeTypeDto.getId());
			if (gradeType.isPresent()) {
				gradeType.get().setGrade(gradeTypeDto.getGrade());
				gradeType.get().setDescription(gradeTypeDto.getDescription());
				gradeType.get().setStatus(gradeTypeDto.getStatus());
				this.gradeTypeRepository.save(gradeType.get());
			}else {
				throw new Exception("El tipo de problema no existe.");
			}
		}else {
			GradeType gradeType = new GradeType();
			gradeType.setGrade(gradeTypeDto.getGrade());
			gradeType.setDescription(gradeTypeDto.getDescription());
			gradeType.setStatus(gradeTypeDto.getStatus());
			this.gradeTypeRepository.save(gradeType);
		}
	}

	@Override
	public void updateStatusGradeType(GradeTypeDto gradeTypeDto) throws Exception {
		if (gradeTypeDto.getStatus() != null) {
			Optional<GradeType> gradeType = this.gradeTypeRepository.findById(gradeTypeDto.getId());
			if (gradeType.isPresent()) {
				gradeType.get().setStatus(gradeTypeDto.getStatus());
				this.gradeTypeRepository.save(gradeType.get());
			}
		}else {
			throw new Exception("El campo estatus no puede ser nulo o vacío");
		}
	}
	
	private void validators(GradeTypeDto gradeTypeDto) throws Exception {
		
		if (gradeTypeDto.getGrade() == null || gradeTypeDto.getGrade().isEmpty()) {
			throw new Exception("El campo grado no puede ser nulo o vacío");
		}
		
		if (gradeTypeDto.getDescription() == null || gradeTypeDto.getDescription().isEmpty()) {
			throw new Exception("El campo descripción no puede ser nulo o vacío");
		}
		
		if (gradeTypeDto.getStatus() == null) {
			throw new Exception("El campo estatus no puede ser nulo o vacío");
		}
	}
}
