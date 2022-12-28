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

import com.honda.hdm.warrantiesmg.app.domain.entity.Group;
import com.honda.hdm.warrantiesmg.app.domain.repository.GroupRepository;
import com.honda.hdm.warrantiesmg.app.service.GroupService;
import com.honda.hdm.warrantiesmg.app.web.model.GroupDto;

@Service
public class GroupServiceImp implements GroupService {
	
	@Autowired
	private GroupRepository groupRepository;

	@Override
	public List<GroupDto> getAllGroups() {
		List<GroupDto> returnGroupDtos = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		List<Group> groupEntity = groupRepository.findAll();
		for(Group group : groupEntity) {
			returnGroupDtos.add(modelMapper.map(group, GroupDto.class));
		}
		return returnGroupDtos;
	}

	@Override
	public GroupDto getById(Long id) {
		GroupDto dto = new GroupDto();
		Optional<Group> groupFound = groupRepository.findById(id);
		if(groupFound.isPresent()) {
			BeanUtils.copyProperties(groupFound.get(), dto);
			return dto;
		}
		throw new RuntimeException("El grupo no existe");
	}

	@Override
	public void saveGroup(GroupDto dto) {
		Group groupEntity = new Group();
		if(groupRepository.findByName(dto.getName()) != null) {
			throw new RuntimeException("El grupo ya existe");
		}
		groupEntity.setDateCreation(new Timestamp(new Date().getTime()));
		groupEntity.setName(dto.getName());
		groupEntity.setStatus(true);
		groupEntity.setDescription(dto.getDescription());
		groupRepository.save(groupEntity);
	}
	
	

}
