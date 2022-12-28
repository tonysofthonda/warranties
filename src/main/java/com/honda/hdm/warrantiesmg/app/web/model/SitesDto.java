package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SitesDto implements Serializable {

	private static final long serialVersionUID = -733611962831020119L;
	
	private Long number;
	private String name;
	private String type;
	private Long idState;
	private Boolean status;

}
