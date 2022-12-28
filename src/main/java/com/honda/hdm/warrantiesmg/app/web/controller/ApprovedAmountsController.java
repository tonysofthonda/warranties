package com.honda.hdm.warrantiesmg.app.web.controller;

import java.text.ParseException;
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

import com.honda.hdm.warrantiesmg.app.service.IApprovedAmountsService;
import com.honda.hdm.warrantiesmg.app.web.model.ApprovedAmountDto;
import com.honda.hdm.warrantiesmg.app.web.model.ApprovedDto;

@RestController
@RequestMapping("approved/amounts/")
public class ApprovedAmountsController {

	@Autowired
	private IApprovedAmountsService approvedAmountsService;

	@GetMapping("/")
	public ResponseEntity<ApprovedDto> getAllAprovedAmounts(
			@RequestParam(name = "dealer", required = false) Long idDealer,
			@RequestParam(name = "dateIni", required = true) String dateIni,
			@RequestParam(name = "dateEnd", required = true) String dateEnd) throws ParseException {
		ApprovedDto approvedList = this.approvedAmountsService.getApprovedAmounts(idDealer, dateIni, dateEnd);
		if (approvedList == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(approvedList, HttpStatus.OK);
	}

	//Put Metod
	@PostMapping
	public ResponseEntity<?> changeDate(@RequestBody ApprovedAmountDto approvedAmount) throws Exception {
		if (approvedAmount.getId() != null) {
			System.out.println(approvedAmount.getId());
			this.approvedAmountsService.changeDate(approvedAmount.getId());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}

	//Delete Method
	@PostMapping("delete")
	public ResponseEntity<?> deleteRecord(@RequestParam(name = "id", required = false) Long id,
			@RequestParam String comments) throws Exception {
		if (id != null) {
			this.approvedAmountsService.deleteRecord(id, comments);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}

	//Put Metod
	@PostMapping("approved")
	public ResponseEntity<?> approved(@RequestParam(name = "dealer", required = false) Long idDealer,
			@RequestParam(name = "dateIni", required = true) String dateIni,
			@RequestParam(name = "dateEnd", required = true) String dateEnd,
			@RequestParam(name = "total", required = true) String total,
			@RequestParam(name = "dataId") List<Long> dataId) throws Exception {
		if (idDealer != null) {
			this.approvedAmountsService.insertIntoAmount(dataId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new Exception("El id no puede ser nulo o vacío");
		}
	}

	@GetMapping("/history")
	public ResponseEntity<List<ApprovedAmountDto>> getAllhistory() throws ParseException {
		List<ApprovedAmountDto> approvedList = this.approvedAmountsService.getAllHistory();
		if (approvedList == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(approvedList, HttpStatus.OK);
	}
	
}
