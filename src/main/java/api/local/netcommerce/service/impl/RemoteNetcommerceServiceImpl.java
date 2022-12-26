package api.local.netcommerce.service.impl;

import org.springframework.stereotype.Service;

import api.local.netcommerce.dto.RequestDataDto;
import api.local.netcommerce.service.RemoteNetcommerceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor
public class RemoteNetcommerceServiceImpl {

	private final RemoteNetcommerceService remoteService;
	
	public String insertManagebd(String token, RequestDataDto request) {
		log.info("start RemoteServiceImpl");
		return remoteService.getSessionId(token, request);
		
	}
}
