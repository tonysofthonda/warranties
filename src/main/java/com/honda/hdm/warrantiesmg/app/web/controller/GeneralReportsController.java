package com.honda.hdm.warrantiesmg.app.web.controller;

import java.io.IOException;

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
import org.springframework.web.multipart.MultipartFile;

import com.honda.hdm.warrantiesmg.app.service.GeneralReportsService;
import com.honda.hdm.warrantiesmg.app.web.model.CampaignDto;

@RestController
@RequestMapping("general/reports/")
public class GeneralReportsController {
	
	@Autowired
	private GeneralReportsService generalReportsService;

	@GetMapping("technical/summary")
	public ResponseEntity<?> technicalSummaryReport(@RequestParam (name="dateIni", required = true) String dateIni,
			@RequestParam (name="dateEnd", required = true) String dateEnd) {
		return new ResponseEntity<>(this.generalReportsService.technicalSummaryReport(dateIni, dateEnd), HttpStatus.OK);
	}
	
	@PostMapping(value = "/units/campign", consumes = {"multipart/form-data"})
	public ResponseEntity<?> saveUnitsCampignReport(@RequestParam("file") MultipartFile file) throws IOException {
		this.generalReportsService.saveUnitsCampignReport(file.getBytes());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/units/campign")
	public ResponseEntity<?> unitsCampignReport() throws IOException {
		return new ResponseEntity<>(this.generalReportsService.unitsCampignReport(), HttpStatus.OK);
	}
	
	@GetMapping("historical/amounts")
	public ResponseEntity<?> historicalAmountsReport(@RequestParam (name="dateIni", required = true) String dateIni,
			@RequestParam (name="dateEnd", required = true) String dateEnd) {
		return new ResponseEntity<>(this.generalReportsService.historicalAmountsReport(dateIni, dateEnd), HttpStatus.OK);
	}
	
	//Delete Method
	@PostMapping("delete")
	public ResponseEntity<?> deleteCampaignRecord(@RequestParam (name="id", required = true) Long id) {
		this.generalReportsService.deleteCampaignRecord(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("put")
	public ResponseEntity<?> updateCampaignRecord(@RequestBody CampaignDto campaignDto) {
		this.generalReportsService.updateCampaignRecord(campaignDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
