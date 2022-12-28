package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.State;
import com.honda.hdm.warrantiesmg.app.domain.repository.StateRepository;
import com.honda.hdm.warrantiesmg.app.service.StateService;
import com.honda.hdm.warrantiesmg.app.web.model.StateDto;

@Service
public class StateServiceImp implements StateService {
	
	@Autowired
	private StateRepository stateRepository;

	@Override
	public List<StateDto> getAllState() {
		List<StateDto> listState = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<State> statesEntity = this.stateRepository.findByStatus(true);
		for(State state: statesEntity) {
			listState.add(modelMapper.map(state, StateDto.class));
		}
		return listState;
	}

}
