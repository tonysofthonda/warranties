package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Group;

@Repository
public interface GroupRepository  extends JpaRepository<Group, Long>{

	Group findByName(String name);
	
	@Query(value = "select g from Group g where UPPER(g.name) = :name")
	Optional<Group> findByNameToUpper(@Param("name") final String name);
	
}
