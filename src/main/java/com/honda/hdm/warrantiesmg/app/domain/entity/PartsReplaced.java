package com.honda.hdm.warrantiesmg.app.domain.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
@Table(name = "wmq_replaced_parts")
public class PartsReplaced implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description")
	private String description;

	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "packaging_cost")					
	private Double packingCost;

	@Column(name = "unit_cost")
	private Double unitCost;

	@Column(name = "total")
	private Double total;
	
	@ManyToOne
	@JoinColumn(name = "cparts_id", referencedColumnName = "ID")
	private Parts parts;
	
	@ManyToOne
	@JoinColumn(name = "warranty_claims_id", referencedColumnName = "ID")
	private WarrantyClaims warrantyClaims;
	
	@Column(name = "obs", columnDefinition = "varchar(255) default ''")
	private String obs;

	@Column(name = "date_creation", columnDefinition = "timestamp default 2022-05-08")
	private Timestamp dateCreation;

	@Column(name = "date_update", columnDefinition = "timestamp default 2022-05-08")
	private Timestamp dateUpdate;

	@Column(name = "bstate", columnDefinition = "integer default 1")
	private Integer bstate;
	
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