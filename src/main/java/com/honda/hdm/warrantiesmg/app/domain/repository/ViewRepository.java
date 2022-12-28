package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.MenuCategory;
import com.honda.hdm.warrantiesmg.app.domain.entity.View;

@Repository
public interface ViewRepository extends JpaRepository<View, Long>{

	List<View> findByMenuCategoryAndBstateOrderByOrder(MenuCategory menuCategory, Integer bstate);
	
	
}
