package api.local.managebpcs.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Request implements Serializable {

	private static final long serialVersionUID = -162141505570849501L;

	private String model;
	private String vin;
	private String num_part;
	private String type;
}
