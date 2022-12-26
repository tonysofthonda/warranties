package api.local.netcommerce.enums;

public enum EnumType {

	MODEL("model"),
	SPARE_PARTS("spareparts"),
	SALES("sales"),
	NETCOMMERCE("netcommerce"),
	JAPAN_FILES("japan_file");
	
	private String type;

	EnumType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
