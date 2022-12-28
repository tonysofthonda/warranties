package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.CreateService;
import com.honda.hdm.warrantiesmg.app.domain.entity.Vin;


@Repository
public interface CreateServiceRepository extends JpaRepository<CreateService, Long> {

	Optional<List<CreateService>> findByVin(Vin vin);
	
	List<CreateService> findByOrderByIdDesc();
	
	CreateService findById(long id);
	
}
