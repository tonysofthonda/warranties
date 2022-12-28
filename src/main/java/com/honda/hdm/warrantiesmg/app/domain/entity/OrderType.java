package com.honda.hdm.warrantiesmg.app.domain.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "wmq_corder_type")
public class OrderType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="idorder_type")
	private Long idOrderType;
	
	@Column(name ="name")
	private Integer name;

	@Column(name ="description")
	private String description;
	
	@Column(name ="datecreation")
	private Timestamp dateCreation;
	
	@Column(name ="dateupdate")
	private Timestamp dateUpdate;
	
	@Column(name ="obs")
	private String obs;
	
	@Column(name ="bstate")
	private Integer bstate;

}
