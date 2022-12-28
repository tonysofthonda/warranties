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
@Table(name = "wmq_services_parts")
public class ServicesParts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="idservice_part")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idservice", referencedColumnName = "idservice")
	private Services services;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Parts parts;
	
	@Column(name ="part_number")
	private String partNumber;
	
	@Column(name ="datecreation")
	private Timestamp dateCreation;
	
	@Column(name ="dateupdate")
	private Timestamp dateUpdate;
	
	@Column(name ="obs")
	private String obs;
	
	@Column(name ="bstate")
	private Integer bstate;
	
	@Column(name ="qty")
	private Integer qty;
	
	@Column(name ="description")
	private String description;
	
	@Column(name ="unit_cost")
	private Double unitCost;
	
	@Column(name ="labor_time")
	private Double laborTime;
	
	@Column(name ="total")
	private Double total;
	
}
