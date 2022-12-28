package com.honda.hdm.warrantiesmg.app.web.model;

import com.honda.hdm.warrantiesmg.app.domain.entity.Country;
import com.honda.hdm.warrantiesmg.app.domain.entity.Dealer;
import com.honda.hdm.warrantiesmg.app.domain.entity.TypeClient;
import com.honda.hdm.warrantiesmg.app.domain.entity.Vin;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientSaleDto {
	
	private Long id;
	
	private String firstName;

	private String lastName;
	
	private String email;
	
	private String phone;
	
	private String addrSteet;
	
	private String dateRegister;
	
	private TypeClient typeClient;
	
	private Vin vin;
	
	private Country country;
	
	private Dealer dealer;

}
