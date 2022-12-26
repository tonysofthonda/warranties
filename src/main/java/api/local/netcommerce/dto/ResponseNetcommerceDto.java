package api.local.netcommerce.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ResponseNetcommerceDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@SerializedName("data")
	private DataNetcommerce[] data;
	@SerializedName("status")
	private String status;
	@SerializedName("msg")
	private String msg;
	@SerializedName("code_auth")
	private String code_auth;
}
