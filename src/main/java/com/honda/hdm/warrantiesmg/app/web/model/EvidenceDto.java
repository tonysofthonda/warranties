package com.honda.hdm.warrantiesmg.app.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class EvidenceDto {

	private String fileName;
	private String fileViewUri;
	private String fileType;
	private Integer size1;
	
}
