package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
	
	@Query(value = "select s from State s where UPPER(s.name) = :state")
	Optional<State> findByState(@Param("state") final String state);

	List<State> findByStatus(Boolean status);
	
}
