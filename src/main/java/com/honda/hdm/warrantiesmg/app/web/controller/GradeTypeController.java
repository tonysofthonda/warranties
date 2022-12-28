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

import com.honda.hdm.warrantiesmg.app.service.GradeTypeService;
import com.honda.hdm.warrantiesmg.app.web.model.GradeTypeDto;

@RestController
@RequestMapping(value = "grade-type")
public class GradeTypeController {
	
	@Autowired
	private GradeTypeService gradeTypeService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllGradeType(@RequestParam(name = "status", required = false) Boolean status) {
		List<GradeTypeDto> gradeTypeList = this.gradeTypeService.getAllGradeType(status);
		if(gradeTypeList.isEmpty()) {
			return new ResponseEntity<>(gradeTypeList, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(gradeTypeList, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveGradeType(@RequestBody GradeTypeDto gradeTypeDto) throws Exception{
		this.gradeTypeService.saveGradeType(gradeTypeDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("/put")
	public ResponseEntity<?> updateGradeType(@RequestBody GradeTypeDto gradeTypeDto) throws Exception{
		if (gradeTypeDto.getId() != null) {
			this.gradeTypeService.saveGradeType(gradeTypeDto);
			return new ResponseEntity<>(HttpStatus.OK);	
		}else {
			throw new Exception("El id no puede ser nulo o vacío");	
		}		
	}
	
	//Pathch Metod
	@PostMapping("/patch")
	public ResponseEntity<?> updateStatusGradeType(@RequestBody GradeTypeDto gradeTypeDto) throws Exception{
		if (gradeTypeDto.getId() != null) {
			this.gradeTypeService.updateStatusGradeType(gradeTypeDto);
			return new ResponseEntity<>(HttpStatus.OK);	
		}else {
			throw new Exception("El id no puede ser nulo o vacío");	
		}		
		
	}
}
