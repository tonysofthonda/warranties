package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Model;

@Repository
public interface ModelRepository  extends JpaRepository<Model, Long>{

	Optional<Model> findByModel(final String model);
	
	@Query(value = "select m from Model m order by m.id asc")
	List<Model> findAllOrderById();
	
	@Query(value = "select m from Model m where lower(m.model) = lower(:model)")
	Model findOneByModel(final String model);
	
}
