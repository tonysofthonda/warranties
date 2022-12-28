package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class DefectPartDto {

	private CategoryProblemDto affectedPart;
	private CategoryProblemDto affectedDefect;
	private CategoryProblemDto symptom;
	private String diagTechnical;
	private String correctiveAction;

}
