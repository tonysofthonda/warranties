package api.local.netcommerce.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CustomerDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("email")
	private String email;
	@SerializedName("name")
	private String name;
	@SerializedName("city")
	private String city;
}
