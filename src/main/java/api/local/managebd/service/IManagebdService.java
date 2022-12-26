package api.local.managebd.service;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import api.local.managebd.dto.BaseResponse;
import api.local.managebd.dto.Request;

public interface IManagebdService {

	<T> BaseResponse<?> typeService(Request<T> dataDto) throws JsonMappingException, JsonProcessingException, ParseException;

	<T> BaseResponse<?> getData(Request<T> dataDto) throws JsonMappingException, JsonProcessingException, ParseException;
}
