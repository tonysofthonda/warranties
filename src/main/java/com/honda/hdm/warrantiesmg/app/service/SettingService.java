package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.SettingDto;

public interface SettingService {

	List<SettingDto> getSettings(String name);
	
	void updateSetting(SettingDto settingDto) throws Exception; 
	
}
