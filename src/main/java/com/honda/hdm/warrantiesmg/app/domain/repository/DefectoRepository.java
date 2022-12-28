package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Defect;

@Repository
public interface DefectoRepository extends JpaRepository<Defect, Long> {

	@Query(value = "select d from Defect d order by id asc")
	List<Defect> findAllOrderById();
}
