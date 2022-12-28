package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Parts;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartsReplaced;
import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClaims;
import com.honda.hdm.warrantiesmg.app.domain.repository.PartsReplacedRepository;
import com.honda.hdm.warrantiesmg.app.service.PartsReplacedService;
import com.honda.hdm.warrantiesmg.app.service.PartsService;
import com.honda.hdm.warrantiesmg.app.service.WarrantyClaimService;
import com.honda.hdm.warrantiesmg.app.web.model.PartDto;
import com.honda.hdm.warrantiesmg.app.web.model.PartsReplacedDto;

@Service
public class PartsReplacedServiceImp implements PartsReplacedService{
	
	@Autowired
	private PartsReplacedRepository partsReplacedRepository;
	
	@Autowired
	private PartsService partsService;
	
	@Autowired
	private WarrantyClaimService warrantyClaimService;

	@Override
	public List<PartsReplacedDto> getAllPartsReplaced(Long warrantyId) {		
		List<PartsReplaced> partsList = this.partsReplacedRepository.findAllByWarrantyClaimsId(warrantyId);
		List<PartsReplacedDto> partsReplacedList = new ArrayList<>();
		if (!partsList.isEmpty()) {
			for (PartsReplaced part : partsList) {
				PartsReplacedDto partsReplacedDto = new PartsReplacedDto();					
				ModelMapper modelMapper = new ModelMapper();
				modelMapper.map(part, partsReplacedDto);
				PartDto partDto = new PartDto();
				modelMapper.map(part.getParts(), partDto);
				partsReplacedDto.setPart(partDto);
				partsReplacedList.add(partsReplacedDto);
			}
			return partsReplacedList;
		}else {
			return partsReplacedList;
		}
	}

	@Override
	public void savePartsReplaced(PartsReplacedDto partsReplacedDto) throws Exception {
		this.validators(partsReplacedDto);
		if (this.warrantyClaimService.existWarrantyClaim(partsReplacedDto.getWarrantyClaimsId())) {
			if (partsReplacedDto.getId() != null ) {
				Optional<PartsReplaced> partsReplaced = this.partsReplacedRepository.findById(partsReplacedDto.getId());
				if (partsReplaced.isPresent()) {

					partsReplaced.get().setDescription(partsReplacedDto.getDescription());
					partsReplaced.get().setPackingCost(partsReplacedDto.getPackingCost());
					partsReplaced.get().setQuantity(partsReplacedDto.getQuantity());
					partsReplaced.get().setTotal(partsReplacedDto.getTotal());
					partsReplaced.get().setUnitCost(partsReplacedDto.getUnitCost());
					
					Parts parts;
					parts  = this.validatePart(partsReplacedDto.getPart().getId());
					partsReplaced.get().setParts(parts);
					
					this.partsReplacedRepository.save(partsReplaced.get());
				}else {
					throw new Exception("La parte no existe.");
				}
			}else {
				
				PartsReplaced partsReplaced = new PartsReplaced();
				
				partsReplaced.setDescription(partsReplacedDto.getDescription());
				partsReplaced.setPackingCost(partsReplacedDto.getPackingCost());
				partsReplaced.setQuantity(partsReplacedDto.getQuantity());
				partsReplaced.setTotal(partsReplacedDto.getTotal());
				partsReplaced.setUnitCost(partsReplacedDto.getUnitCost());
				
				Parts parts;
				parts  = this.validatePart(partsReplacedDto.getPart().getId());
				partsReplaced.setParts(parts);
				
				WarrantyClaims warrantyClaims = new WarrantyClaims();
				warrantyClaims.setId(partsReplacedDto.getWarrantyClaimsId());
				partsReplaced.setWarrantyClaims(warrantyClaims);
								
				partsReplaced.setParts(this.validatePart(partsReplacedDto.getPart().getId()));
				this.partsReplacedRepository.save(partsReplaced);
			}
		}
	}
	
	@Override
	public void deletePartsReplaced(Long partsReplacedId) throws Exception {
		Optional<PartsReplaced> partsReplaced = this.partsReplacedRepository.findById(partsReplacedId);
		if (partsReplaced.isPresent()) {
			this.partsReplacedRepository.delete(partsReplaced.get());		
		}else {
			throw new Exception("La parte no existe.");
		}
	}
	
	private Parts validatePart(Long partId) throws Exception {		
		Parts part = this.partsService.findPartById(partId);
		if (part != null ) {
			return part;
		}else {
			throw new Exception("La parte no existe.");
		}
	}
	
	private void validators(PartsReplacedDto partsReplacedDto) throws Exception{
		
		if (partsReplacedDto.getDescription() == null || partsReplacedDto.getDescription().isEmpty()) {
			throw new Exception("El campo Descripción no puede ser nulo o vacío");			
		}
		
		if (partsReplacedDto.getPackingCost() == null || partsReplacedDto.getPackingCost() ==0) {
			throw new Exception("El campo Packing cost no puede ser nulo o cero");
		}
		
		if (partsReplacedDto.getPart() == null || partsReplacedDto.getPart().getId() ==0) {
			throw new Exception("La Parte no puede ser nulo o cero");			
		}
		
		if (partsReplacedDto.getQuantity() == null || partsReplacedDto.getQuantity() ==0) {
			throw new Exception("El campo Quantity no puede ser nulo o cero");
		}
		
		if (partsReplacedDto.getTotal() == null || partsReplacedDto.getTotal() ==0) {
			throw new Exception("El campo Total no puede ser nulo o cero");
		}
		
		if (partsReplacedDto.getUnitCost() == null || partsReplacedDto.getUnitCost() ==0) {
			throw new Exception("El campo Unit cost no puede ser nulo o cero");
		}
		
		if (partsReplacedDto.getWarrantyClaimsId() == null || partsReplacedDto.getWarrantyClaimsId() ==0) {
			throw new Exception("El campo Warranty claims no puede ser nulo o cero");
		}
	}

}
