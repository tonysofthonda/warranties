package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.TypeClaims;
import com.honda.hdm.warrantiesmg.app.domain.repository.TypeClaimsRepository;
import com.honda.hdm.warrantiesmg.app.service.TypeClaimsService;
import com.honda.hdm.warrantiesmg.app.web.model.TypeClaimsDto;

@Service
public class TypeClaimsServiceImp implements TypeClaimsService{
	
	@Autowired
	private TypeClaimsRepository typeClaimsRepository;

	@Override
	public List<TypeClaimsDto> getAllTypeClaims(Boolean status) {
		List<TypeClaims> typeClaimList = this.typeClaimsRepository.findByStatus(status);
		
		List<TypeClaimsDto> typeDtoList = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		
		for(TypeClaims typeClaims : typeClaimList) {
			typeDtoList.add(mapper.map(typeClaims, TypeClaimsDto.class));
		}
		
		return typeDtoList;
	}

	@Override
	public void saveTypeClaim(TypeClaimsDto typeClaimsDto) throws Exception{
		this.validators(typeClaimsDto);
		if (typeClaimsDto.getId() != null) {
			Optional<TypeClaims> typeClaims = this.typeClaimsRepository.findById(typeClaimsDto.getId());
			if (typeClaims.isPresent()) {
				typeClaims.get().setClaimsType(typeClaimsDto.getClaimsType());
				typeClaims.get().setClaimsDescription(typeClaimsDto.getClaimsDescription());
				typeClaims.get().setStatus(typeClaimsDto.getStatus());				
				this.typeClaimsRepository.save(typeClaims.get());
			}else {
				throw new Exception("El tipo de reclamo no existe.");
			}
		}else {
			TypeClaims typeClaims = new TypeClaims();
			typeClaims.setClaimsType(typeClaimsDto.getClaimsType());
			typeClaims.setClaimsDescription(typeClaimsDto.getClaimsDescription());
			typeClaims.setStatus(typeClaimsDto.getStatus());
			this.typeClaimsRepository.save(typeClaims);
		}
	}
	
	private void validators(TypeClaimsDto typeClaimsDto)  throws Exception{
		if (typeClaimsDto.getClaimsType() == null || typeClaimsDto.getClaimsType().isEmpty()) {
			throw new Exception("El campo tipo de reclamo no puede ser nulo o vacío");
		}
		
		if (typeClaimsDto.getClaimsDescription() == null || typeClaimsDto.getClaimsDescription().isEmpty()) {
			throw new Exception("El campo descripción no puede ser nulo o vacío");
		}

		if (typeClaimsDto.getStatus() == null) {
			throw new Exception("El campo estatus no puede ser nulo o vacío");
		}
	}

	@Override
	public void updateStatusTypeClaim(TypeClaimsDto typeClaimsDto) throws Exception {
		if (typeClaimsDto.getStatus() != null) {
			Optional<TypeClaims> typeClaims = this.typeClaimsRepository.findById(typeClaimsDto.getId());
			if (typeClaims.isPresent()) {
				typeClaims.get().setStatus(typeClaimsDto.getStatus());				
				this.typeClaimsRepository.save(typeClaims.get());
			}else {
				throw new Exception("El tipo de reclamo no existe.");
			}
		}else {
			throw new Exception("El campo estatus no puede ser nulo o vacío");
		}
	}
	
}
