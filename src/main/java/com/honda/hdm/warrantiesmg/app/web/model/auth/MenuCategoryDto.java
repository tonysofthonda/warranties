package com.honda.hdm.warrantiesmg.app.web.model.auth;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuCategoryDto {

	private Long id;
	
	private String name;
	
	private Integer order;
	
	private List<ViewDto> views;
}
