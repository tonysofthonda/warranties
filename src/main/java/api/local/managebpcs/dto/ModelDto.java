package api.local.managebpcs.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ModelDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long anio;
	private String vds;
	private String model;
	private String color;
	private String cchp;
	private String origin;
	private String description;
}
