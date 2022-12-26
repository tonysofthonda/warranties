package api.local.netcommerce.service.impl;

import org.springframework.stereotype.Service;

import api.local.netcommerce.dto.BaseResponse;
import api.local.netcommerce.dto.NetCommerceDto;
import api.local.netcommerce.dto.RequestManagebdDto;
import api.local.netcommerce.dto.RequestManagedbDataDto;
import api.local.netcommerce.service.RemoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor
public class RemoteServiceImpl {

	private final RemoteService remoteService;
	
	public BaseResponse<?> getManagebd(RequestManagebdDto request) {
		log.info("start RemoteServiceImpl get");
		return remoteService.getManagebd(request);
		
	}
	
	public BaseResponse<?> insertManagebd(RequestManagedbDataDto<NetCommerceDto> request) {
		log.info("start RemoteServiceImpl insert");
		return remoteService.insertManagebd(request);
		
	}
}
