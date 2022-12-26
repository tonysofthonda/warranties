package api.local.managebpcs.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ModelBpcsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dealer;
	private String nameDelaer;
	private String model;
	private String dateInvoice;
	private String motor;
	private String vin;
}
