package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.GroupDto;

public interface GroupService {
	
	public List<GroupDto> getAllGroups();
	
	public GroupDto getById(Long id);
	
	public void saveGroup(GroupDto dto);

}
