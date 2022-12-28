package com.honda.hdm.warrantiesmg.app.web.model;

import com.honda.hdm.warrantiesmg.app.domain.entity.State;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LocationDto {

	private Long id;
	
	private String name;
	
	private State state;
}
