package api.local.managebpcs.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import api.local.managebpcs.config.HandlerError;
import api.local.managebpcs.dto.BaseResponse;
import api.local.managebpcs.dto.Request;
import api.local.managebpcs.service.BpcsService;
import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = {"http://qhdmglserwfs01.hdm.am.honda.com:8080/", "http://localhost:4200"} )
@RequestMapping("api/")
public class BpcsController extends HandlerError {

	@Autowired
	private BpcsService bpcsService;
	
	@GetMapping
	public BaseResponse<?> typeService(@SpringQueryMap Request requestQuery, @RequestBody(required = false) Request requestBody) throws JsonMappingException, JsonProcessingException, ParseException {
		log.info("Start api bpcs controller");
		requestBody = (requestBody == null) ? requestQuery: requestBody;
		return this.bpcsService.typeService(requestBody);
	}
}
