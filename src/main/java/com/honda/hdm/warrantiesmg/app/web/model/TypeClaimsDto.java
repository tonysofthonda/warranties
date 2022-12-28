package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TypeClaimsDto {

	private Long id;

	private String claimsType;
	
	private String claimsDescription;	
	
	private Boolean status;

}
