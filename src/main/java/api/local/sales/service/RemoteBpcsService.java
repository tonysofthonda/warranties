package api.local.sales.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.RequestBpcsDto;
import api.local.sales.dto.SalesDto;

@FeignClient(name = "bpcs", url = "${microservice.bpcs}")
public interface RemoteBpcsService {

	@GetMapping(value = "/", consumes = "application/json")
	public BaseResponse<? extends SalesDto> getBpcs(@SpringQueryMap RequestBpcsDto request);
}
