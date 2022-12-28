package com.honda.hdm.warrantiesmg.app.domain.entity;

import java.io.Serializable;
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

@Getter
@Setter
@Entity
@Table(name = "wmq_cdealer")
public class Dealer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "dealer_number", unique = true)
	private String dealerNumber;

	@Column(name = "dealer_name")
	private String name;

	@Column(name = "address")
	private String addrStreet;

	@Column(name = "neighborhood")
	private String neighborhood;

	@Column(name = "dealer_owner")
	private String dealerOwner;

	@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "opening_hours")
	private String openingHours;

	@Column(name = "opening_hours_sat")
	private String openingHoursSat;

	@Column(name = "general_manager")
	private String generalManager;

	@Column(name = "general_manager_tel")
	private String generalManagerTel;

	@Column(name = "general_manager_email")
	private String generalManagerEmail;

	@Column(name = "services_manager")
	private String serviceManager;

	@Column(name = "services_manager_email")
	private String servicesManagerEmail;

	@Column(name = "services_manager_tel")
	private String servicesManagerTel;

	@Column(name = "spare_parts_contact")
	private String sparePartsContact;

	@Column(name = "spare_parts_email")
	private String sparePartsEmail;

	@Column(name = "spare_parts_tel")
	private String sparePartsTel;

	@Column(name = "type")
	private String type;

	@Column(name = "status")
	private Boolean status;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id", referencedColumnName = "id")
	private Group group;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Location location;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "zone_id", referencedColumnName = "id")
	private Zone zone;

	@Column(name = "CREATE_TIMESTAMP", nullable = false, insertable = false, updatable = false)
	private Timestamp createTimestamp;

	@Column(name = "UPDATE_TIMESTAMP", insertable = false)
	private Timestamp updateTimestamp;

	@Column(name="obs", insertable = false)
	private String obs = "";

	@Column(name = "date_creation", insertable = false)
	private Timestamp dateCreation;

	@Column(name = "date_update", insertable = false)
	private Timestamp dateUpdate;

	@Column(name = "bstate")
	private Integer bstate = 1;
	
	@Column(name = "usertype")
	private String userType;
	
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
