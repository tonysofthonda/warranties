package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.ProblemCategory;
import com.honda.hdm.warrantiesmg.app.domain.repository.ProblemCategoryRepository;
import com.honda.hdm.warrantiesmg.app.service.ProblemCategoryService;
import com.honda.hdm.warrantiesmg.app.web.model.ProblemCategoryDto;

@Service
public class ProblemCategoryServiceImp implements ProblemCategoryService{

	@Autowired
	private ProblemCategoryRepository problemCategoryRepository;
	
	@Override
	public List<ProblemCategoryDto> getProblemCategory(Boolean status) {
		List<ProblemCategory> problemCategoryList = this.problemCategoryRepository.findByStatus(status);
		List<ProblemCategoryDto> problemCategoryDtoList = new ArrayList<>();
		if (!problemCategoryList.isEmpty()) {
			for (ProblemCategory problemCategory : problemCategoryList) {
				ProblemCategoryDto problemDto = new ProblemCategoryDto();
				problemDto.setId(problemCategory.getId());
				problemDto.setProblemName(problemCategory.getProblemName());
				problemDto.setProblemDescription(problemCategory.getProblemDescription());
				problemDto.setStatus(problemCategory.getStatus());
				problemCategoryDtoList.add(problemDto);
			}
			return problemCategoryDtoList;
		}
		return problemCategoryDtoList;
	}


	@Override
	public void saveProblemCategory(ProblemCategoryDto problemCategoryDto) throws Exception {
		this.validators(problemCategoryDto);
		if (problemCategoryDto.getId() != null) {
			Optional<ProblemCategory> problemCategory = this.problemCategoryRepository.findById(problemCategoryDto.getId());
			if (problemCategory.isPresent()) {
				problemCategory.get().setProblemName(problemCategoryDto.getProblemName());
				problemCategory.get().setProblemDescription(problemCategoryDto.getProblemDescription());
				problemCategory.get().setStatus(problemCategoryDto.getStatus());
				this.problemCategoryRepository.save(problemCategory.get());
			}else {
				throw new Exception("El tipo de problema no existe.");
			}			
		}else {
			ProblemCategory problemCategory = new ProblemCategory();
			problemCategory.setProblemName(problemCategoryDto.getProblemName());
			problemCategory.setProblemDescription(problemCategoryDto.getProblemDescription());
			problemCategory.setStatus(problemCategoryDto.getStatus());
			this.problemCategoryRepository.save(problemCategory);
		}
	}
	
	private void validators(ProblemCategoryDto problemCategoryDto) throws Exception {
		
		if (problemCategoryDto.getProblemName() == null || problemCategoryDto.getProblemName().isEmpty()) {
			throw new Exception("El campo tipo de problema no puede ser nulo o vacío");
		}
		
		if (problemCategoryDto.getProblemDescription() == null || problemCategoryDto.getProblemDescription().isEmpty()) {
			throw new Exception("El campo descripción no puede ser nulo o vacío");
		}
		
		if (problemCategoryDto.getStatus() == null) {
			throw new Exception("El campo estatus no puede ser nulo o vacío");
		}
		
	}

	@Override
	public void changeStatusProblemCategory(ProblemCategoryDto problemCategoryDto) throws Exception {
		if (problemCategoryDto.getStatus() != null ) {
			Optional<ProblemCategory> problemCategory = this.problemCategoryRepository.findById(problemCategoryDto.getId());
			if (problemCategory.isPresent()) {				
				problemCategory.get().setStatus(problemCategoryDto.getStatus());
				this.problemCategoryRepository.save(problemCategory.get());				
			}else {
				throw new Exception("El tipo de problema no existe.");
			}			
		}else {
			throw new Exception("El campo estatus no puede ser nulo o vacío");
		}
	}

}
