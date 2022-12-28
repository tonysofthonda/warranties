package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.TypeClaims;

@Repository
public interface TypeClaimsRepository extends JpaRepository<TypeClaims, Long> {
	
	@Query(value="SELECT tc FROM TypeClaims tc WHERE (:status IS NULL OR tc.status =:status) order by tc.id asc")
	List<TypeClaims> findByStatus(@Param("status") Boolean status);

}
