package com.honda.hdm.warrantiesmg.app.web.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class OrderTypeDto {

	private Long idOrderType;
	
	private Integer name;

	private String description;
	
	private Timestamp dateCreation;
	
	private Timestamp dateUpdate;
	
	private String obs;
	
	private Integer bstate;
	
}
