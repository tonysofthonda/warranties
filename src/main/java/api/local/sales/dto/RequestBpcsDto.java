package api.local.sales.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestBpcsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String model;
	private String num_part;
	private String vin;
	private String type;
}
