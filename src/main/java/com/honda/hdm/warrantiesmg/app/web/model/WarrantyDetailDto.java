package com.honda.hdm.warrantiesmg.app.web.model;

import java.sql.Timestamp;
import java.util.Date;

import com.honda.hdm.warrantiesmg.app.domain.entity.Dealer;
import com.honda.hdm.warrantiesmg.app.domain.entity.FirstOwner;
import com.honda.hdm.warrantiesmg.app.domain.entity.GradeType;
import com.honda.hdm.warrantiesmg.app.domain.entity.PartsDefectsSymptom;
import com.honda.hdm.warrantiesmg.app.domain.entity.ProblemCategory;
import com.honda.hdm.warrantiesmg.app.domain.entity.ServiceModel;
import com.honda.hdm.warrantiesmg.app.domain.entity.TypeClaims;
import com.honda.hdm.warrantiesmg.app.domain.entity.Vin;
import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClient;

import lombok.Data;

@Data
public class WarrantyDetailDto {
	
	public Long id;
	public String numberClaims;
	public String noServOrder;
	public String status;
	public Dealer dealer;
	public String serviceEmployee;
	public Vin vin;
	public FirstOwner firstOwner;
	public WarrantyClient warrantyClient;
	public Float km;
	public TypeClaims typeClaims;
	public GradeType gradeType;
	public ServiceModel serviceModel;
	public ProblemCategory problemCategory;
	public String technicalEmployee;
	public Date failDate;
	public Date repairDate;
	public PartsDefectsSymptom partsDefectsSymptom;
	public String technicalDiagnosis;
	public String correctiveAction;
	public Double partsCost;
	public Double packagingCost;
	public Double laborCost;
	public Double sparePartsCost;
	public Double ttlCost;
	public Double totalAmount;
	public Timestamp createDate;
	public Timestamp updateDate;
	public String obs;
	public Timestamp dateCreation;
	public Timestamp dateUpdate;
	public Integer bstate;
	private String saleBy;
	private String elaboratedBY;

}
