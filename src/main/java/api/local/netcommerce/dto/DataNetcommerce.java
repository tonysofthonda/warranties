package api.local.netcommerce.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;


@Data
public class DataNetcommerce implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@SerializedName("vin")
	private String vin;
	@SerializedName("no_motor")
	private String no_motor;
	@SerializedName("model")
	private String model;
	@SerializedName("year")
	private String year;
	@SerializedName("color")
	private String color;
	@SerializedName("num_dealer")
	private String num_dealer;
	@SerializedName("name_dealer")
	private String name_dealer;
	@SerializedName("email")
	private String email;
	@SerializedName("name")
	private String name;
	@SerializedName("date_sale")
	private String date_sale;
	@SerializedName("folio_invoice")
	private String folio_invoice;
	@SerializedName("city")
	private String city;
	@SerializedName("guaranteepolicy")
	private GuaranteepolicyDto[] guaranteepolicyDto;
	@SerializedName("sales")
	private SalesDto[] salesDto;
}
