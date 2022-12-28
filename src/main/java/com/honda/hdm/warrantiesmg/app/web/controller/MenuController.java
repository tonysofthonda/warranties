package com.honda.hdm.warrantiesmg.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.hdm.warrantiesmg.app.service.MenuCategoryService;
import com.honda.hdm.warrantiesmg.app.web.model.auth.MenuCategoryDto;

@RestController
@RequestMapping("menu")
public class MenuController {
	
	@Autowired
	private MenuCategoryService menuCategoryService;
	
	@GetMapping("/list")
	public ResponseEntity<List<MenuCategoryService>> getMenus(){
		List<MenuCategoryDto> menu= menuCategoryService.findAll();
		return new ResponseEntity(menu, HttpStatus.OK);
	}

}
