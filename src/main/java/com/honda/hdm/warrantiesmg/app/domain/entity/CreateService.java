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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "wmq_create_service")
public class CreateService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="number_order")
	private String serviceNumber;
	
	@Column(name="status")
	private String status;
	
	@OneToOne
	@JoinColumn(name = "cdealer_id", referencedColumnName = "ID")
	private Dealer dealer;
	
	@Column(name="km")
	private Double km;
	
	@OneToOne
	@JoinColumn(name = "cmodel_id", referencedColumnName = "ID")
	private Model model;
	
	@OneToOne
	@JoinColumn(name = "vin_id", referencedColumnName = "ID")
	private Vin vin;
	
	@OneToOne
	@JoinColumn(name = "service_client_id", referencedColumnName = "ID")
	private ServiceClient serviceClient;
	
	@Column(name = "sale_date", nullable = false, insertable = false, updatable = false)
	private Timestamp saleDate;
	
	@Column(name="sold_by")
	private String soldBy;
	
	@Column(name="owner")
	private String owner;

	@Column(name="technical")
	private String technical;
	
	@Column(name="service_performed")
	private String servicePerformed;
	
	@Column(name="service_detail")
	private String serviceDetail;
	
	@Column(name = "create_report_by")
	private String createReportBy;
	
	@Column(name="date_service")
	private Timestamp dateService;
	
	@Column(name = "create_timestamp", insertable = false)
	private Timestamp createTimestamp;
	
	@Column(name = "update_timestamp", insertable = false)
	private Timestamp updateTimestamp;
	
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
