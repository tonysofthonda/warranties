package api.local.sales.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestNetcommerceDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String vin;
}
