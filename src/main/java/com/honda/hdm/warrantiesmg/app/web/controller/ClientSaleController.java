package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.ClientSaleService;
import com.honda.hdm.warrantiesmg.app.web.model.ClientSaleDto;

@RestController
@RequestMapping(value =  "client")
public class ClientSaleController {
	
	@Autowired
	private ClientSaleService clientSaleService;
		
	@GetMapping("/")
	public ResponseEntity<List<ClientSaleDto>> getAllClients(){
		List<ClientSaleDto> clientList = this.clientSaleService.getAll();
		if(clientList.isEmpty()) {
			return new ResponseEntity<>(clientList, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(clientList , HttpStatus.OK);
	}

}
