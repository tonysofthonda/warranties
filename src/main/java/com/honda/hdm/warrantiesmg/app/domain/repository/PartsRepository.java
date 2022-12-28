package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Parts;

@Repository
public interface PartsRepository extends JpaRepository<Parts, Long> {

	Optional<Parts> findByPartNumber(String partNumber);

}
