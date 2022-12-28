package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.TypeClaimsDto;

public interface TypeClaimsService {
	
	List<TypeClaimsDto> getAllTypeClaims(Boolean status);
	
	void saveTypeClaim(TypeClaimsDto typeClaimsDto) throws Exception;

	void updateStatusTypeClaim(TypeClaimsDto typeClaimsDto) throws Exception;
}
