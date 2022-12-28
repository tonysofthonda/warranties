package com.honda.hdm.warrantiesmg.app.web.model;

import java.util.Date;

import lombok.Data;

@Data
public class VinDto {
	
	public Long id;
	public String vin;
	public String engineNumber;
	public String modelYear;
	public String color;
	public Date salesDate;
	public String warrantyNumberFolio;
	public Date  warrantyDateExpdate;
	public String invoiceFolio;
	public ModelDto model;

}
