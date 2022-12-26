package api.local.managebpcs.service;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.local.managebpcs.dto.SparePartsDto;

public interface SparePartsService {

	SparePartsDto getSparePartsByNumPart(String numPart) throws JsonProcessingException, ParseException;

}
