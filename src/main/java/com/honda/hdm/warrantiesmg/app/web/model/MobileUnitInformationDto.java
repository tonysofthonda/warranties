package com.honda.hdm.warrantiesmg.app.web.model;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class MobileUnitInformationDto {

	private String vinNumber;
    private float mileage;
    private CategoryProblemDto model;
    private String engineSerie;
    private Timestamp saleDate;
    private String saleBy;
    private String owner;
    
}
