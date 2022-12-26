package api.local.netcommerce.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestManagebdDto implements Serializable {

	private static final long serialVersionUID = -1391360501017635094L;

	private String model;
	private String num_part;
	private String vin;
	private String action;
	private String type;
}
