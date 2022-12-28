package com.honda.hdm.warrantiesmg.app.web.model;

import java.sql.Timestamp;

import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClaims;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Data
public class EvidencesDto {

	private Long id;
	private String typeFile;
	private String filePath;
	private WarrantyClaims warrantyClaim;
	private Boolean status;
	private Timestamp createTimestamp;
	private Timestamp updateTimestamp;
	private String obs;
	private Timestamp dateCreation;
	private Timestamp dateUpdate;
	private Integer bstate;
	
}
