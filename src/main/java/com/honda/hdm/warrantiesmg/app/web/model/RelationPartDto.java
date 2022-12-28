package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class RelationPartDto {

	private long id;
    private String description;
    private Integer quantity;
    private Double packingCost;
    private Double unitCost;
    private Double total;
    private PartDto part;
    private long warrantyClaimsID;
    
}
