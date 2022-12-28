package com.honda.hdm.warrantiesmg.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.VinService;

@RestController
@RequestMapping(value = "vin")
public class VinController {

	@Autowired
	VinService vinService;
	
	@GetMapping(name = "/")
	public ResponseEntity<?> getVin(@RequestParam String vin){
		return new ResponseEntity<>(this.vinService.findByVin(vin), HttpStatus.OK);
	}
	
}
