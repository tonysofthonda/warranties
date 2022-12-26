package api.local.managebpcs.service;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.local.managebpcs.dto.BaseResponse;
import api.local.managebpcs.dto.Request;

public interface BpcsService {

	BaseResponse<?> typeService(Request request) throws JsonProcessingException, ParseException;

}
