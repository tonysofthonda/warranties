package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.PlantService;
import com.honda.hdm.warrantiesmg.app.web.model.PlantDto;

@RestController
@RequestMapping(value = "plants")
public class PlantController {
	
	@Autowired
	private PlantService plantService;
	
	@GetMapping("/")
	public ResponseEntity<List<PlantDto>> getAllPlants(){
		List<PlantDto> plantList = this.plantService.getAllPlants();
		if(plantList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(plantList ,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> savePlant(@RequestBody PlantDto plant){
		this.plantService.savePlant(plant);
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
