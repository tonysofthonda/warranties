package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClaims;
import com.honda.hdm.warrantiesmg.app.domain.repository.WarrantyClaimsRepository;
import com.honda.hdm.warrantiesmg.app.service.MotorcicleService;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyClaimsDto;

public class MotorcicleServiceImp implements MotorcicleService {
	
	@Autowired
	private WarrantyClaimsRepository claimsRepository;

	@Override
	public List<WarrantyClaimsDto> getAll() {
		List<WarrantyClaimsDto> clamisDto = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		List<WarrantyClaims> claims = claimsRepository.findAll();
		for(WarrantyClaims warranty: claims) {
			clamisDto.add(mapper.map(warranty, WarrantyClaimsDto.class));
		}
		return clamisDto;
	}

}
