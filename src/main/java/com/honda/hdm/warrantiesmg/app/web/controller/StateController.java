package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.StateService;
import com.honda.hdm.warrantiesmg.app.web.model.StateDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("state")
public class StateController {
	
	@Autowired
	private StateService stateService;
	
	@ApiOperation(value = "Obtiene la lista completa de Estados", notes = "Enlista todos los Estados.")
	@GetMapping("/")
	public ResponseEntity<List<StateDto>> getAllState(){
		List<StateDto> listState = this.stateService.getAllState();
		return new ResponseEntity<>(listState, HttpStatus.OK);
	}
}
