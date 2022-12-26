package api.local.netcommerce.dto;

import java.io.Serializable;

@lombok.Data
public class RequestDataDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String vin;
	private String code_auth;

}
