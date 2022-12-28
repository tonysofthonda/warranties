package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.DealerService;
import com.honda.hdm.warrantiesmg.app.web.model.DealersDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("dealers")
public class DealerController {

	@Autowired
	private DealerService dealerService;

	@ApiOperation(value = "Obtiene la lista completa de Dealer", notes = "Enlista todos los Dealer")
	@GetMapping("/")
	public ResponseEntity<List<DealersDto>> getAllDealers() {
		List<DealersDto> dealerList = this.dealerService.getAllDealers();
		return new ResponseEntity<>(dealerList, HttpStatus.OK);
	}

	@ApiOperation(value = "Guarda Dealer", notes = "Guarda un nuevo registro de Dealer")
	@PostMapping("/")
	public ResponseEntity<?> saveDealer(@RequestBody DealersDto dealersDto) {
		this.validators(dealersDto);
		this.dealerService.saveDealer(dealersDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	public void validators(DealersDto dealersDto) {

		if (dealersDto.getName().isEmpty()) {
			throw new RuntimeException("El campo Nombre es un campo requerido");
		}

		if (dealersDto.getGroup() == null) {
			throw new RuntimeException("El campo Grupo es un campo requerido");
		}

		if (dealersDto.getZone() == null) {
			throw new RuntimeException("El campo Zona es un campo requerido");
		}

		if (dealersDto.getStatus() == null) {
			throw new RuntimeException("El campo Estatus es un campo requerido");
		}
	}

 	//Patch Metod
	@ApiOperation(value = "Actuaiza Dealer", notes = "Actualiza un Dealer por id.")
	@PostMapping("/patch")
	public ResponseEntity<?> updateStatusDealer(@RequestBody DealersDto dealersDto) {
		try {
			dealerService.updateStatus(dealersDto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Obtiene la lista completa de Dealer", notes = "Enlista todos los Dealer")
	@GetMapping("/getDealer")
	public ResponseEntity<DealersDto> getDealerByNumber(@RequestParam String dealerNumber) {
		DealersDto dealer = this.dealerService.getByDealerNumber(dealerNumber);
		return new ResponseEntity<>(dealer, HttpStatus.OK);
	}

}
