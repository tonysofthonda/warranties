package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.View;
import com.honda.hdm.warrantiesmg.app.domain.repository.ViewRepository;
import com.honda.hdm.warrantiesmg.app.service.ViewService;
import com.honda.hdm.warrantiesmg.app.web.model.auth.ViewDto;
@Service
public class ViewServiceImp implements ViewService{
	
	@Autowired
	private ViewRepository viewRepository;

	@Override
	public List<ViewDto> findAll() {
		List<ViewDto> viewDtosList = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		List<View> views = this.viewRepository.findAll();
		for(View view : views) {
			viewDtosList.add(mapper.map(view, ViewDto.class));
		}
		return viewDtosList;
	}

}
