package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Period;

@Repository
public interface PeriodRepository  extends JpaRepository<Period, Long>{

	@Query(value = "select p from Period p order by p.id asc")
	List<Period> findAllOrderById();
}
