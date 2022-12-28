package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honda.hdm.warrantiesmg.app.domain.entity.ServiceModel;

public interface ServiceModelRepository extends JpaRepository<ServiceModel, Long>{
	
	public List<Optional<ServiceModel>> findByModel(String model);

}
