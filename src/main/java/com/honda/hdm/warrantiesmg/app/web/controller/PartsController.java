package com.honda.hdm.warrantiesmg.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.PartsService;
import com.honda.hdm.warrantiesmg.app.web.model.PartDto;

@RestController
@RequestMapping(value =  "parts")
public class PartsController {
	
	@Autowired
	private PartsService partsService;
		
	@GetMapping("/")
	public ResponseEntity<?> getPart(@RequestParam (name="partNumber", required = true) String partNumber) throws Exception{
		PartDto partDto = this.partsService.getPartByPartNumber(partNumber);
		if (partDto != null) {
			return new ResponseEntity<>(partDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(partDto, HttpStatus.NO_CONTENT);
		}
	} 
	
	@PostMapping("/")
	public ResponseEntity<?> savePart(@RequestBody PartDto part) throws Exception{
		try {
			this.partsService.savePart(part);		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	} 

}
