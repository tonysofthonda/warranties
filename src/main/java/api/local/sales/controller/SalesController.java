package api.local.sales.controller;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import api.local.sales.config.HandlerError;
import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.Request;
import api.local.sales.service.SalesService;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api/")
@CrossOrigin(origins = {"${cors.origin.urione}", "${cors.origin.uritwo}"})
public class SalesController extends HandlerError {
	
	@Autowired
	private SalesService salesService;

	@GetMapping
    public BaseResponse<?> typeService(@SpringQueryMap Request requestQuery, @RequestParam(name = "vin", required = false) String vin,
            @RequestBody(required = false) Request requestBody) throws JsonMappingException, JsonProcessingException, ParseException {
        log.info("Start api sales controller");
        requestBody = (requestBody == null || requestBody.getVin() == null) ? requestQuery: requestBody;
        if(StringUtils.isNotBlank(vin)) {
            requestBody.setVin(vin);
        }
        return this.salesService.sales(requestBody);
    }
}
