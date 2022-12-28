package com.honda.hdm.warrantiesmg.app.web.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data	
@NoArgsConstructor
public class EmailDto {

	@NotNull(message = "Destination e-mail can not be empty.")
	private List<String> sentTo;
	@NotNull(message = "The e-mail subject can not be empty.")
	private String subject;
	@NotNull(message = "The content of the e-mail can not be empty.")
	private String message;
}
