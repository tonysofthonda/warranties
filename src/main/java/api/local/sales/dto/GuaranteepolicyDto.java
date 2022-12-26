package api.local.sales.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GuaranteepolicyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String folio;
	private String folio_invoice;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_invoice;
}
