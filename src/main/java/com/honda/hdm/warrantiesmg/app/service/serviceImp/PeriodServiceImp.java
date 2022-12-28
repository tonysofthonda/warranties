package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Period;
import com.honda.hdm.warrantiesmg.app.domain.repository.PeriodRepository;
import com.honda.hdm.warrantiesmg.app.service.PeriodService;
import com.honda.hdm.warrantiesmg.app.web.model.PeriodDto;
import com.honda.hdm.warrantiesmg.util.DateUtils;

@Service
public class PeriodServiceImp implements PeriodService{
	
	@Autowired
	private PeriodRepository periodRepository;
	
	@Autowired
	private DateUtils dateUtils;
	
	private static String dataFormat ="yyyy-MM-dd";

	@Override
	public List<PeriodDto> getAllPeriods() {
		List<PeriodDto> periodDtoList = new ArrayList<>();
		List<Period> periodEntity = this.periodRepository.findAllOrderById();
		for(Period period : periodEntity) {
			PeriodDto periodDto = new PeriodDto();
			periodDto.setId(period.getId());
			periodDto.setPeriodName(period.getPeriodName());
			periodDto.setDescription(period.getDescription());
			periodDto.setDateInit(this.dateUtils.convertTimestampToString(period.getDateInit(), "dd-MM-yyyy"));
			periodDto.setDateEnd( this.dateUtils.convertTimestampToString(period.getDateEnd() , "dd-MM-yyyy"));
			periodDto.setStatus(period.getStatus());			
			periodDtoList.add(periodDto);			
		}
		return periodDtoList;
	}

	@Override
	public void savePeriod(PeriodDto dto) {
		
		if(dto.getId() == null) {
			Period period = new Period();
			period.setPeriodName(dto.getPeriodName());
			period.setDescription(dto.getDescription());			
			period.setDateInit(this.dateUtils.convertStringToDate(dto.getDateInit(), dataFormat));
			period.setDateEnd(this.dateUtils.convertStringToDate(dto.getDateEnd(), dataFormat));
			period.setStatus(dto.getStatus());
			periodRepository.save(period);
		}else {
			Period periodFound = this.periodRepository.findById(dto.getId()).get();
			periodFound.setPeriodName(dto.getPeriodName());
			periodFound.setDescription(dto.getDescription());			
			periodFound.setDateInit(this.dateUtils.convertStringToDate(dto.getDateInit(), dataFormat));
			periodFound.setDateEnd(this.dateUtils.convertStringToDate(dto.getDateEnd(), dataFormat));
			periodFound.setStatus(dto.getStatus());
			periodRepository.save(periodFound);
		}
				
	}

	@Override
	public void updatePeriodByCron() {
		List<Period> periodEntity = this.periodRepository.findAll();
		for (Period period : periodEntity) {
			if(period.getDateInit().compareTo(period.getDateEnd()) > 90) {
				period.setStatus(false);
				this.periodRepository.save(period);
			}
		}
	}

	@Override
	public void updateStatusPeriod(PeriodDto periodDto) throws Exception{
		Optional<Period> periodFound = this.periodRepository.findById(periodDto.getId());
		if (periodFound.isPresent()) {
			periodFound.get().setStatus(periodDto.getStatus());
			this.periodRepository.save(periodFound.get());			
		}else {
			throw new Exception("El periodo no existe");	
		}
	}

}
