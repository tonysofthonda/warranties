package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.ServicesParts;

@Repository
public interface ServicesPartsRepository extends JpaRepository<ServicesParts, Long>{
	
	@Query(value = "SELECT s FROM ServicesParts s ORDER BY s.id ASC")
	List<ServicesParts> findAll();

}
