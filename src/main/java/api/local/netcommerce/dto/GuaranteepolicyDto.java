package api.local.netcommerce.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GuaranteepolicyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("date_invoice")
	private String dateInvoice;
	@SerializedName("folio")
	private String folio;
}
