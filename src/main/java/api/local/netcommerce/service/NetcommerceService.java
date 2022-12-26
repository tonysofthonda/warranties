package api.local.netcommerce.service;

import api.local.netcommerce.dto.BaseResponse;
import api.local.netcommerce.dto.Request;

public interface NetcommerceService {

	BaseResponse<?> checkVinManagebd(final Request request) throws Exception;
	
}
