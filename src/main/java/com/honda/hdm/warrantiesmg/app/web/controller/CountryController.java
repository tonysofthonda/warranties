package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.CountryService;
import com.honda.hdm.warrantiesmg.app.web.model.CountryDto;

@RestController
@RequestMapping(value = "country")
public class CountryController {

	@Autowired
	private CountryService countryService;
	
	@GetMapping("/")
	public ResponseEntity<List<CountryDto>> getAllCountry(){
		List<CountryDto> countryList = this.countryService.getAllCountries();
		if(countryList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(countryList ,HttpStatus.OK);
	}
}
