package api.local.netcommerce.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import api.local.netcommerce.dto.RequestDataDto;
import feign.Headers;
import feign.Param;

@FeignClient(value = "netcommerceApi", url = "http://api.ventasmotos.honda.mx/info_vin")
public interface RemoteNetcommerceService {

	@GetMapping
	String getSessionId(@RequestHeader("Authorization") String token, @SpringQueryMap RequestDataDto request);
}
