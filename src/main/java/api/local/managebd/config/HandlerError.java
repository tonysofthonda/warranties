package api.local.managebd.config;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import api.local.managebd.dto.BaseResponse;
import api.local.managebd.dto.Response;

public class HandlerError {
	
	private static final Logger logger = LogManager.getLogger(HandlerError.class);

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public BaseResponse<?> badRequest() {
	   logger.error("Error bad request");
	   return new Response<>().response("EMPTY JSON", 0);
	}

    @ExceptionHandler(NullPointerException.class)
    public BaseResponse<?> nullPointerHandler() {
    	logger.error("Error null Pointer Handler");
		return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(NumberFormatException.class)
    public BaseResponse<?> numberFormatHandler() {
    	logger.error("Error number Format Handler");
    	return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> internalError() {
    	logger.error("Error internal Error");
    	return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(JsonMappingException.class)
    public BaseResponse<?> jsonMappingException() {
    	logger.error("Error json Mapping Exception");
		return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(JsonProcessingException.class)
    public BaseResponse<?> jsonException() {
    	logger.error("Error Json Processing Exception");
		return new Response<>().response("UNKNOWN ERROR", 0);
    }
    
    @ExceptionHandler(ParseException.class)
    public BaseResponse<?> parseException() {
    	logger.error("Error parse Exception");
		return new Response<>().response("UNKNOWN ERROR", 0);
    }
}
