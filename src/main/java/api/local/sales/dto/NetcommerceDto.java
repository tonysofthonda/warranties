package api.local.sales.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class NetcommerceDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String num_dealer;
	private String name_dealer;
	private String model;
	private String nomotor;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_invoice;
	private GuaranteepolicyDto data_guarantee;
}
