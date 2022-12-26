package api.local.netcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.local.netcommerce.config.HandlerError;
import api.local.netcommerce.dto.BaseResponse;
import api.local.netcommerce.dto.Request;
import api.local.netcommerce.service.NetcommerceService;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api/")
@CrossOrigin(origins = {"${cors.origin.urione}", "${cors.origin.uritwo}"})
public class NetcommerceController extends HandlerError {
	
	@Autowired
	private NetcommerceService netcommerceService;

	@GetMapping
	public BaseResponse<?> typeService(@SpringQueryMap Request requestQuery, 
			@RequestBody(required = false) Request requestBody) throws Exception {
		log.info("Start api netcommerce controller ");
		requestBody = (requestBody == null || requestBody.getVin() == null) ? requestQuery: requestBody;
		return netcommerceService.checkVinManagebd(requestBody);
	}
}
