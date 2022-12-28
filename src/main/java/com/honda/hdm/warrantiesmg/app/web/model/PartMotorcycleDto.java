package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PartMotorcycleDto {

	private Long id;

	private String partNameEnglish;

	private String partNameSpanish;

	private Boolean status;

}
