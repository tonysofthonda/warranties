package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.ZoneService;
import com.honda.hdm.warrantiesmg.app.web.model.ZoneDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("zone/")
public class ZoneController {
	
	@Autowired
	private ZoneService zoneService;
	
	@ApiOperation(value = "Obtiene la lista completa de Zonas", notes = "Enlista todas las Zonas")
	@GetMapping()
	public ResponseEntity<List<ZoneDto>> getAllZone(){
		List<ZoneDto> listZone = zoneService.getAllZone();
		return new ResponseEntity<>(listZone, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtiene Zona por id", notes = "Muestra Zona por id.")
	@GetMapping("{id}")
	public ResponseEntity<ZoneDto> getByIdZone(@PathVariable Long id){
		ZoneDto zoneFound = zoneService.getById(id);
		if(zoneFound == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(zoneFound, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Guarda Zona", notes = "Almacena un nuevo registro de Zona.")
	@PostMapping
	public ResponseEntity<?> saveZone(@RequestBody ZoneDto dto){
		if(dto.getZoneName().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		zoneService.saveZone(dto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
