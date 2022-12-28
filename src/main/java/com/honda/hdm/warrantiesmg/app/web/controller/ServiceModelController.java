package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.ServiceModelService;
import com.honda.hdm.warrantiesmg.app.web.model.ServiceModelDto;

@RestController
@RequestMapping(value = "service-model")
public class ServiceModelController {
	
	@Autowired
	private ServiceModelService serviceModelService;
	
	@GetMapping
	public ResponseEntity<?> getWarrantyClaimConsecutive(@RequestParam(name = "model",required = true) String model){
		List<ServiceModelDto> serviceModelDto = this.serviceModelService.findByModel(model);
		return new ResponseEntity<>(serviceModelDto, HttpStatus.OK);
	}

}
