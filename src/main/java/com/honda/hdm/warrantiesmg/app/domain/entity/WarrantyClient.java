package com.honda.hdm.warrantiesmg.app.domain.entity;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "wmq_warranty_client")
public class WarrantyClient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "addr_street")
	private String addressStreet;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
//	@Column(name = "location_id")
//	private int locationId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Location location;
	
	@Column(name = "create_timestamp")
	private Timestamp createTimeStamp;
	
	@Column(name = "update_timestamp")
	private Timestamp updateTimeStamp;
	
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