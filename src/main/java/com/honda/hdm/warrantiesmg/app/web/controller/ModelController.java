package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.ModelService;
import com.honda.hdm.warrantiesmg.app.web.model.ModelDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "models")
public class ModelController {
	
	@Autowired
	private ModelService modelService;
	
	@ApiOperation(value = "Obiene todos los modelos", notes = "Enlista todos lo modelos.")
	@GetMapping("/")
	public ResponseEntity<List<ModelDto>> getAllModels(){
		List<ModelDto> modelList = this.modelService.getAllModels();
		if(modelList.isEmpty()) {
			return new ResponseEntity<>(modelList, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(modelList , HttpStatus.OK);
	}
	
	@ApiOperation(value = "Guarda y actualiza", notes = "Crea un nuevo registro de modelos y actualiza un registro existente.")
	@PostMapping("/")
	public ResponseEntity<?> saveModel(@RequestBody ModelDto modelDto){
		try {
			this.modelService.saveModel(modelDto);			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	//Pathch Metod
	@ApiOperation(value = "Actuaiza Model", notes = "Actualiza un Model por id.")
	@PostMapping("/patch")
	public ResponseEntity<?> updateStatusModel(@RequestBody ModelDto modelDto){
		try {
			this.modelService.updateStatus(modelDto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Obiene un modelo por model", notes = "Busca un modelo.")
	@GetMapping("/model/")
	public ResponseEntity<ModelDto> getOneModel(@RequestParam("model") String model) {
		ModelDto modelDto = this.modelService.getOneModel(model);
		if(model == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(modelDto, HttpStatus.OK);
	}

}
