package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.GroupService;
import com.honda.hdm.warrantiesmg.app.web.model.GroupDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("groups")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@ApiOperation(value = "Obtiene la lista de Grupos", notes = "Enlista toos los grupos")
	@GetMapping("/")
	public ResponseEntity<List<GroupDto>> getAllGroups(){
		List<GroupDto> listGroups = groupService.getAllGroups();
		return new ResponseEntity<>(listGroups ,HttpStatus.OK);
	}

	@ApiOperation(value = "Guarda Grupo", notes = "Almacena un nuevo registro de Grupo.")
	@PostMapping("/")
	public ResponseEntity<?> saveGroup(@RequestBody GroupDto groupDto) {
		if(groupDto.getName().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		groupService.saveGroup(groupDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
