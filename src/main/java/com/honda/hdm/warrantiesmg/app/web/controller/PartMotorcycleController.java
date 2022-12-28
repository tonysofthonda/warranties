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

import com.honda.hdm.warrantiesmg.app.service.PartMotorcycleService;
import com.honda.hdm.warrantiesmg.app.web.model.PartMotorcycleDto;

@RestController
@RequestMapping(value = "parts-motorcycle")
public class PartMotorcycleController {

	@Autowired
	private PartMotorcycleService partMotorcycleService;

	@GetMapping("/")
	public ResponseEntity<?> getAllPartMotorcycle() {
		List<PartMotorcycleDto> partDtoList = this.partMotorcycleService.getAllPartsMotorcycle(null);
		if (partDtoList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(partDtoList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/by-status")
	public ResponseEntity<?> getPartMotorcycleByStatus(@RequestParam (name= "status", required = false) Boolean status) {
		List<PartMotorcycleDto> partDtoList = this.partMotorcycleService.getAllPartsMotorcycle(status);
		if (partDtoList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(partDtoList, HttpStatus.OK);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<?> savePartMotorcycle(@RequestBody PartMotorcycleDto partMotorcycleDto) throws Exception{
		this.partMotorcycleService.savePartMotorcycle(partMotorcycleDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("/put")
	public ResponseEntity<?> updatePartMotorcycle(@RequestBody PartMotorcycleDto partMotorcycleDto) throws Exception {
		if (partMotorcycleDto.getId() != null) {
			this.partMotorcycleService.savePartMotorcycle(partMotorcycleDto);		
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}		
	}
	
	//Pathch Metod
	@PostMapping("/patch")
	public ResponseEntity<?> updateStatusPartMotorcycle(@RequestBody PartMotorcycleDto partMotorcycleDto) throws Exception {
		if (partMotorcycleDto.getId() != null) {
			this.partMotorcycleService.savePartMotorcycle(partMotorcycleDto);		
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}		
	
	}
	
}
