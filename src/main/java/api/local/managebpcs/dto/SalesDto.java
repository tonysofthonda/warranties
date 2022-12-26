package api.local.managebpcs.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SalesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String vin;
	private String num_dealer;
	private String name_dealer;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date invoice_date;
	private String model;
	private String motor;
}
