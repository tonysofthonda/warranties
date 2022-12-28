package com.honda.hdm.warrantiesmg.app.web.enums;

public enum SitesEnum {
	
	GROUP("group"),
	STATE("state"),
	TOWNSHIP("township"),
	AREA("area");
	
	private String name;

	SitesEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
