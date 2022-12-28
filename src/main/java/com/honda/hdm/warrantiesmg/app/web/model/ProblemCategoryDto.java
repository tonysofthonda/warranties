package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter
public class ProblemCategoryDto {
	
	private Long id;
	
	private String problemName;
	
	private String problemDescription;

	private Boolean status;

}
