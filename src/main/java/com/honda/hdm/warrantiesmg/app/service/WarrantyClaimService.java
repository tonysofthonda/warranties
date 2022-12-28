package com.honda.hdm.warrantiesmg.app.service;

import java.text.ParseException;
import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.ApprovedDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyConsecutiveDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyConsultDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyDetailDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyDto;
import com.honda.hdm.warrantiesmg.app.web.model.WarrantyInformationDto;

public interface WarrantyClaimService {
	
	public WarrantyConsecutiveDto getWarrantyClaimConsecutive(Integer dealerNumber);
	
	public Boolean existWarrantyClaim(Long warrantyClaimId);
	
	List<WarrantyDto> getWarrantiesClaim();
	
	WarrantyDto getWarrantyClaim(String claimNumber);
	
	WarrantyDetailDto getWarrantyClaimDetail(Long id);
	
	List<WarrantyConsultDto> getWarrantiesConsult(String search, String dateIni, String dateEnd);
	
	public void saveWarrantyClaimInEnviado(WarrantyInformationDto warrantyInformation);
	
	public void saveWarrantyClaimInDraft(WarrantyInformationDto warrantyInformation);
	
	public void saveWarrantyClaimInCancel(WarrantyConsultDto warrantyConsultDto);
	
	public void saveWarrantyClaimOnHold(Long idWarranty);
	
	public void saveWarrantyClaimReactive(Long idWarranty);
	
	ApprovedDto getApprovedAmounts(Long idDealer, String dateIni, String dateEnd) throws ParseException;
	
	ApprovedDto getTotalById(Long idWarranty) throws ParseException;
	
	public void updateWarrantyClaim(Long warrantyId, String status);

}
