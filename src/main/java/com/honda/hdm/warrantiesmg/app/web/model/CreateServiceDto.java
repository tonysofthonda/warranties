package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateServiceDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String vin;
    private String serviceNumber;
    private String dealer;
    private String km;
    private String dateService;
    private String createDate;
    private String status;

}
