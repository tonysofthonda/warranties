package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.SitesDto;

public interface SitesService {

	List<SitesDto> getAllState();
	
	List<SitesDto> getAllTownship();
	
	List<SitesDto> getAllArea();
	
	List<SitesDto> getAllGroup();
	
	void saveSites(final SitesDto sitesDto) throws Exception;
	
	void updateSite(final SitesDto sitesDto) throws Exception;
}
