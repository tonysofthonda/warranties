package api.local.managebpcs.service;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.local.managebpcs.dto.SalesDto;

public interface SalesService {

	SalesDto getSalesByVin(String vin) throws JsonProcessingException, ParseException;

}
