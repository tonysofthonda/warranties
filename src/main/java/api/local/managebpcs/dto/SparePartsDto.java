package api.local.managebpcs.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class SparePartsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal price;
}
