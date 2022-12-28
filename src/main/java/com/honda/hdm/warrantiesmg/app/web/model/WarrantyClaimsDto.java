package com.honda.hdm.warrantiesmg.app.web.model;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.domain.entity.ClientOwner;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartsReplaced;
import com.honda.hdm.warrantiesmg.app.domain.entity.Resources;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WarrantyClaimsDto {

	private Long id;
	
	private String numberClaims;
	
	private Integer kilometros;
	
	private String noServOrder;
	
	private String dateFail;
	
	private String dateRepair;
	
	private String technicianName;
	
	private String dateRegister;
	
	private String dateUpdate;
	
	private String status;
	
	private double totalParts;
	
	private double totalDesembarque;
	
	private double moHours;
	
	private double costSpareParts;
	
	private double totalFrt;

	private double totalPartsFrt;
	
	private TypeClaimsDto typeClaims;
	
	private GradeTypeDto grade;
	//TODO Cambiar o crear el DTO	
	private List<PartsReplaced> partsReplaced;
	
	private ClientSaleDto clientSale;
	
	//TODO Cambiar o crear el DTO
	private ClientOwner clientOwner;
	
	private List<OtherExpensesDto> expenses;
	
	//TODO Cambiar o crear el DTO 
	private List<Resources> resources;
}
