package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.honda.hdm.warrantiesmg.app.domain.entity.ProblemCategory;

@Repository
public interface ProblemCategoryRepository extends JpaRepository<ProblemCategory, Long>{
	
	@Query(value="SELECT pc FROM ProblemCategory pc WHERE (:status IS NULL OR pc.status =:status) order by pc.id asc")
	List<ProblemCategory> findByStatus(@Param("status") Boolean status);

}
