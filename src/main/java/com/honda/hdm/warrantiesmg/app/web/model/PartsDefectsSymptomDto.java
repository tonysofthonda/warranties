package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class PartsDefectsSymptomDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private PartMotorcycleDto partMotorcycle;
	
	private DefectosDto defects;
	
	private SintomaDto symptom;
	
	private Boolean status;
}
