package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class WarrantyInformationDto {

	private long id;
    private String status;
    private PersonalInformationDto personalInformation;
    private ClassificationInformationDto classificationInformation;
    private PartInformationDto partInformation;
    
}
