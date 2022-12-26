package api.local.netcommerce.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class NetCommerceDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String vin;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_sale;
	private String nomotor;
	private String model;
	private Integer year;
	private String color;
	private String name_dealer;
	private String num_dealer;
	private CustumerDto data_custumer;
	private WarrantyPolicyDto data_guarantee;
	
}
