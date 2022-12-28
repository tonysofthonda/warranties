package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.SettingService;
import com.honda.hdm.warrantiesmg.app.web.model.SettingDto;

@RestController
@RequestMapping(value = "setting")
public class SettingController {

	@Autowired
	private SettingService settingService;

	@GetMapping("/")
	public ResponseEntity<?> getSettings(@RequestParam(name = "name", required = false) String name) {
		List<SettingDto> settingDtoList = this.settingService.getSettings(name);
		if (settingDtoList.isEmpty()) {
			return new ResponseEntity<>(settingDtoList, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(settingDtoList, HttpStatus.OK);
		}
	}

	// Pathch Metod
	@PostMapping("/")
	public ResponseEntity<?> updateSetting(@RequestBody SettingDto settingDto) throws Exception {
//		if (settingDto.getId() != null) {
		this.settingService.updateSetting(settingDto);
		return new ResponseEntity<>(HttpStatus.OK);
//		}else {
//			throw new Exception("El id no puede ser nulo o vacío");
//		}		
	}
}
