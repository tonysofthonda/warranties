package com.honda.hdm.warrantiesmg.app.domain.entity;

import java.sql.Timestamp;

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

@Getter @Setter
@Entity
@Table(name = "wmq_services")
public class Services {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="idservice")
	private Long idService;
	
	@Column(name ="order_number")
	private String orderNumber;
	
	@Column(name ="date_register")
	private Timestamp dateRegister;
	
	@Column(name ="status")
	private String status;
	
	@Column(name ="dealer_num")
	private String dealerNum;
	
	@Column(name ="dealer_name")
	private String dealerName;
	
	@Column(name ="reported_by")
	private String reportedBy;
	
	@Column(name ="customer_name")
	private String customerName;
	
	@Column(name ="customer_phone")
	private String customerPhone;
	
	@Column(name ="customer_email")
	private String customerEmail;
	
	@Column(name ="address")
	private String address;
	
	@Column(name ="vin")
	private String vin;
	
	@Column(name ="model")
	private String model;
	
	@Column(name ="serie")
	private String serie;
	
	@Column(name ="year")
	private Integer year;
	
	@Column(name ="color")
	private String color;
	
	@Column(name ="date_sale")
	private Timestamp dateSale;
	
	@Column(name ="sale_by")
	private String saleBy;
	
	@Column(name ="owner")
	private String owner;
	
	@Column(name ="date_in")
	private Timestamp dateIn;
	
	@Column(name ="date_out")
	private Timestamp dateOut;
	
	@Column(name ="kilometer")
	private Integer kilometer;
	
	@ManyToOne
	@JoinColumn(name = "idorder_type", referencedColumnName = "idorder_type")
	private OrderType orderType;
	
	@ManyToOne
	@JoinColumn(name = "idwork_type", referencedColumnName = "idwork_type")
	private WorkType workType;
	
	@Column(name ="description")
	private String description;
	
	@Column(name ="frt")
	private Double frt;
	
	@Column(name ="workforce")
	private Double workforce;
	
	@Column(name ="total")
	private Double total;
	
	@Column(name ="datecreation")
	private Timestamp dateCreation;
	
	@Column(name ="dateupdate")
	private Timestamp dateUpdate;
	
	@Column(name ="obs")
	private String obs;
	
	@Column(name ="bstate")
	private Integer bstate;
	
}
