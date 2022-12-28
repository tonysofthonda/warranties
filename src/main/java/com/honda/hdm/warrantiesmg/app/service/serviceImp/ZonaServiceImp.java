package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Zone;
import com.honda.hdm.warrantiesmg.app.domain.repository.ZoneRepository;
import com.honda.hdm.warrantiesmg.app.service.ZoneService;
import com.honda.hdm.warrantiesmg.app.web.model.ZoneDto;

@Service
public class ZonaServiceImp implements ZoneService {
	
	@Autowired
	private ZoneRepository zoneRepository;

	@Override
	public List<ZoneDto> getAllZone() {
		List<ZoneDto> returnZoneDtos = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<Zone> zoneEntity = zoneRepository.findAll();
		for(Zone zone : zoneEntity) {
			returnZoneDtos.add(modelMapper.map(zone, ZoneDto.class));
		}
		return returnZoneDtos;
	}

	@Override
	public ZoneDto getById(Long id) {
		ZoneDto dto = new ZoneDto();
		Optional<Zone> zoneFound = zoneRepository.findById(id);
		if(zoneFound.isPresent()) {
			BeanUtils.copyProperties(zoneFound.get(), dto);
			return dto;			
		}
		throw new RuntimeException("La zona no existe");
	}

	@Override
	public void saveZone(ZoneDto dto) {
		Zone zoneEntity = new Zone();
		if(zoneRepository.findByName(dto.getZoneName()) != null) {
			throw new RuntimeException("La zona ya existe");
		}
		zoneEntity.setName(dto.getZoneName());
		zoneEntity.setStatus(true);
		zoneEntity.setDateCreation(new Timestamp(new Date().getTime()));
		zoneRepository.save(zoneEntity);		
	}

}
