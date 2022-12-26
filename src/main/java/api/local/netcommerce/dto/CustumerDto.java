package api.local.netcommerce.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustumerDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String city;
}
