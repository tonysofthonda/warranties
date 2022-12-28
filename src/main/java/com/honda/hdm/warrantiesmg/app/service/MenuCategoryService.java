package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.auth.MenuCategoryDto;

public interface MenuCategoryService {
	
	public List<MenuCategoryDto> findAll();

}
