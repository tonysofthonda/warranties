package api.local.managebd.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import api.local.managebd.config.HandlerError;
import api.local.managebd.dto.BaseResponse;
import api.local.managebd.dto.Request;
import api.local.managebd.dto.Response;
import api.local.managebd.enums.EnumActions;
import api.local.managebd.service.impl.ManagebdService;
import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = {"${cors.origin.urione}", "${cors.origin.uritwo}"})
@RequestMapping("api/")
public class ManagebdController extends HandlerError {
	
	@Autowired
	private ManagebdService modelService;
	
	@GetMapping
	public BaseResponse<?> get(@SpringQueryMap Request<?> requestQuery, @RequestBody(required = false) Request<?> requestBody) throws JsonMappingException, JsonProcessingException, ParseException {
		log.info("start method get managebd ManagebdController");
		requestBody = (requestBody == null || requestBody.getVin() == null) ? requestQuery: requestBody;
		if(requestBody.getAction().equals(EnumActions.GET.getAction())) {
			if(requestBody.getData() == null || requestBody.getData().toString().equals("")) {
				return this.modelService.getData(requestBody);
			} else {
				log.info("Error invalid data method get ManagebdController");
				return new Response<>().response("INVALID DATA", 0, requestBody.getType());
			}
		} else {
			log.info("Error methos not allowed method get managebd ManagebdController");
			return new Response<>().response("METHOD NOT ALLOWED", 0, requestBody.getType());
		}
		
	}
	
	@PostMapping
	public BaseResponse<?> save(@RequestBody Request<?> request) throws JsonMappingException, JsonProcessingException, ParseException {
		log.info("start method post managebd ManagebdController");
		if(request.getAction().equals(EnumActions.POST.getAction())) {
			if(request.getData() != null) {
				return this.modelService.typeService(request);
			} else {
				log.info("Error invalid data method post ManagebdController");
				return new Response<>().response("INVALID DATA", 0, request.getType());
			}
		} else {
			log.info("Error methos not allowed method post managebd ManagebdController");
			return new Response<>().response("METHOD NOT ALLOWED", 0, request.getType());
		}
	}
	
	@RequestMapping(value = "/", produces = "application/json", method = { RequestMethod.PUT, RequestMethod.DELETE, 
			RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.TRACE, RequestMethod.OPTIONS})
	public BaseResponse<?> invalidMethods() {
		log.info("Error methos not allowed managebd ManagebdController");
		return new Response<>().response("METHOD NOT ALLOWED", 0, "ERROR");
	}
}
