package api.local.sales.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.RequestManagebdDto;
import api.local.sales.dto.RequestRemote;
import api.local.sales.dto.SalesDto;

@FeignClient(name = "managedb", url = "${microservice.managedb}")
public interface RemoteService {

	@GetMapping("/")
	public BaseResponse<?> getManagebd(@SpringQueryMap RequestManagebdDto request);
	
	@PostMapping("/")
	public BaseResponse<? extends SalesDto> insertManagebd(@RequestBody RequestRemote<?> request);
}
