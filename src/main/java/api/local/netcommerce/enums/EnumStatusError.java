package api.local.netcommerce.enums;

public enum EnumStatusError {

	EMPTY_JSON("EMPTY JSON", 0),
	TYPE_UNKNOWN("TYPE UNKNOWN", 0),
	SUCCESS("SUCCESS", 1),
	SAVE_RECORD_FAILED("SAVE RECORD FAILED", 0),
	RECORD_ALREADY_EXIST("RECORD ALREADY EXIST", 0),
	DATABASE_CONNECTION_FAILED("DATABASE CONNECTION FAILED", 0),
	EMPTY_NUM_PART_OR_FILENAME("EMPTY NUM PART OR FILENAME", 0),
	EMPTY_RESULTS("EMPTY RESULTS", 3),
	VIN_EMPTY("VIN EMPTY", 0),
	MODEL_EMPTY("MODEL EMPTY", 0),
	NOPART_EMPTY("NOPART EMPTY", 0),
	INCOMPLETE_DATA("INCOMPLETE DATA", 2),
	METHOD_NOT_ALLOWED("METHOD NOT ALLOWED", 0),
	EMPTY_FOLDER("EMPTY FOLDER", 0),
	NOT_FOUND("NOT FOUND", 3);
	
	private String type;
	private int value;

	EnumStatusError(String type, int value) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	
	public int getValue() {
		return value;
	}
}
