package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Zone;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

	Zone findByName(String name);
	
	@Query(value = "select z from Zone z where UPPER(z.name) = :zone")
	Optional<Zone> findByZone(@Param("zone") final String zone);
}
