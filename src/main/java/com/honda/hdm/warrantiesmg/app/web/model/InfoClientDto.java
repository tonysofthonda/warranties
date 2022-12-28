package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class InfoClientDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clientName;
	private String address;
	private Long state;
	private Long township;
	private String phone;
	private String email;
}
