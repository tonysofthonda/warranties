package api.local.sales.enums;

public enum EnumType {

	MODEL("model"),
	SPARE_PARTS("spareparts"),
	SALES("sales"),
	NETCOMMERCE("netcommerce"),
	JAPAN_FILES("japanfiles");
	
	private String type;

	EnumType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
