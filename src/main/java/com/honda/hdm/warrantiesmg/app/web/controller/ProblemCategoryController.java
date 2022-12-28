package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.ProblemCategoryService;
import com.honda.hdm.warrantiesmg.app.web.model.ProblemCategoryDto;

@RestController
@RequestMapping(value = "problem-category")
public class ProblemCategoryController {
	
	@Autowired
	private ProblemCategoryService problemCategoryService;
	
	@GetMapping("/")
	public ResponseEntity<?> getProblemCategory(@RequestParam(name = "status", required = false) Boolean status) {
		List<ProblemCategoryDto> problemCategoryDtoList = this.problemCategoryService.getProblemCategory(status);
		if (!problemCategoryDtoList.isEmpty()) {
			return new ResponseEntity<>(problemCategoryDtoList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(problemCategoryDtoList, HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveProblemCategory(@RequestBody ProblemCategoryDto problemCategoryDto) throws Exception{
		this.problemCategoryService.saveProblemCategory(problemCategoryDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("/put")
	public ResponseEntity<?> updateProblemCategory(@RequestBody ProblemCategoryDto problemCategoryDto) throws Exception{
		if (problemCategoryDto.getId() != null ) {
			this.problemCategoryService.saveProblemCategory(problemCategoryDto);
			return new ResponseEntity<>(HttpStatus.OK);	
		}else {
			throw new Exception("El id no puede ser nulo o vacío");				
		}
	}
	
	//Pathch Metod
	@PostMapping("/patch")
	public ResponseEntity<?> changeStatusProblemCategory(@RequestBody ProblemCategoryDto problemCategoryDto) throws Exception{
		if (problemCategoryDto.getId() != null) {
			this.problemCategoryService.changeStatusProblemCategory(problemCategoryDto);
			return new ResponseEntity<>(HttpStatus.OK);	
		}else {
			throw new Exception("El id no puede ser nulo o vacío");				
		}
	}
	
}
