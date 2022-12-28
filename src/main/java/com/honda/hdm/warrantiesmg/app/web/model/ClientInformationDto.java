package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class ClientInformationDto {

	private String clientName;
    private String address;
    private String phone;
    private CategoryProblemDto state;
    private CategoryProblemDto location;
    private String email;
    
}
