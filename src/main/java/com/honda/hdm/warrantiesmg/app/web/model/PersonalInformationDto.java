package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class PersonalInformationDto {

	private ClientInformationDto clientInformation;
    private ReportInformationDto reportInformation;
    private MobileUnitInformationDto mobileUnitInformation;
    
}
