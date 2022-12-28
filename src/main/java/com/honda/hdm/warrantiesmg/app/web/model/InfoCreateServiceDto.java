package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class InfoCreateServiceDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private InfoReportDto infoReportDto;
	private InfoClientDto infoClientDto;
	private InfoMobileUnitDto infoMobileUnitDto;
	private InfoServiceDetailDto infoServiceDetailDto;

}
