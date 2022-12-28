package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.OtherExpenses;

@Repository
public interface OtherExpensesRepository extends JpaRepository<OtherExpenses, Long>{
	
	List<OtherExpenses> findAllByWarrantyClaimsId(Long warrantyId);
	
}
