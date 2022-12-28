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

import com.honda.hdm.warrantiesmg.app.domain.entity.WorkType;
import com.honda.hdm.warrantiesmg.app.service.WorkTypeService;

@RestController
@RequestMapping(value = "work-type")
public class WorkTypeController {
	
	@Autowired
	private WorkTypeService workTypeService;

	@GetMapping("/")
	public ResponseEntity<Object> getAll(@RequestParam(required = false) Long id){
		if(id != null) {
			WorkType work = this.workTypeService.findByIdWorkType(id);
			List<WorkType> list = new ArrayList<>();
			list.add(work);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			List<WorkType> list = this.workTypeService.findAll();
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("by-order-type/")
	public ResponseEntity<Object> getByIdOrderType(@RequestParam Long id) {
		List<WorkType> list = this.workTypeService.findByIdOrderType(id);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
}
