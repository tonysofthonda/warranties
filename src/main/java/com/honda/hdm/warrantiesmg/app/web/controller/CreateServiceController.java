package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.CreateServiceService;
import com.honda.hdm.warrantiesmg.app.web.model.CreateServiceDetailDto;
import com.honda.hdm.warrantiesmg.app.web.model.CreateServiceDto;
import com.honda.hdm.warrantiesmg.app.web.model.InfoCreateServiceDto;

@RestController
@RequestMapping(value = "create/service/")
public class CreateServiceController {
	
	@Autowired
	private CreateServiceService createService;
	
	@GetMapping
	public ResponseEntity<List<CreateServiceDto>> getAllServiceCreated(){
		List<CreateServiceDto> allService = this.createService.getAllServiceCreated();
		if(allService.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(allService ,HttpStatus.OK);
	}
	
	@GetMapping(value = "detail")
	public ResponseEntity<CreateServiceDetailDto> getAllServiceCreatedDetail(@RequestParam (name="id", required = true) Long id){
		CreateServiceDetailDto serviceCreatedDetail = this.createService.getServiceCreatedDetail(id);
		if(Objects.isNull(serviceCreatedDetail)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(serviceCreatedDetail ,HttpStatus.OK);
	}
	
	@GetMapping(value = "number")
	public ResponseEntity<String> getServiceNumber(){
		String serviceNumber = this.createService.getServiceNumber();
		if(StringUtils.isBlank(serviceNumber)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(serviceNumber ,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> saveCreateService(@RequestBody InfoCreateServiceDto infoCreateService) {
		createService.saveCreateService(infoCreateService);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("put")
	public ResponseEntity<?> updateCreateService(@RequestBody InfoCreateServiceDto infoCreateService, @RequestParam (name="id", required = true) long id) {
		createService.updateService(infoCreateService, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Method
	@PostMapping("delete")
	public ResponseEntity<?> deleteService(@RequestParam (name="id", required = true) Long id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("history")
	public ResponseEntity<?> historyService(@RequestParam(name = "vin",required = true) String vin) {
		return new ResponseEntity<>(createService.getHistoryService(vin), HttpStatus.OK);
	}
}
