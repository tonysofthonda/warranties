package com.honda.hdm.warrantiesmg.app.web.model.auth;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ViewActionDto implements Serializable {

	public static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String shortName;
	
	private String friendlyName;
	
}
