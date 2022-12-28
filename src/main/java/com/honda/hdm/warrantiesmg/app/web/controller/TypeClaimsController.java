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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.TypeClaimsService;
import com.honda.hdm.warrantiesmg.app.web.model.TypeClaimsDto;

@RestController
@RequestMapping(value = "type-claims")
public class TypeClaimsController {
	
	@Autowired
	private TypeClaimsService typeClaimsService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAll(@RequestParam(name = "status", required = false) Boolean status) {
		List<TypeClaimsDto> typeClaimsDtoList = this.typeClaimsService.getAllTypeClaims(status);
		if(typeClaimsDtoList.isEmpty()) {
			return new ResponseEntity<>(typeClaimsDtoList, HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<>(typeClaimsDtoList, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveTypeClaim(@RequestBody TypeClaimsDto typeClaimsDto) throws Exception{
		this.typeClaimsService.saveTypeClaim(typeClaimsDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("/put")
	public ResponseEntity<?> updateTypeClaim(@RequestBody TypeClaimsDto typeClaimsDto) throws Exception{
		if (typeClaimsDto.getId() != null) {
			this.typeClaimsService.saveTypeClaim(typeClaimsDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}

	//Pathch Metod
	@PostMapping("/patch")
	public ResponseEntity<?> updateStatusTypeClaim(@RequestBody TypeClaimsDto typeClaimsDto) throws Exception{
		if (typeClaimsDto.getId() != null) {
			this.typeClaimsService.updateStatusTypeClaim(typeClaimsDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}
	
}
