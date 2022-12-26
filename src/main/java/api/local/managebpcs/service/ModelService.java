package api.local.managebpcs.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.local.managebpcs.dto.ModelDto;

public interface ModelService {

	ModelDto getModelBymodel(String model) throws JsonProcessingException ;

}
