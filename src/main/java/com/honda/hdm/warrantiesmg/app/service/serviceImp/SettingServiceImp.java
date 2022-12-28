package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.Setting;
import com.honda.hdm.warrantiesmg.app.domain.repository.SettingRepository;
import com.honda.hdm.warrantiesmg.app.service.SettingService;
import com.honda.hdm.warrantiesmg.app.web.model.SettingDto;
import com.honda.hdm.warrantiesmg.util.DateUtils;

@Service
public class SettingServiceImp implements SettingService{

	@Autowired
	private SettingRepository settingRepository;
	
	@Autowired
	private DateUtils dateUtils;
	
	@Override
	public List<SettingDto> getSettings(String name) {
		List<Setting> settingList = this.settingRepository.findByName(name);
		List<SettingDto> settingDtoList = new ArrayList<>();
		if (!settingList.isEmpty()) {
			for (Setting setting : settingList) {
				SettingDto settingDto = new SettingDto(); 				
				settingDto.setId(setting.getId());
				settingDto.setName(setting.getName());
				settingDto.setValueNumber(setting.getValueNumber());
				settingDto.setValueText(setting.getValueText());
				if (setting.getUpdateTimestamp() != null) {
					settingDto.setLastUpdate(dateUtils.convertTimestampToString(setting.getUpdateTimestamp()));
				}else {
					settingDto.setLastUpdate(dateUtils.convertTimestampToString(setting.getCreateTimestamp()));
				}
				settingDtoList.add(settingDto);
			}
			return settingDtoList;
		}else {
			return settingDtoList;
		}		
	}

	@Override
	public void updateSetting(SettingDto settingDto) throws Exception {
		this.validations(settingDto); 
		if (settingDto.getId() != null) {
			Optional<Setting> setting = this.settingRepository.findById(settingDto.getId());
			if (setting.isPresent()) {
				setting.get().setValueNumber(settingDto.getValueNumber());
				setting.get().setValueText(settingDto.getValueText());
				this.settingRepository.save(setting.get());
			} 
		} else {
			Setting newSetting = new Setting();
			newSetting.setCreateTimestamp(new Timestamp(new Date().getTime()));
			newSetting.setValueNumber(settingDto.getValueNumber());
			newSetting.setValueText(settingDto.getValueText());
			newSetting.setName(settingDto.getName());
			newSetting.setObs("");
			newSetting.setStatus(true);
			newSetting.setBstate(0);
			this.settingRepository.save(newSetting);
		}
		
	}
	
	private void validations(SettingDto settingDto) throws Exception {
		if((settingDto.getValueNumber() == null || settingDto.getValueNumber() == 0) 
				&& (settingDto.getValueText() == null || settingDto.getValueText().isEmpty())) {
			throw new Exception("El campo de valor texto y numerico no puede ser nulos o vacíos");
		}		
	}
}
