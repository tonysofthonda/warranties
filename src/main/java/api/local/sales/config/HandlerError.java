package api.local.sales.config;

import java.text.ParseException;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import api.local.sales.dto.BaseResponse;
import api.local.sales.dto.Response;

public class HandlerError {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public BaseResponse<?> badRequest(){
	   return new Response<>().response("EMPTY JSON", 0);
	}

    @ExceptionHandler(NullPointerException.class)
    public BaseResponse<?> nullPointerHandler(){
		return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(NumberFormatException.class)
    public BaseResponse<?> numberFormatHandler(){
    	return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> internalError(){
    	return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(JsonMappingException.class)
    public BaseResponse<?> jsonMappingException(){
		return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(JsonProcessingException.class)
    public BaseResponse<?> jsonException(){
		return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(ParseException.class)
    public BaseResponse<?> parseException(){
		return new Response<>().response("UNKNOWN ERROR", 0);
    }
}
