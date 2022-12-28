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
@Table(name = "wmq_cwork_type")
public class WorkType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="idwork_type")
	private Long idWorkType;
	
	@Column(name ="name")
	private Integer name;

	@Column(name ="description")
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idorder_type", referencedColumnName = "idorder_type")
	private OrderType orderType;
	
	@Column(name ="details")
	private String details;
	
	@Column(name ="datecreation")
	private Timestamp dateCreation;
	
	@Column(name ="dateupdate")
	private Timestamp dateUpdate;
	
	@Column(name ="obs")
	private String obs;
	
	@Column(name ="bstate")
	private Integer bstate;

}
