package com.honda.hdm.warrantiesmg.app.domain.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "wmq_client_sale")
public class ClientSale implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "addr_steet")
	private String addrSteet;
	
	@Column(name = "date_register")
	private String dateRegister;
	
	@OneToOne
	@JoinColumn(name = "id_type_clent", referencedColumnName = "ID")
	private TypeClient typeClient;
	
	@OneToOne
	@JoinColumn(name = "id_vin", referencedColumnName = "ID")
	private Vin vin;
	
	@OneToOne
	@JoinColumn(name = "id_city", referencedColumnName = "ID")
	private Country country;
	
	@ManyToOne
	@JoinColumn(name = "id_cdealers", referencedColumnName = "ID")
	private Dealer dealer;
}
