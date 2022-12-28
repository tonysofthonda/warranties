package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Evidences;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidences, Long> {

	Evidences findByFilePath(String path);
	
	List<Evidences> findByWarrantyClaimId(Long warrantyId);
	
}
