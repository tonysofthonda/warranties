package api.local.netcommerce.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SalesDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@SerializedName("folio_invoice")
	private String folioInvoice;
	
	@SerializedName("date_sale")
	private String dateSale;
	
	@SerializedName("customer")
	private CustomerDto[] customerDto;
}
