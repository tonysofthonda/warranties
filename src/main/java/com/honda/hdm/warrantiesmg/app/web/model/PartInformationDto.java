package com.honda.hdm.warrantiesmg.app.web.model;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class PartInformationDto {

	private DefectPartDto defectPart;
    private List<RelationPartDto> relationPart;
    private List<OtherJobInformationDto> otherJobInformation;
    private CostDetailDto costDetail;
    private List<EvidenceDto> evidence;
}
