package com.honda.hdm.warrantiesmg.app.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.honda.hdm.warrantiesmg.app.domain.entity.OrderType;

@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Long>{

	@Query(value = "select o from OrderType o order by o.idOrderType asc")
	public List<OrderType> findAll();
	
	public OrderType findByIdOrderType(Long id);

}
