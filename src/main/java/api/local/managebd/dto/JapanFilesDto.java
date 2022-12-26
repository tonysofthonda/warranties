package api.local.managebd.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class JapanFilesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String file;
	private String num_part;
	private BigDecimal labor_time;
	private String model;
	private String description;
}
