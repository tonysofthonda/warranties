package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.honda.hdm.warrantiesmg.app.domain.entity.Symptom;
import com.honda.hdm.warrantiesmg.app.domain.repository.SintomaRepository;
import com.honda.hdm.warrantiesmg.app.web.model.SintomaDto;

class SintomaServiceImpTest {
	
	@Mock
	private SintomaRepository repository;
	
	@InjectMocks
	private SintomaServiceImp service;
	
	

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
	}

	
	@Test
	void testGetSintomas(Symptom sintomaDto) {
		List<SintomaDto> sintoma1 = new ArrayList<>();
		SintomaDto sintoma = new SintomaDto();
		sintoma.setId(sintomaDto.getId());
		sintoma.setCode(sintomaDto.getCode());
		sintoma.setDescriptionSpa(sintomaDto.getDescriptionSpa());
		sintoma.setStatus(sintomaDto.getStatus());
		sintoma1.add(sintoma);
		
		when(service.getSintomas()).thenReturn(sintoma1);
		
		verify(repository, times(1)).findAll();
	}

}
