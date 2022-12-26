package api.local.managebpcs.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.local.managebpcs.dto.SparePartsDto;
import api.local.managebpcs.repository.BpcsRepository;
import api.local.managebpcs.service.SparePartsService;
import lombok.extern.java.Log;

@Log
@Service
public class SparePartsServiceImpl implements SparePartsService {

	@Autowired
	private BpcsRepository bpcsRepository;
	
	@Override
	public SparePartsDto getSparePartsByNumPart(final String numPart) throws JsonProcessingException, ParseException {
		log.info("start SparePartsServiceImpl");
		List<Object[]> bpcs = bpcsRepository.findByNumPart(numPart);
		SparePartsDto sparePartsDto = null;
		if(bpcs != null) {
			for(Object[] obj : bpcs) {
				sparePartsDto = new SparePartsDto();
				sparePartsDto.setPrice(new BigDecimal(obj[0].toString()));
			}
		}
		
		return sparePartsDto;
	}
}
