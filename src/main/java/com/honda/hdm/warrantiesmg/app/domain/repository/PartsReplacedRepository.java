package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.PartsReplaced;

@Repository
public interface PartsReplacedRepository extends JpaRepository<PartsReplaced, Long> {
	
	List<PartsReplaced> findAllByWarrantyClaimsId(Long warrantyId);
	
	Optional<PartsReplaced> findByPartsPartNumber(final String partNumber);

}
