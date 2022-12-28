package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

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

import com.honda.hdm.warrantiesmg.app.service.OtherExpensesService;
import com.honda.hdm.warrantiesmg.app.web.model.OtherExpensesDto;

@RestController
@RequestMapping(value = "other_expenses")
public class OtherExpensesController {
	
	@Autowired
	private OtherExpensesService otherExpensesService;
	
	@GetMapping("/")
	public ResponseEntity<?> getOtherExpenses(@RequestParam (name="warrantyId", required = true) Long warrantyId){
		List<OtherExpensesDto> otherExpensesList = this.otherExpensesService.getAllOtherExpensesByWarranty(warrantyId);
		if (!otherExpensesList.isEmpty()) {
			return new ResponseEntity<>(otherExpensesList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(otherExpensesList, HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveOtherExpenses(@RequestBody OtherExpensesDto otherExpensesDto) throws Exception{
		this.otherExpensesService.otherExpensesSave(otherExpensesDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("/put")
	public ResponseEntity<?> updateOtherExpenses(@RequestBody OtherExpensesDto otherExpensesDto) throws Exception{
		if (otherExpensesDto.getId() != null) {
			this.otherExpensesService.otherExpensesSave(otherExpensesDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}
	
	//Delete Metod
	@PostMapping("/delete")
	public ResponseEntity<?> deleteOtherExpenses(@RequestParam(name ="otherExpensesId", required = true) Long otherExpensesId) throws Exception{
		if (otherExpensesId != null) {
			this.otherExpensesService.deleteOtherExpenses(otherExpensesId);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}
	
}
