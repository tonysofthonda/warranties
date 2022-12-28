package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.domain.entity.OrderType;
import com.honda.hdm.warrantiesmg.app.service.OrderTypeService;

@RestController
@RequestMapping(value = "order-type")
public class OrderTypeController {
	
	@Autowired
	private OrderTypeService orderTypeService;

	@GetMapping("/")
	public ResponseEntity<List<OrderType>> getAll(@RequestParam(required = false) Long id){
		if(id != null) {
			OrderType order = this.orderTypeService.findByIdOrderType(id);
			List<OrderType> list = new ArrayList<>();
			list.add(order);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			List<OrderType> list = this.orderTypeService.findAll();
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}
	
}
