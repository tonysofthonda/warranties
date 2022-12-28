package com.honda.hdm.warrantiesmg.app.web.enums;

public enum ModelColumnEnum {

	CODE("COD. CMPAÑA", 0),
	NUMBER("NO. BOLETIN", 1),
	TITLE("TITULO DE BOLETIN-CAMPAÑA-", 2),
	MODEL("MODELO", 3),
	YEAR("AÑO", 4),
	VIN("VIN", 5),
	CLIENT("LOCALIZACIÓN / CLIENTE", 6),
	DATE_SALE("FECHA DE VENTA", 7),
	DATE_REPAIR("FECHA DE INSP/REP", 8),
	STATUS("STATUS DE CAMPAÑA", 9),
	NOTE("Nota", 10);
	
	private String name;
	private Integer column;
	ModelColumnEnum(String name, Integer column) {
		this.name = name;
		this.column = column;
	}
	public String getName() {
		return name;
	}

	public Integer getColumn() {
		return column;
	}
}
