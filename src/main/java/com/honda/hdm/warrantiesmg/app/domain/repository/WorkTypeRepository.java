package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.WorkType;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Long>{
	
	@Query(value = "select w from WorkType w order by w.idWorkType asc")
	public List<WorkType> findAll();
	
	public WorkType findByIdWorkType(Long id);
	
	public List<WorkType> findByOrderTypeIdOrderType(Long id);

}
