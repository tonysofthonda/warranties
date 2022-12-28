package com.honda.hdm.warrantiesmg.app.domain.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Symptom;

@Repository
public interface SintomaRepository extends JpaRepository<Symptom, Long> {
	
	public Symptom findByCode(String codigo);
	
	@Query(value = "select s from Symptom s order by id asc")
	List<Symptom> findAllOrderById();

}
