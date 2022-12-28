package com.honda.hdm.warrantiesmg.app.domain.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wmq_warranty_claims")
public class WarrantyClaims implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "claim_number")
	private String numberClaims;

	@Column(name = "service_order")
	private String noServOrder;

	@Column(name = "status_claim")
	private String status;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dealer_id", referencedColumnName = "ID")
	private Dealer dealer;

	@Column(name = "service_employee")
	private String serviceEmployee;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vin_id", referencedColumnName = "ID")
	private Vin vin;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "first_owner_id", referencedColumnName = "ID")
	private FirstOwner firstOwner;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "warranty_client", referencedColumnName = "ID")
	private WarrantyClient warrantyClient;

	@Column(name = "km")
	private Float km;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "type_claim_id", referencedColumnName = "ID")
	private TypeClaims typeClaims;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "grade_id", referencedColumnName = "ID")
	private GradeType gradeType;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "service_model_id", referencedColumnName = "ID")
	private ServiceModel serviceModel;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "problem_category_id", referencedColumnName = "ID")
	private ProblemCategory problemCategory;

	@Column(name = "technical_employee")
	private String technicalEmployee;

	@Column(name = "fail_date")
	private Date failDate;

	@Column(name = "repair_date")
	private Date repairDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parts_defects_symptom_id", referencedColumnName = "ID")
	private PartsDefectsSymptom partsDefectsSymptom;

	@Column(name = "technical_diagnosis")
	private String technicalDiagnosis;

	@Column(name = "corrective_action")
	private String correctiveAction;

	@Column(name = "parts_cost")
	private Double partsCost;

	@Column(name = "packaging_cost")
	private Double packagingCost;

	@Column(name = "labor_cost")
	private Double laborCost;

	@Column(name = "spare_parts_cost")
	private Double sparePartsCost;

	@Column(name = "ttl_cost")
	private Double ttlCost;

	@Column(name = "total_amount")
	private Double totalAmount;

	@Column(name = "create_timestamp", nullable = false, insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name = "update_timestamp", insertable = false)
	private Timestamp updateDate;

	@Column(name = "obs")
	private String obs;

	@Column(name = "date_creation")
	private Timestamp dateCreation;

	@Column(name = "date_update")
	private Timestamp dateUpdate;

	@Column(name = "bstate")
	private Integer bstate;
	
	@Column(name = "sold_by")
	private String soldBy;
	
	@Column(name = "comments")
	private String comments;
	
	public Timestamp getDateUpdate() {
		if(this.dateUpdate == null  ) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			try {
				date = dateFormat.parse("05/05/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long time = date.getTime();
			this.dateUpdate = new Timestamp(time);
		}
		return this.dateUpdate;
	}
	
	public Timestamp getDateCreation() {
		if(this.dateCreation == null  ) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			try {
				date = dateFormat.parse("05/05/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long time = date.getTime();
			this.dateCreation = new Timestamp(time);
		}
		return dateCreation;
	}
	
	public String getObs() {
		if (this.obs == null)
		       this.obs = " ";
		return this.obs;
	}
	
	public Integer getBstate() {
		if (this.bstate == null)
		       this.bstate = 1;
		return this.bstate;
	}
	    
}
