package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Data;

@Data
public class FirstOwnerDto {

	public Long id;
	public String firstName;
	public String lastName;
	public String addrStreet;
	public String email;
	public String phone;
	public LocationDto location;
	
}
