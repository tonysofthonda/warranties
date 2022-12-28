package com.honda.hdm.warrantiesmg.app.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class InfoServiceDetailDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String technical;
	private String dateService;
	private String servicePerformed;
	private String serviceDetail;
}
