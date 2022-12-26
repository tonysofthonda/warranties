package api.local.managebd.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class SalesDto implements Serializable {

	private static final long serialVersionUID = -4541425154592708604L;
	
	private String vin;
	private String num_dealer;
	private String name_dealer;
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_invoice;
	private String model;
	private String nomotor;

}
