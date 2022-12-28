package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.PartMotorcycle;

@Repository
public interface PartMotorcycleRepository extends JpaRepository<PartMotorcycle, Long> {

	List<PartMotorcycle> findAllByStatusOrderByIdAsc(Boolean status);
	
	@Query(value = "select p from PartMotorcycle p order by p.id asc")
	List<PartMotorcycle> findAllOrderById();

}
