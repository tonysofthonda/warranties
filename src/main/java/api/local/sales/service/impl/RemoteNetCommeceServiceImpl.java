package api.local.sales.service.impl;

import org.springframework.stereotype.Service;

import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.NetcommerceDto;
import api.local.sales.dto.RequestNetcommerceDto;
import api.local.sales.service.RemoteNetCommeceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor
public class RemoteNetCommeceServiceImpl {

	private final RemoteNetCommeceService remoteService;
	
	public BaseResponse<? extends NetcommerceDto> getNetcommerce(RequestNetcommerceDto request) {
		log.info("get netcommerce in RemoteNetCommeceServiceImpl");
		return remoteService.getNetcommerce(request);
		
	}
}
