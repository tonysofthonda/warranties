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

import com.honda.hdm.warrantiesmg.app.service.PartsDefectsSymptomService;
import com.honda.hdm.warrantiesmg.app.web.model.DefectosDto;
import com.honda.hdm.warrantiesmg.app.web.model.PartsDefectsSymptomDto;
import com.honda.hdm.warrantiesmg.app.web.model.SintomaDto;

@RestController
@RequestMapping(value = "parts-defects-symptom")
public class PartsDefectsSymptomController {
	
	@Autowired
	private PartsDefectsSymptomService partsDefectsSymptomService;
	
	@GetMapping("/getRelatedDefects")
	public ResponseEntity<?> getRelatedDefects(@RequestParam (name="partId", required = true) Long partId) {
		List<DefectosDto> defectsDtoList = this.partsDefectsSymptomService.getDefectsByPartId(partId);
		if (defectsDtoList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(defectsDtoList, HttpStatus.OK);
		}		
	}
	
	@GetMapping("/getRelatedSymptom")
	public ResponseEntity<?> getRelatedSymptom(@RequestParam (name="defectId", required = true) Long defectId, 
			@RequestParam ( name="partId", required = true) Long partId ) {
		List<SintomaDto> sintomaDtoList = this.partsDefectsSymptomService.getSymptomDtoByPartIdAndDefectId(partId,defectId);
		if (sintomaDtoList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(sintomaDtoList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllRelated(@RequestParam (name="status", required = false) Boolean status){
		List<PartsDefectsSymptomDto> partsDefectsSymptomDtoList = this.partsDefectsSymptomService.getAllRelated(status);
		if (partsDefectsSymptomDtoList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(partsDefectsSymptomDtoList, HttpStatus.OK);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveRelated(@RequestBody PartsDefectsSymptomDto partsDefectsSymptomDto) throws Exception{
		this.partsDefectsSymptomService.saveRelated(partsDefectsSymptomDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("/put")
	public ResponseEntity<?> updateRelated(@RequestBody PartsDefectsSymptomDto partsDefectsSymptomDto) throws Exception{
		if (partsDefectsSymptomDto.getId() != null) {
			this.partsDefectsSymptomService.saveRelated(partsDefectsSymptomDto);	
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}
	
	//Pathch Metod
	@PostMapping("/patch")
	public ResponseEntity<?> updateStatusRelated(@RequestBody PartsDefectsSymptomDto partsDefectsSymptomDto) throws Exception{
		if (partsDefectsSymptomDto.getId() != null) {
			this.partsDefectsSymptomService.updateStatusRelated(partsDefectsSymptomDto);			
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}		
	}
}
