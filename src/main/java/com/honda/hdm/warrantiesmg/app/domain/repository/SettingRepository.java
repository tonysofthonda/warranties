package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.honda.hdm.warrantiesmg.app.domain.entity.Setting;

public interface SettingRepository extends JpaRepository<Setting, Long> {
	
	@Query(value="SELECT s FROM Setting s WHERE (:name IS NULL OR s.name = :name)")
	List<Setting> findByName(@Param("name") String name);
	
	

}
