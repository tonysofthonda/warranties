package api.local.sales.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.NetcommerceDto;
import api.local.sales.dto.RequestNetcommerceDto;

@FeignClient(name = "netcommerce", url = "${microservice.netcommerce}")
public interface RemoteNetCommeceService {

	@GetMapping(value = "/", consumes = "application/json")
	public BaseResponse<? extends NetcommerceDto> getNetcommerce(@SpringQueryMap RequestNetcommerceDto request);
}
