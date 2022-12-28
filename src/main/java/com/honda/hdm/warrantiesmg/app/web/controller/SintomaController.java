package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.domain.entity.Symptom;
import com.honda.hdm.warrantiesmg.app.service.SintomaServie;
import com.honda.hdm.warrantiesmg.app.web.model.SintomaDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("sintoma")
public class SintomaController {
	
	@Autowired
	private SintomaServie sintomaService;
	
	@ApiOperation(value = "Listar Simtomas", notes = "Devuelve una lista con todos los registros.")
	@GetMapping("/list")
	public ResponseEntity<List<SintomaDto>> getAll(){
		List<SintomaDto> sintomaList = this.sintomaService.getSintomas();
		if(sintomaList.isEmpty()) {
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<>(sintomaList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Buscar Sintoma x id", notes = "Devuelve un ejemplo por id.")
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		
		SintomaDto sintomaFound = this.sintomaService.getByIdSintoma(id);
		
		if(sintomaFound == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}
		
		return new ResponseEntity<>(sintomaFound ,HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Buscar Sintoma x id", notes = "Devuelve un ejemplo por id.")
	@GetMapping("/")
	public ResponseEntity<?> getByCode(@RequestParam String code){
		
		Symptom sintomaFound = this.sintomaService.getByCodigo(code);
		
		if(sintomaFound == null) {
			return new ResponseEntity<>(null, HttpStatus.OK);			
		}
		
		return new ResponseEntity<>(sintomaFound ,HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Guardar Simtoma", notes = "Almacena un nuevo registo de Sintomas.")
	@PostMapping("/save")
	public ResponseEntity<?> saveSintoma(@RequestBody SintomaDto sintomaDto) throws Exception{
		this.sintomaService.saveSintoma(sintomaDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@ApiOperation(value = "Actualiza Sintoma", notes = "Actualiza un Sitoma por id.")
	@PostMapping("/put")
	public ResponseEntity<?> updateSintoma(@RequestBody SintomaDto sintomaDto){		
		if(sintomaDto.getId() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}
		
		try {
			this.sintomaService.updateSintoma(sintomaDto);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getStackTrace().toString(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Pathch Metod
	@ApiOperation(value = "Actualiza estatus sintoma", notes = "Actualiza un estatus sitoma por id.")
	@PostMapping("/patch")
	public ResponseEntity<?> updateStatusSintoma(@RequestBody SintomaDto sintomaDto){
		try {			
			if(sintomaDto.getId() != null) {				
				this.sintomaService.updateStatusSintoma(sintomaDto);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				throw new RuntimeException("El registro que desea actualizar, no existe en el sistema");
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getStackTrace().toString(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	public void validators(SintomaDto sintomaDto) throws Exception {
		String code = "";
		Symptom foundSintomaByCode = this.sintomaService.getByCodigo(sintomaDto.getCode());
		if(foundSintomaByCode == null) {
			code = "0";
		}else {
			code = foundSintomaByCode.getCode();
		}
		
		if(sintomaDto.getCode().isEmpty())  throw new Exception("El campo 'codigo' no deber ser nulo");

		if(sintomaDto.getCode().equalsIgnoreCase(code))  throw new Exception("El campo 'codigo' y existe");
		
		if(sintomaDto.getDescriptionSpa().isEmpty())  throw new Exception("El campo 'Descripcion' no deber ser nulo'");

		if(sintomaDto.getStatus() == null)  throw new Exception("El campo 'Estatus' no deber ser nulo'");
	}

}
