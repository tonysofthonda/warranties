package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.PeriodService;
import com.honda.hdm.warrantiesmg.app.web.model.PeriodDto;

@RestController
@RequestMapping(value = "periods")
public class PeriodController {

	@Autowired
	private PeriodService periodService;
	
	@GetMapping("/")
	public ResponseEntity<List<PeriodDto>> getAllPeriods() {
		List<PeriodDto> periodList = this.periodService.getAllPeriods();
		if (periodList.isEmpty()) {
			return new ResponseEntity<>(periodList, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(periodList, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> savePeriod(@RequestBody PeriodDto dto) {
		this.validations(dto);
		try {
			this.periodService.savePeriod(dto);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Pathch Metod
	@PostMapping("/patch")
	public ResponseEntity<?> updateStatusPeriod(@RequestBody PeriodDto periodDto) throws Exception{
		if (periodDto.getId() != null) {
			this.periodService.updateStatusPeriod(periodDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");	
		}		
	}
	
	//Put Metod
	@Scheduled(cron = "${custom.period.cron}")
	@PostMapping("/put")
	public ResponseEntity<?> updatePeriodByCron(){
		return new ResponseEntity<>(HttpStatus.OK); 
	}
	
	public void validations(PeriodDto dto) {
		if(dto.getPeriodName().isEmpty() || dto.getPeriodName().length() == 0) {
			throw new RuntimeException("El campo Nombre del periodo no debe ser vacio");
		}

		if(dto.getDescription().isEmpty() || dto.getPeriodName().length() == 0) {
			throw new RuntimeException("El campo Descripcion no debe ser vacio");
		}
		
		if(dto.getDateInit() == null) {
			throw new RuntimeException("El campo Fecha Inicio no debe ser vacio");
		}
		
		if(dto.getDateEnd() == null) {
			throw new RuntimeException("El campo Fecha Fin no debe ser vacio");
		}
	}
}
