package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.OtherExpenses;
import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClaims;
import com.honda.hdm.warrantiesmg.app.domain.repository.OtherExpensesRepository;
import com.honda.hdm.warrantiesmg.app.service.OtherExpensesService;
import com.honda.hdm.warrantiesmg.app.service.WarrantyClaimService;
import com.honda.hdm.warrantiesmg.app.web.model.OtherExpensesDto;

@Service
public class OtherExpensesServiceImp implements OtherExpensesService{

	@Autowired
	private OtherExpensesRepository otherExpensesRepository;
	
	@Autowired
	private WarrantyClaimService warrantyClaimService;
	
	@Override
	public List<OtherExpensesDto> getAllOtherExpensesByWarranty(Long warrantyId) {
		List<OtherExpenses> otherExpensesList = this.otherExpensesRepository.findAllByWarrantyClaimsId(warrantyId);
		List<OtherExpensesDto> otherExpensesDtoList = new ArrayList<>();
		if (!otherExpensesList.isEmpty()) {
			for (OtherExpenses otherExpenses: otherExpensesList) {
				OtherExpensesDto otherExpensesDto = new OtherExpensesDto();
				otherExpensesDto.setId(otherExpenses.getId());								
				otherExpensesDto.setDescription(otherExpenses.getDescriptionExpenses());				
				otherExpensesDto.setQuantity(otherExpenses.getQuantity());
				otherExpensesDto.setDetails(otherExpenses.getDetails());		
				otherExpensesDto.setInvoice(otherExpenses.getInvoice());		
				otherExpensesDto.setCost(otherExpenses.getCost());	
				otherExpensesDto.setTotal(otherExpenses.getCost()*otherExpenses.getQuantity());			
				otherExpensesDto.setWarrantyClaims(otherExpenses.getWarrantyClaims().getId());
				otherExpensesDtoList.add(otherExpensesDto);
			}
			
		} 
		return otherExpensesDtoList;
	}

	@Override
	public void otherExpensesSave(OtherExpensesDto otherExpensesDto) throws Exception {
		this.validators(otherExpensesDto);
		if (this.warrantyClaimService.existWarrantyClaim(otherExpensesDto.getWarrantyClaims())) {
			if (otherExpensesDto.getId() != null ) {
				Optional<OtherExpenses> otherExpenses = this.otherExpensesRepository.findById(otherExpensesDto.getId());
				if (otherExpenses.isPresent()) {
					otherExpenses.get().setDescriptionExpenses(otherExpensesDto.getDescription());
					otherExpenses.get().setQuantity(otherExpensesDto.getQuantity());
					otherExpenses.get().setDetails(otherExpensesDto.getDetails());
					otherExpenses.get().setInvoice(otherExpensesDto.getInvoice());
					otherExpenses.get().setCost(otherExpensesDto.getCost());
					otherExpenses.get().setTotal(otherExpensesDto.getTotal());					
					WarrantyClaims warrantyClaims = new WarrantyClaims();
					warrantyClaims.setId(otherExpensesDto.getWarrantyClaims());
					otherExpenses.get().setWarrantyClaims(warrantyClaims);					
					this.otherExpensesRepository.save(otherExpenses.get());					
				}else {
					throw new Exception("El gasto no existe.");
				}
			}else { 
				OtherExpenses otherExpenses = new OtherExpenses();
				otherExpenses.setDescriptionExpenses(otherExpensesDto.getDescription());
				otherExpenses.setQuantity(otherExpensesDto.getQuantity());
				otherExpenses.setDetails(otherExpensesDto.getDetails());
				otherExpenses.setInvoice(otherExpensesDto.getInvoice());
				otherExpenses.setCost(otherExpensesDto.getCost());
				otherExpenses.setTotal(otherExpensesDto.getTotal());					
				WarrantyClaims warrantyClaims = new WarrantyClaims();
				warrantyClaims.setId(otherExpensesDto.getWarrantyClaims());
				otherExpenses.setWarrantyClaims(warrantyClaims);					
				this.otherExpensesRepository.save(otherExpenses);
			}			
		}else {
			throw new Exception("El Warranty Claims no existe.");
		}
	}
	
	private void validators(OtherExpensesDto otherExpensesDto) throws Exception{
		
		if (otherExpensesDto.getDescription() == null || otherExpensesDto.getDescription().isEmpty()) {
			throw new Exception("El campo descripción no puede ser nulo o vacío");
		}
		
		if (otherExpensesDto.getQuantity() == null || otherExpensesDto.getQuantity() == 0) {
			throw new Exception("El campo quantity no puede ser nulo o vacío");
		}
		
		if (otherExpensesDto.getDetails() == null || otherExpensesDto.getDetails().isEmpty()) {
			throw new Exception("El campo detalle no puede ser nulo o vacío");
		}
		
		if (otherExpensesDto.getInvoice() == null || otherExpensesDto.getInvoice().isEmpty()) {
			throw new Exception("El campo invoice no puede ser nulo o vacío");
		}
		
		if (otherExpensesDto.getCost() == null || otherExpensesDto.getCost() == 0) {
			throw new Exception("El campo costo no puede ser nulo o cero");
		}
		
		if (otherExpensesDto.getTotal() == null || otherExpensesDto.getTotal() == 0) {
			throw new Exception("El campo total no puede ser nulo o cero");
		}
		
		if (otherExpensesDto.getWarrantyClaims() == null || otherExpensesDto.getWarrantyClaims() == 0 ) {
			throw new Exception("El campo warranty no puede ser nulo o cero");
		}
	}

	@Override
	public void deleteOtherExpenses(Long otherExpensesId) throws Exception {
		Optional<OtherExpenses> otherExpenses = this.otherExpensesRepository.findById(otherExpensesId);
		if (otherExpenses.isPresent()) {
			this.otherExpensesRepository.delete(otherExpenses.get());
		}else {
			throw new Exception("El gasto no existe");
		}
	
		
	}
}
