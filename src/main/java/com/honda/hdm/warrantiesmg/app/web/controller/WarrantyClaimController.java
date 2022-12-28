package com.honda.hdm.warrantiesmg.app.web.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.WarrantyClaimService;
import com.honda.hdm.warrantiesmg.app.web.model.ApprovedAmountDto;
import com.honda.hdm.warrantiesmg.app.web.model.ApprovedDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyConsecutiveDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyConsultDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyInformationDto;

@RestController
@RequestMapping(value = "warranty-claims")
public class WarrantyClaimController {

	@Autowired
	private WarrantyClaimService warrantyClaimService;

	@GetMapping("/consecutive")
	public ResponseEntity<?> getWarrantyClaimConsecutive(
			@RequestParam(name = "dealerNumber", required = true) Integer dealerNumber) {
		WarrantyConsecutiveDto warrantyConsecutiveDto = this.warrantyClaimService
				.getWarrantyClaimConsecutive(dealerNumber);
		return new ResponseEntity<>(warrantyConsecutiveDto, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getWarrantiesClaim() {
		return new ResponseEntity<>(this.warrantyClaimService.getWarrantiesClaim(), HttpStatus.OK);
	}
	
	@GetMapping("/get-by-claim-number")
	public ResponseEntity<?> getWarrantiesClaimByClaimNumber(@RequestParam String claimNumber) {
		return new ResponseEntity<>(this.warrantyClaimService.getWarrantyClaim(claimNumber), HttpStatus.OK);
	}

	@GetMapping("/detail")
	public ResponseEntity<?> getWarrantyClaim(@RequestParam Long id) {
		return new ResponseEntity<>(this.warrantyClaimService.getWarrantyClaimDetail(id), HttpStatus.OK);
	}

	@GetMapping("/consult")
	public ResponseEntity<?> getWarrantiesConsult(@RequestParam(name = "search") String search,
			@RequestParam(name = "dateIni", required = true) String dateIni,
			@RequestParam(name = "dateEnd", required = true) String dateEnd) {
		return new ResponseEntity<>(this.warrantyClaimService.getWarrantiesConsult(search, dateIni, dateEnd),
				HttpStatus.OK);
	}

	//Put Metod
	@PostMapping("/warranty-claim-enviado")
	public ResponseEntity<?> saveWarrantyClaim(@RequestBody WarrantyInformationDto warrantyInformation) {
		warrantyClaimService.saveWarrantyClaimInEnviado(warrantyInformation);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//Put Metod
	@PostMapping("/warranty-claim-cancel")
	public ResponseEntity<?> cancelWarrantyClaim(@RequestBody WarrantyConsultDto warrantyConsultDto) {
		warrantyClaimService.saveWarrantyClaimInCancel(warrantyConsultDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/warranty-claim-draft")
	public ResponseEntity<?> saveWarrantyClaimInDraft(@RequestBody WarrantyInformationDto warrantyInformation) {
		warrantyClaimService.saveWarrantyClaimInDraft(warrantyInformation);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/getAllAprovedAmounts")
	public ResponseEntity<ApprovedDto> getAllAprovedAmounts(
			@RequestParam(name = "dealer", required = false) Long idDealer,
			@RequestParam(name = "dateIni", required = true) String dateIni,
			@RequestParam(name = "dateEnd", required = true) String dateEnd) throws ParseException {
		ApprovedDto approvedList = this.warrantyClaimService.getApprovedAmounts(idDealer, dateIni, dateEnd);
		if (approvedList == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(approvedList, HttpStatus.OK);
	}

	@GetMapping("/getTotalById")
	public ResponseEntity<ApprovedDto> getTotalById(@RequestParam Long idWarranty) throws ParseException {
		ApprovedDto approvedList = this.warrantyClaimService.getTotalById(idWarranty);
		if (approvedList == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(approvedList, HttpStatus.OK);
	}

	//Put Metod
	@PostMapping("/onhold")
	public ResponseEntity<ApprovedDto> onholdWarranty(@RequestBody ApprovedAmountDto approvedAmount) throws ParseException {
		warrantyClaimService.saveWarrantyClaimOnHold(approvedAmount.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Put Metod
	@PostMapping("/reactive")
	public ResponseEntity<ApprovedDto> reactiveWarranty(@RequestBody ApprovedAmountDto approvedAmount) throws ParseException {
		warrantyClaimService.saveWarrantyClaimReactive(approvedAmount.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
