package api.local.netcommerce.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class BaseResponse<T> implements Serializable {

	private static final long serialVersionUID = -162141505570849501L;

	private Integer status;
	private String msg;
	private String type;
	private T data;
}
