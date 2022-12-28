package com.honda.hdm.warrantiesmg.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.ServiceClient;

@Repository
public interface ServiceClientRepository extends JpaRepository<ServiceClient, Long> {

	ServiceClient findByName(String clientName);

}
