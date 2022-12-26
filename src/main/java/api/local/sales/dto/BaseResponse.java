package api.local.sales.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class BaseResponse<T extends Object> implements Serializable {

	private static final long serialVersionUID = -162141505570849501L;

	private Integer status;
	private String msg;
	private String type;
	private T data;
}
