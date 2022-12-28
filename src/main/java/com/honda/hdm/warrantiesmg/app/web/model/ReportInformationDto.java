package com.honda.hdm.warrantiesmg.app.web.model;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class ReportInformationDto {

	private String claimNumber;
    private String serviceOrderNumber;
    private String status;
    private Timestamp reportCreationDate;
    private String dealerName;
    private String elaboratedBY;
    
}
