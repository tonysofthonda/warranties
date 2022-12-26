package api.local.netcommerce.config;

import java.text.ParseException;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import api.local.netcommerce.dto.BaseResponse;
import api.local.netcommerce.dto.Response;
import api.local.netcommerce.enums.EnumStatusError;
import feign.FeignException;

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
		return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue());
    }
    
    @ExceptionHandler(JsonProcessingException.class)
    public BaseResponse<?> jsonException(){
		return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue());
    }
    
    @ExceptionHandler(ParseException.class)
    public BaseResponse<?> parseException(){
		return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue());
    }
    
    @ExceptionHandler(FeignException.class)
    public BaseResponse<?> handleFeignStatusException() {
    	return new Response<>().response(EnumStatusError.TYPE_UNKNOWN.getType(), EnumStatusError.TYPE_UNKNOWN.getValue());
    }
}
