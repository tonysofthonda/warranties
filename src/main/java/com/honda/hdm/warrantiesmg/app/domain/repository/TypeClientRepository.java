package com.honda.hdm.warrantiesmg.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.TypeClient;

@Repository
public interface TypeClientRepository  extends JpaRepository<TypeClient, Long>{

}
