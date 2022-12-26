package api.local.netcommerce.enums;

public enum EnumActions {

	GET("get"),
	POST("insert");
	
	private String action;

	EnumActions(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}
}
