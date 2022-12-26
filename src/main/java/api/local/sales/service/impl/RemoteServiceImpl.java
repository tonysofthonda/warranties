package api.local.sales.service.impl;

import org.springframework.stereotype.Service;

import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.RequestManagebdDto;
import api.local.sales.dto.RequestRemote;
import api.local.sales.dto.SalesDto;
import api.local.sales.service.RemoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor
public class RemoteServiceImpl {
	
	private final RemoteService remoteService;
	
	public BaseResponse<?> getManagebd(RequestManagebdDto request) {
		log.info("consult in microservice managebd remoteserviceImpl");
		return remoteService.getManagebd(request);	
	}
	
	public BaseResponse<? extends SalesDto> insertManagebd(RequestRemote<? extends SalesDto> request) {
		log.info("insert in microservice managebd remoteserviceImpl");
		return remoteService.insertManagebd(request);
	}
}
