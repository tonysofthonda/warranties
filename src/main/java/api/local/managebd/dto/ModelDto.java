package api.local.managebd.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ModelDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String model;
	private Long anio;
	private String color;
	private String description;
	private BigDecimal cchp;
	private String vds;
	private String origin;
}
