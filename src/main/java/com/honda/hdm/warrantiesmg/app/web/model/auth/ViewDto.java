package com.honda.hdm.warrantiesmg.app.web.model.auth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewDto{

	private Long id;
	
	private String name;
	
	private String friendlyName;
	
	private Integer order;

	private String route;
		
	private MenuCategoryDto menuCategory;

}
