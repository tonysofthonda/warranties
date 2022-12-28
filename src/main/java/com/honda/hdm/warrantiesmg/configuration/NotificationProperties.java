package com.honda.hdm.warrantiesmg.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {
	
	private String sender;	
	private String rfqSubmitted;
}

