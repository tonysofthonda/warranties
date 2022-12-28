package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honda.hdm.warrantiesmg.app.domain.entity.WarrantyClient;

public interface WarrantyClientRepository extends JpaRepository<WarrantyClient, Long> {
	
	Optional<WarrantyClient> findByClientName(String clientName);

}
