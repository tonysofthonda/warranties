package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	
	@Query(value =  "SELECT p FROM Location p WHERE p.state.id = :id ORDER BY p.name ASC")
	List<Location> findByStateId (@Param("id") Long id);
	
	@Query(value =  "SELECT p FROM Location p WHERE p.id = :id AND p.state.id = :idState")
	Optional<Location> findByIdAndStateId (@Param("id") Long id, @Param("idState") Long idState);
	
	@Query(value = "select l from Location l where UPPER(l.name) = :township")
	Optional<Location> findByLocation(@Param("township") final String township);
}
