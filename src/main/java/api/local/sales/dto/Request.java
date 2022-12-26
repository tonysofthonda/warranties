package api.local.sales.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Request implements Serializable {

	private static final long serialVersionUID = -162141505570849501L;

	private String vin;
}
