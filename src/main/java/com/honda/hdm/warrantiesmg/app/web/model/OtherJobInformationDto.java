package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class OtherJobInformationDto {

	private long id;
    private String description;
    private Integer quantity;
    private String details;
    private String invoice;
    private Double cost;
    private Double total;
    private long warrantyClaims;
    
}
