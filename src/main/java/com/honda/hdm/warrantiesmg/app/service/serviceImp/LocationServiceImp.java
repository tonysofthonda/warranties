package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Location;
import com.honda.hdm.warrantiesmg.app.domain.entity.State;
import com.honda.hdm.warrantiesmg.app.domain.repository.LocationRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.StateRepository;
import com.honda.hdm.warrantiesmg.app.service.LocationService;
import com.honda.hdm.warrantiesmg.app.web.model.LocationDto;

@Service
public class LocationServiceImp implements LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Override
	public List<LocationDto> getAllLocations() {
		List<LocationDto> returnlocationDtos = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<Location> locations = locationRepository.findAll();
		for(Location location : locations) {
			returnlocationDtos.add(modelMapper.map(location, LocationDto.class));
		}
		return returnlocationDtos;
	}

	@Override
	public List<LocationDto> getlocationByIdState(Long id) {
		List<LocationDto> listLocation = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<Location> locationsEntity = locationRepository.findByStateId(id);
		for(Location location : locationsEntity) {
			listLocation.add(modelMapper.map(location, LocationDto.class));
		}
		return listLocation;
	}


	@Override
	public LocationDto getByIdSelect(Long id) {
		LocationDto dto = new LocationDto();
		Location locationEntity = locationRepository.findById(id).get();
		if(locationEntity != null) {
			BeanUtils.copyProperties(locationEntity, dto);
			Optional<State> stateEntity = stateRepository.findById(locationEntity.getState().getId());
			if(stateEntity.isPresent()) {
				dto.setState(stateEntity.get());
			}
			return dto;
		}
		throw new RuntimeException("El Municipio seleccionado no existe");
	}


}
