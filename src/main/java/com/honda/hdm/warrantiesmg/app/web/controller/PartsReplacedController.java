package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.PartsReplacedService;
import com.honda.hdm.warrantiesmg.app.web.model.PartsReplacedDto;

@RestController
@RequestMapping(value =  "parts-replaced")
public class PartsReplacedController {
	
	@Autowired
	private PartsReplacedService partsReplacedService;
	
	@GetMapping("/")
	public ResponseEntity<?> getPartsReplaced(@RequestParam (name="warrantyId", required = true) Long warrantyId) {
		List<PartsReplacedDto> partsReplacedDtoList = this.partsReplacedService.getAllPartsReplaced(warrantyId);
		if (partsReplacedDtoList.isEmpty()) {
			return new ResponseEntity<>(partsReplacedDtoList, HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(partsReplacedDtoList, HttpStatus.OK);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<?> savePartsReplaced(@RequestBody PartsReplacedDto partsReplacedDto) throws Exception {
		this.partsReplacedService.savePartsReplaced(partsReplacedDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("/put")
	public ResponseEntity<?> updatePartsReplaced(@RequestBody PartsReplacedDto partsReplacedDto) throws Exception {
		if (partsReplacedDto.getId() != null) {
			this.partsReplacedService.savePartsReplaced(partsReplacedDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}
	
	//Delete Metod
	@PostMapping("/delete")
	public ResponseEntity<?> deletePartsReplaced(@RequestParam (name="partsReplacedId", required = true) Long partsReplacedId) throws Exception {
		if (partsReplacedId != null) {
			this.partsReplacedService.deletePartsReplaced(partsReplacedId);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}
	
}
