package com.honda.hdm.warrantiesmg.app.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "wmq_ccombine_parts_defects_symptom")
public class PartsDefectsSymptom implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "parts_motorcycle_id", referencedColumnName = "ID")
	private PartMotorcycle partMotorcycle;
	
	@OneToOne
	@JoinColumn(name = "defects_id", referencedColumnName = "ID")
	private Defect defect;

	@OneToOne
	@JoinColumn(name = "symptom_id", referencedColumnName = "ID")
	private Symptom symptom;

	@Column(name="status")
	private Boolean status;
}
