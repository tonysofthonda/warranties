package com.honda.hdm.warrantiesmg.app.web.enums;

public enum StatusEnum {

	DRAFT("Draft"),
	ENVIADO("Enviado"),
	CANCELADO("Cancelado"),
	ONHOLD("Onhold"),
	OPEN("Open"),
	ELIMINADO("Eliminado");
	
	private String status;

	StatusEnum(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}