package api.local.managebpcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.local.managebpcs.dto.ModelDto;
import api.local.managebpcs.repository.BpcsRepository;
import api.local.managebpcs.service.ModelService;
import lombok.extern.java.Log;

@Log
@Service
public class ModelServiceImpl implements ModelService {

	@Autowired
	private BpcsRepository bpcsRepository;
		
	@Override
	public ModelDto getModelBymodel(String model) throws JsonProcessingException {
		log.info("start ModelServiceImpl");
		List<Object[]> bpcs = bpcsRepository.findByModel(model);
		ModelDto modelDto = null;
		System.out.println(bpcs.size());
		if(bpcs != null) {
			for(Object[] obj : bpcs) {
				modelDto = new ModelDto();
				modelDto.setAnio(obj[0] == null ? null : Long.valueOf(obj[0].toString().trim().replace("", "")));
				modelDto.setVds(obj[1] == null ? null : obj[1].toString().trim());
				modelDto.setModel(obj[2] == null ? null : obj[2].toString().trim());
				modelDto.setColor(obj[3] == null ? null : obj[3].toString().trim());
				modelDto.setCchp(obj[4] == null ? null : obj[4].toString().trim());
				modelDto.setOrigin(obj[5] == null ? null : obj[5].toString().trim());
				modelDto.setDescription(obj[6] == null ? null : obj[6].toString().trim());
			}
		}
		
		return modelDto;
	}
}
