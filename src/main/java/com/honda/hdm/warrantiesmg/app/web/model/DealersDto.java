package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DealersDto {

	private Long id;
	
	private String dealerNumber;
	
	private String name;
	
	private String workTime;
	
	private String type;
	
	private String saturday;
	
	private String email;
	
	private String phone;
	
	private String addrStreet;
	
	private String addrNeigborthoot;
	
	private String gerenteGral;
	
	private String gerenteServ;
	
	private Boolean status;

	private GroupDto group;
	
	private StateDto state;
	
	private ZoneDto zone;
	   
	private LocationDto location;
	
	private String userType;
}
