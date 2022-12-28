package com.honda.hdm.warrantiesmg.app.web.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ClassificationInformationDto {
	
	private CategoryProblemDto typeClaim;
    private CategoryProblemDto grade;
    private String servicePerformed;
    private CategoryProblemDto categoryProblem;
    private String repairTechnician;
    private Timestamp reportedDate;
    private Timestamp rapairDate;

}
