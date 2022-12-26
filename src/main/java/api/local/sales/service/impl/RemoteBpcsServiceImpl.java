package api.local.sales.service.impl;

import org.springframework.stereotype.Service;

import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.RequestBpcsDto;
import api.local.sales.dto.SalesDto;
import api.local.sales.service.RemoteBpcsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@RequiredArgsConstructor
public class RemoteBpcsServiceImpl {

	private final RemoteBpcsService remoteService;
	
	public BaseResponse<? extends SalesDto> getBpcs(RequestBpcsDto request) {
		log.info("consult remote bpcs service");
		return remoteService.getBpcs(request);
		
	}
}
