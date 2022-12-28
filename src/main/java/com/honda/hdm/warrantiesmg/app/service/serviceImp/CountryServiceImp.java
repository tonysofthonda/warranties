package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Country;
import com.honda.hdm.warrantiesmg.app.domain.repository.CountryRepository;
import com.honda.hdm.warrantiesmg.app.service.CountryService;
import com.honda.hdm.warrantiesmg.app.web.model.CountryDto;

@Service
public class CountryServiceImp implements CountryService{
	
	@Autowired
	private CountryRepository countryRepository;

	@Override
	public List<CountryDto> getAllCountries() {
		List<Country> countryEntity = this.countryRepository.findAll();
		List<CountryDto> countryDto = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		for(Country country : countryEntity) {
			countryDto.add(modelMapper.map(country, CountryDto.class));
		}
		return countryDto;
	}

}
