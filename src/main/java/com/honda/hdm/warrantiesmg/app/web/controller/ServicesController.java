package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.domain.entity.Services;
import com.honda.hdm.warrantiesmg.app.service.ServicesService;

@RestController
@RequestMapping(value = "services")
public class ServicesController {
	
	@Autowired
	private ServicesService servicesService;

	@GetMapping("/")
	public ResponseEntity<Object> getAll(@RequestParam(required = false) Long id) {
		List<Services> list = this.servicesService.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("new-service/")
	public ResponseEntity<Object> newServices(@RequestParam String dealerNumber, @RequestParam String dealerName,
			@RequestParam String createBy) {
		Services services = this.servicesService.newService(dealerNumber, dealerName, createBy);
		return new ResponseEntity<>(services, HttpStatus.OK);
	}
	
	@GetMapping("by-order-number/")
	public ResponseEntity<Object> getServicesByOrderNumber(@RequestParam(required = false) String order){
		Services services = this.servicesService.getServicesByOrderNumber(order);
		return new ResponseEntity<>(services, HttpStatus.OK);
	}
	
	@PostMapping("delete/")
	public ResponseEntity<Object> deleteServices(@RequestBody(required = false) Services service){
		this.servicesService.deleteService(service);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("save/")
	public ResponseEntity<Object> saveServices(@RequestBody(required = false) Services service){
		this.servicesService.saveService(service);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("consult/")
	public ResponseEntity<?> getServicesConsult(@RequestParam(name = "search") String search,
			@RequestParam(name = "creationDate", required = true) String creationDate) {
		return new ResponseEntity<>(this.servicesService.getServicesConsult(search, creationDate), HttpStatus.OK);
	}
	
}
