package api.local.managebpcs.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.local.managebpcs.dto.BaseResponse;
import api.local.managebpcs.dto.ModelDto;
import api.local.managebpcs.dto.Request;
import api.local.managebpcs.dto.Response;
import api.local.managebpcs.dto.SalesDto;
import api.local.managebpcs.dto.SparePartsDto;
import api.local.managebpcs.enums.EnumStatusError;
import api.local.managebpcs.enums.EnumType;
import api.local.managebpcs.service.BpcsService;
import api.local.managebpcs.service.ModelService;
import api.local.managebpcs.service.SalesService;
import api.local.managebpcs.service.SparePartsService;
import lombok.extern.java.Log;

@Log
@Service
public class BpcsServiceImpl implements BpcsService {

	@Autowired
	private ModelService modelService; 
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private SparePartsService sparePartsService;
		
	@Override
	public BaseResponse<?> typeService(Request request) throws JsonProcessingException, ParseException {
		log.info("start BpcsServiceImpl");
		if(request.getType().equals(EnumType.MODEL.getType())) {
			if(request.getModel() == null || request.getModel().equals("")) {
				return new Response<>().response(EnumStatusError.MODEL_EMPTY.getType(), EnumStatusError.MODEL_EMPTY.getValue(), request.getType());
			}
			
			ModelDto modelDto = modelService.getModelBymodel(request.getModel());
			if(modelDto == null) {
				return new Response<>().response(EnumStatusError.EMPTY_RESULTS.getType(), EnumStatusError.EMPTY_RESULTS.getValue(), request.getType());
			}
			
			return new Response<>().response(modelDto, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), request.getType());
		} else if(request.getType().equals(EnumType.SPARE_PARTS.getType())) {
			if(request.getNum_part() == null || request.getNum_part().equals("")) {
				return new Response<>().response(EnumStatusError.NOPART_EMPTY.getType(), EnumStatusError.NOPART_EMPTY.getValue(), request.getType());
			}
			
			SparePartsDto sparePartsDto = this.sparePartsService.getSparePartsByNumPart(request.getNum_part());
			if(sparePartsDto == null) {
				return new Response<>().response(EnumStatusError.EMPTY_RESULTS.getType(), EnumStatusError.EMPTY_RESULTS.getValue(), request.getType());
			}
			
			return new Response<>().response(sparePartsDto, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), request.getType());
		} else if(request.getType().equals(EnumType.SALES.getType())) {
			if(request.getVin() == null || request.getVin().equals("")) {
				return new Response<>().response(EnumStatusError.VIN_EMPTY.getType(), EnumStatusError.VIN_EMPTY.getValue(), request.getType());
			}
			
			SalesDto salesDto = salesService.getSalesByVin(request.getVin());
			if(salesDto == null ) {
				return new Response<>().response(EnumStatusError.EMPTY_RESULTS.getType(), EnumStatusError.EMPTY_RESULTS.getValue(), request.getType());
			}

			return new Response<>().response(salesDto, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), request.getType());
		}
		return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue(), request.getType());
	}
}
