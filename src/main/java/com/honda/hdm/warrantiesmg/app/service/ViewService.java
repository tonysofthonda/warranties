package com.honda.hdm.warrantiesmg.app.service;

import java.util.List;

import com.honda.hdm.warrantiesmg.app.web.model.auth.ViewDto;

public interface ViewService {

	public List<ViewDto> findAll();
}
