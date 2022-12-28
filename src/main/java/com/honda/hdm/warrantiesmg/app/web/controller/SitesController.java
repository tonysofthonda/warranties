package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.SitesService;
import com.honda.hdm.warrantiesmg.app.web.model.SitesDto;

@RestController
@RequestMapping(value = "site/")
public class SitesController {
	
	@Autowired
	private SitesService sitesService;
	
	@GetMapping("state")
	public ResponseEntity<List<SitesDto>> getAllState(){
		List<SitesDto> sitesDto = this.sitesService.getAllState();
		if(sitesDto.isEmpty()) {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<>(sitesDto, HttpStatus.OK);
	}
	
	@GetMapping("township")
	public ResponseEntity<List<SitesDto>> getAllTownship(){
		List<SitesDto> sitesDto = this.sitesService.getAllTownship();
		if(sitesDto.isEmpty()) {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<>(sitesDto, HttpStatus.OK);
	}
	
	@GetMapping("area")
	public ResponseEntity<List<SitesDto>> getAllArea(){
		List<SitesDto> sitesDto = this.sitesService.getAllArea();
		if(sitesDto.isEmpty()) {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<>(sitesDto, HttpStatus.OK);
	}
	
	@GetMapping("group")
	public ResponseEntity<List<SitesDto>> getAllGroup(){
		List<SitesDto> sitesDto = this.sitesService.getAllGroup();
		if(sitesDto.isEmpty()) {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<>(sitesDto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<List<SitesDto>> save(@RequestBody SitesDto sitesDto) throws Exception{
		this.sitesService.saveSites(sitesDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("put")
	public ResponseEntity<List<SitesDto>> update(@RequestBody SitesDto sitesDto) throws Exception{
		this.sitesService.updateSite(sitesDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
