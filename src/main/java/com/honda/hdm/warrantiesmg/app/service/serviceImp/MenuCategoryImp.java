package com.honda.hdm.warrantiesmg.app.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.domain.entity.MenuCategory;
import com.honda.hdm.warrantiesmg.app.domain.entity.View;
import com.honda.hdm.warrantiesmg.app.domain.repository.MenuCategoryRepository;
import com.honda.hdm.warrantiesmg.app.domain.repository.ViewRepository;
import com.honda.hdm.warrantiesmg.app.service.MenuCategoryService;
import com.honda.hdm.warrantiesmg.app.web.model.auth.MenuCategoryDto;
import com.honda.hdm.warrantiesmg.app.web.model.auth.ViewDto;

@Service
public class MenuCategoryImp implements MenuCategoryService {

	@Autowired
	private MenuCategoryRepository menuCategoryRep;
	
	@Autowired 
	private ViewRepository viewRepository;
	

	@Override
	public List<MenuCategoryDto> findAll() {
		List<MenuCategoryDto> categoryList = new ArrayList<>();		
		
		List<MenuCategory> menuCategory = this.menuCategoryRep.findAllOrderByOrder();
		ModelMapper mapper = new ModelMapper();
		for (MenuCategory category : menuCategory) {
			List<ViewDto> viewDtoList = new ArrayList<>();
			MenuCategoryDto menuCategoryDto = new MenuCategoryDto();
			menuCategoryDto.setId(category.getId());
			menuCategoryDto.setName(category.getName());
			menuCategoryDto.setOrder(category.getOrder());
			List<View> viewList = this.viewRepository.findByMenuCategoryAndBstateOrderByOrder(category, 1);			
			for (View view : viewList) {
				ViewDto viewDto = new ViewDto();
				mapper.map(view, viewDto);
				viewDtoList.add(viewDto);
			}		
			menuCategoryDto.setViews(viewDtoList);
			categoryList.add(menuCategoryDto);
		}
		return categoryList;
	}

}
