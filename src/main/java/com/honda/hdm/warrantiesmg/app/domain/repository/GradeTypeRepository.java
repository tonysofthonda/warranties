package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.GradeType;

@Repository
public interface GradeTypeRepository extends JpaRepository<GradeType, Long> {

	@Query(value="SELECT gt FROM GradeType gt WHERE (:status IS NULL OR gt.status =:status) order by gt.id asc")
	List<GradeType> findByStatus(@Param("status") Boolean status);
	
	
}
