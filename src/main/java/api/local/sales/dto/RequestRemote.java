package api.local.sales.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestRemote<T> implements Serializable {

	private static final long serialVersionUID = -162141505570849501L;

	private String model;
	private String num_part;
	private String vin;
	private String file;
	private String action;
	private String type;
	private Object data;

}
