package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.LocationService;
import com.honda.hdm.warrantiesmg.app.web.model.LocationDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("locations/")
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	@ApiOperation(value = "Obtiene la lista completa de Municipios", notes = "Enlista todos los municipios.")
	@GetMapping
	public ResponseEntity<List<LocationDto>> getAllLocations(){
		List<LocationDto> listLocations = locationService.getAllLocations();
		return new ResponseEntity<>(listLocations ,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obiene Municipios por Estado", notes = "Enlista Municipios por Estado seleccionado.")
	@GetMapping("{id}")
	public ResponseEntity<List<LocationDto>> getLocationByState(@PathVariable Long id){
		List<LocationDto> listLocations = locationService.getlocationByIdState(id);
		if(listLocations.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
 		return new ResponseEntity<>( listLocations, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtiene Municipio seleccionado", notes = "Muesta un solo Municipio")
	@GetMapping("/selectItem/{id}")
	public ResponseEntity<LocationDto> getByItemSelect(@PathVariable Long id){
		LocationDto locationDto = locationService.getByIdSelect(id);
		if(locationDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(locationDto, HttpStatus.OK);
	}

}
