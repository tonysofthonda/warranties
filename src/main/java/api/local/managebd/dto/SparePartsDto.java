package api.local.managebd.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class SparePartsDto implements Serializable {

	private static final long serialVersionUID = -959742394621464019L;

	private String  num_part;
	private BigDecimal price;
	private String model;
	private BigDecimal labor_time;
	private String description;
}