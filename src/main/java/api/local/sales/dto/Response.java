package api.local.sales.dto;

public class Response<T> {
	
	public BaseResponse<T> response(final T data, final String message, final Integer status, final String type) {
		BaseResponse<T> response = new BaseResponse<T>();
		response.setMsg(message);
		response.setStatus(status);
		response.setType(type);
		response.setData(data);
		return response;
	}
	
	public BaseResponse<T> response(final String message, final Integer status, final String type) {
		BaseResponse<T> response = new BaseResponse<T>();
		response.setMsg(message);
		response.setStatus(status);
		response.setType(type);
		return response;
	}
	
	public BaseResponse<T> response(final String message, final Integer status) {
		BaseResponse<T> response = new BaseResponse<T>();
		response.setMsg(message);
		response.setStatus(status);
		return response;
	}
}
