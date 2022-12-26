package api.local.netcommerce.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import api.local.netcommerce.dto.BaseResponse;
import api.local.netcommerce.dto.NetCommerceDto;
import api.local.netcommerce.dto.RequestManagebdDto;
import api.local.netcommerce.dto.RequestManagedbDataDto;


@FeignClient(value = "netcommerce", url = "${microservice.managedb}")
public interface RemoteService {

	@GetMapping("/")
	BaseResponse<?> getManagebd(@SpringQueryMap RequestManagebdDto request);
	
	@PostMapping("/")
	BaseResponse<?> insertManagebd(@RequestBody RequestManagedbDataDto<NetCommerceDto> request);
	
}
