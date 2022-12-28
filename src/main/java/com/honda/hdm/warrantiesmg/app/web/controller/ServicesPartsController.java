package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.domain.entity.ServicesParts;
import com.honda.hdm.warrantiesmg.app.service.ServicesPartsService;

@RestController
@RequestMapping(value = "services-parts")
public class ServicesPartsController {
	
	@Autowired
	ServicesPartsService partService;
	
	@GetMapping("/")
	public ResponseEntity<Object> getAll(@RequestParam(required = false) Long id) {
		List<ServicesParts> list = this.partService.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
