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
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.DefectoService;
import com.honda.hdm.warrantiesmg.app.web.model.DefectosDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("defects")
public class DefectoController {
	
	@Autowired
	private DefectoService defectoService;
	
	@ApiOperation(value = "Listar Defectos", notes = "Devuelve una lista de todos los registros.")
	@GetMapping("/")
	public ResponseEntity<List<DefectosDto>> getAllDefectos(){
		
		List<DefectosDto> defectosFound = defectoService.getAllDedefectos();
		if(defectosFound.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<>(defectosFound, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Guardar Defecto", notes = "Guarda un nuevo defecto")
	@PostMapping("/")
	public ResponseEntity<?> saveDefect(@RequestBody DefectosDto defectos) {
		try {
			defectoService.saveDefect(defectos);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getStackTrace().toString(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	//Put Metod
	@ApiOperation(value = "Actuaiza Defecto", notes = "Actualiza un Defecto por id.")
	@PostMapping("/put")
	public ResponseEntity<?> updateDefect(@RequestBody DefectosDto defectosDto){
		try {
			defectoService.updateDefect(defectosDto);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	//Pathch Metod
	@ApiOperation(value = "Actuaiza Defecto", notes = "Actualiza un Defecto por id.")
	@PostMapping("/patch")
	public ResponseEntity<?> updateStatusDefect(@RequestBody DefectosDto defectosDto){
		try {
			defectoService.updateStatusDefect(defectosDto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
