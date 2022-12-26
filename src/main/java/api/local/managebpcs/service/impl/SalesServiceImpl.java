package api.local.managebpcs.service.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import api.local.managebpcs.dto.SalesDto;
import api.local.managebpcs.repository.BpcsRepository;
import api.local.managebpcs.service.SalesService;
import api.local.managebpcs.utils.DateUtils;
import lombok.extern.java.Log;

@Log
@Service
public class SalesServiceImpl implements SalesService {
	
	@Autowired
	private BpcsRepository bpcsRepository;
	
	@Autowired
	private DateUtils dateUtils;

	@Override
	public SalesDto getSalesByVin(final String vin) throws JsonProcessingException, ParseException {
		log.info("start SalesServiceImpl");
		List<Object[]> bpcs = bpcsRepository.findByVin(vin);
		SalesDto salesDto = null;
		if(bpcs != null) {
			for(Object[] obj : bpcs) {
				salesDto = new SalesDto();
				salesDto.setName_dealer(obj[1].toString());
				salesDto.setNum_dealer(obj[0].toString());
				salesDto.setInvoice_date(this.dateUtils.parseDate(obj[3].toString().replace(",", "")));
				salesDto.setModel(obj[2].toString());
				salesDto.setMotor(obj[4].toString());
				salesDto.setVin(obj[5].toString());
			}
		}
		
		return salesDto;
	}
}
