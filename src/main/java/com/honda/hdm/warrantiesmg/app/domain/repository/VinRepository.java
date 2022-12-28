package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.Vin;

@Repository
public interface VinRepository extends JpaRepository<Vin, Long> {

	Optional<Vin> findByVin(String vin);
}
