package api.local.managebpcs.config;

import java.text.ParseException;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import api.local.managebpcs.dto.BaseResponse;
import api.local.managebpcs.dto.Response;
import api.local.managebpcs.enums.EnumStatusError;

public class HandlerError {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public BaseResponse<?> badRequest(){
	   return new Response<>().response(EnumStatusError.EMPTY_JSON.getType(), EnumStatusError.EMPTY_JSON.getValue());
	}

    @ExceptionHandler(NullPointerException.class)
    public BaseResponse<?> nullPointerHandler(){
		return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue());
    }
    
    @ExceptionHandler(NumberFormatException.class)
    public BaseResponse<?> numberFormatHandler(){
    	return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue());
    }
    
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> internalError(){
    	return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue());
    }
    
    @ExceptionHandler(JsonMappingException.class)
    public BaseResponse<?> jsonMappingException(){
		return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.EMPTY_JSON.getValue());
    }
    
    @ExceptionHandler(JsonProcessingException.class)
    public BaseResponse<?> jsonException(){
		return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.EMPTY_JSON.getValue());
    }
    
    @ExceptionHandler(ParseException.class)
    public BaseResponse<?> parseException(){
		return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.EMPTY_JSON.getValue());
    }
    
    @RequestMapping(value = "/", produces = "application/json", method = { RequestMethod.PUT, RequestMethod.DELETE, 
			RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.TRACE, RequestMethod.OPTIONS, RequestMethod.POST})
	public BaseResponse<?> invalidMethods() {
		return new Response<>().response(EnumStatusError.METHOD_NOT_ALLOWED.getType(), EnumStatusError.METHOD_NOT_ALLOWED.getValue());
	}
}
