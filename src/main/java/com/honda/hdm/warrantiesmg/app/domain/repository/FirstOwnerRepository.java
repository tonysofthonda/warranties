package com.honda.hdm.warrantiesmg.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.honda.hdm.warrantiesmg.app.domain.entity.FirstOwner;

public interface FirstOwnerRepository extends JpaRepository<FirstOwner, Long> {

	FirstOwner findByFirstName(String firstName);
	
}
