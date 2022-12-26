package api.local.netcommerce.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ResponseEncode implements Serializable {

		private static final long serialVersionUID = -162141505570849501L;

		@SerializedName("token")
		private String token;
		@SerializedName("code_auth")
		private String code_auth;

}
