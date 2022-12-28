package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Data;

@Data
public class ServiceModelDto {

	public Long id;
	public Integer serviceNumber;
	public String model;
	public String modelYear;
	public Float km;
	public Boolean status;
	
}
