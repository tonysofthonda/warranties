package api.local.sales.service;

import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.Request;

public interface SalesService {

	BaseResponse<?> sales(final Request request);

}
